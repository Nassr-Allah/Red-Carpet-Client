package com.maduo.redcarpet.android.presentation.feature_payment.ccp_screen

import android.content.ContentResolver
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maduo.redcarpet.domain.dto.PaymentDto
import com.maduo.redcarpet.domain.repositories.ClientCacheRepository
import com.maduo.redcarpet.domain.repositories.ImageRepository
import com.maduo.redcarpet.domain.repositories.PaymentRepository
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
class CcpScreenViewModel @Inject constructor(
    private val paymentRepository: PaymentRepository,
    private val imageRepository: ImageRepository,
    private val clientCacheRepository: ClientCacheRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CcpScreenState())
    val state: MutableStateFlow<CcpScreenState> = _state

    val uri = mutableStateOf<Uri?>(null)

    private fun createPayment(orderId: String) {
        val formatter = SimpleDateFormat("dd MMMM yyyy", Locale("en"))
        val date = formatter.format(Date())

        viewModelScope.launch {

            val clientAuthDetails = clientCacheRepository.getClientFromDb()
            if (clientAuthDetails != null) {
                val paymentDto = PaymentDto(
                    method = "ccp/BaridiMob",
                    receipt = state.value.imageUrl,
                    date = date,
                    clientId = clientAuthDetails.clientId,
                    orderId = orderId
                )
                paymentRepository.createPayment(paymentDto, clientAuthDetails.token).collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _state.value = _state.value.copy(isLoading = true, error = "")
                        }
                        is Resource.Success -> {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                isPaymentCreated = true,
                                paymentResponse = result.data ?: "created"
                            )
                        }
                        is Resource.Error -> {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                error = result.message ?: "Unexpected Error",
                                isPaymentCreated = false
                            )
                        }
                    }
                }
            } else {
                _state.value = _state.value.copy(isLoading = false, error = "Unauthorized Request")
            }
        }
    }

    fun uploadReceipt(contentResolver: ContentResolver, orderId: String) {
        val useCase = GetImageFileUseCase(uri.value!!, contentResolver)
        viewModelScope.launch {
            val image = useCase.toByteArray()
            val fileName = useCase.getFileName()
            val clientAuthDetails = clientCacheRepository.getClientFromDb()
            if (clientAuthDetails != null) {
                imageRepository.uploadReceipt(image, fileName, clientAuthDetails.token).collect { result ->
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
                            createPayment(orderId)
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
    }

}