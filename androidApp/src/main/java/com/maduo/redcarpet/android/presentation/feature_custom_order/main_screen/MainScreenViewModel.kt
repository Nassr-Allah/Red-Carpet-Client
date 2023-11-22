package com.maduo.redcarpet.android.presentation.feature_custom_order.main_screen

import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maduo.redcarpet.domain.dto.CustomDesignDto
import com.maduo.redcarpet.domain.dto.CustomOrderDto
import com.maduo.redcarpet.domain.dto.OrderDto
import com.maduo.redcarpet.domain.entity.ClientAuthDetails
import com.maduo.redcarpet.domain.repositories.ClientCacheRepository
import com.maduo.redcarpet.domain.repositories.CustomOrderRepository
import com.maduo.redcarpet.domain.repositories.ImageRepository
import com.maduo.redcarpet.domain.usecase.GetImageFileUseCase
import com.maduo.redcarpet.domain.usecase.getFileName
import com.maduo.redcarpet.domain.usecase.toByteArray
import com.maduo.redcarpet.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val customOrderRepository: CustomOrderRepository,
    private val imageRepository: ImageRepository,
    private val clientCacheRepository: ClientCacheRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MainScreenState())
    val state: MutableStateFlow<MainScreenState> = _state

    val category = mutableStateOf("")
    val gender = mutableStateOf("")
    val age = mutableStateOf("")
    val designType = mutableStateOf("")
    val clothMaterial = mutableStateOf("")
    val size = mutableStateOf("")
    val colors = mutableStateOf("")
    val budget = mutableStateOf("")
    val deliveryTime = mutableStateOf("")
    val isSewn = mutableStateOf(false)
    val isPatternIncluded = mutableStateOf(false)
    val addition = mutableStateOf("")
    val attachment = mutableStateOf("")

    val uri = mutableStateOf<Uri?>(null)

    private fun initializeCustomOrder(): CustomOrderDto {
        val formatter = SimpleDateFormat("dd MMMM yyyy", Locale("en"))
        val date = formatter.format(Date())

        return CustomOrderDto(
            clientId = "",
            budget = budget.value.trim().toInt(),
            deliveryTime = deliveryTime.value,
            isSewingIncluded = isSewn.value,
            isPatternIncluded = isPatternIncluded.value,
            addition = addition.value.ifEmpty { "nothing" },
            attachment = state.value.imageUrl.ifEmpty { "null" },
            gender = gender.value,
            age = age.value.trim().toInt(),
            type = designType.value,
            material = clothMaterial.value,
            size = size.value,
            colors = colors.value,
            category = category.value,
            status = "Pending",
            totalPrice = budget.value.trim().toInt(),
            date = date
        )
    }

    private fun createCustomOrder() {
        viewModelScope.launch {
            val clientAuthDetails = clientCacheRepository.getClientFromDb()
            if (clientAuthDetails != null) {
                val customOrder = initializeCustomOrder().copy(clientId = clientAuthDetails.clientId)
                customOrderRepository.createCustomOrder(customOrder, clientAuthDetails.token).collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            Log.d("MainScreenViewModel", "loading...")
                            _state.value =
                                _state.value.copy(isLoading = true, error = "", message = "")
                        }
                        is Resource.Success -> {
                            Log.d("MainScreenViewModel", "Success: ${state.value}")
                            _state.value = _state.value.copy(
                                isLoading = false,
                                isOrderSent = true,
                                error = "",
                                message = result.data ?: ""
                            )
                        }
                        is Resource.Error -> {
                            Log.d("MainScreenViewModel", "Error: ${state.value}")
                            _state.value = _state.value.copy(
                                isLoading = false,
                                isOrderSent = false,
                                error = result.message ?: "Unknown Error"
                            )
                        }
                    }
                }
            } else {
                _state.value = _state.value.copy(isLoading = false, error = "Unauthorized Request")
            }
        }
    }

    fun uploadAttachment(contentResolver: ContentResolver) {
        if (uri.value != null) {
            val useCase = GetImageFileUseCase(uri.value!!, contentResolver)
            viewModelScope.launch {
                val clientAuthDetails = clientCacheRepository.getClientFromDb()
                val image = useCase.toByteArray()
                val fileName = useCase.getFileName()
                if (clientAuthDetails != null) {
                    imageRepository.uploadReceipt(image, fileName, clientAuthDetails.token)
                        .collect { result ->
                            when (result) {
                                is Resource.Loading -> {
                                    _state.value = _state.value.copy(isLoading = true, error = "")
                                }
                                is Resource.Success -> {
                                    _state.value = _state.value.copy(
                                        isLoading = false,
                                        imageUrl = result.data ?: "",
                                        isImageUploaded = true
                                    )
                                    createCustomOrder()
                                }
                                is Resource.Error -> {
                                    _state.value = _state.value.copy(
                                        isLoading = false,
                                        error = result.message ?: "Unknown Error",
                                        isImageUploaded = false
                                    )
                                }
                        }
                    }
               } else {
                   _state.value = _state.value.copy(isLoading = false, error = "Unauthorized Request")
                }
            }
        } else {
            viewModelScope.launch {
                createCustomOrder()
            }
        }
    }

    fun validateInput(): Boolean {
        if (!age.value.trim().isDigitsOnly() || !budget.value.trim().isDigitsOnly()) return false
        val fields = listOf(
            category.value,
            gender.value,
            age.value,
            deliveryTime.value,
            designType.value,
            clothMaterial.value,
            size.value,
            colors.value
        )
        fields.forEach { field ->
            if (field.isEmpty()) return false
        }
        return true
    }

}