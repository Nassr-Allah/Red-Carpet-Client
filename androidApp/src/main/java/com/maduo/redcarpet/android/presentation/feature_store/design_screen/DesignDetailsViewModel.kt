package com.maduo.redcarpet.android.presentation.feature_store.design_screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maduo.redcarpet.data.mappers.mapToDesign
import com.maduo.redcarpet.domain.dto.DeliveryDto
import com.maduo.redcarpet.domain.dto.DesignDto
import com.maduo.redcarpet.domain.dto.NormalOrderDto
import com.maduo.redcarpet.domain.dto.OrderDto
import com.maduo.redcarpet.domain.model.Design
import com.maduo.redcarpet.domain.repositories.ClientCacheRepository
import com.maduo.redcarpet.domain.repositories.DesignRepository
import com.maduo.redcarpet.domain.repositories.NormalOrderRepository
import com.maduo.redcarpet.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DesignDetailsViewModel @Inject constructor(
    private val orderRepository: NormalOrderRepository,
    private val designRepository: DesignRepository,
    private val clientCacheRepository: ClientCacheRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DesignDetailsScreenState())
    val state: MutableStateFlow<DesignDetailsScreenState> = _state

    private val formatter = SimpleDateFormat("dd MMMM yyyy", Locale("en"))
    private val date = formatter.format(Date())

    val designId = mutableStateOf("")

    val design = mutableStateOf(state.value.design)
    val size = mutableStateOf("")

    val order = mutableStateOf(
        OrderDto(
            status = "pending",
            date = date,
            isPatternIncluded = false
        )
    )

    fun sendOrder() {
        viewModelScope.launch {
            val clientAuthDetails = clientCacheRepository.getClientFromDb()
            if (clientAuthDetails != null) {

                val normalOrder = NormalOrderDto(
                    order = order.value,
                    clientId = clientAuthDetails.clientId,
                    designId = _state.value.design!!.id,
                    deliveryDto = DeliveryDto(
                        places = _state.value.design!!.deliveryPlaces,
                        price = _state.value.design!!.deliveryPrice
                    ),
                    size = size.value
                )

                orderRepository.createOrder(normalOrder, clientAuthDetails.token).collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            Log.d("DesignDetailsViewModel", "state: ${state.value}")
                            _state.value = _state.value.copy(isLoading = true, error = "")
                        }
                        is Resource.Success -> {
                            Log.d("DesignDetailsViewModel", "state: ${state.value}")
                            _state.value = _state.value.copy(
                                isLoading = false,
                                isOrderCreated = true,
                                response = result.data ?: "Order Added",
                                error = ""
                            )
                        }
                        is Resource.Error -> {
                            Log.d("DesignDetailsViewModel", "state: ${state.value}")
                            Log.d("DesignDetailsViewModel", "Error: ${result.message}")
                            _state.value = _state.value.copy(
                                isLoading = false,
                                error = result.message ?: "Unknown Error",
                                isOrderCreated = false
                            )
                        }
                    }
                }
            } else {
                _state.value = _state.value.copy(isLoading = false, error = "Unauthorized Request")
            }
        }
    }

    fun getDesignById() {
        viewModelScope.launch {
            val clientAuthDetails = clientCacheRepository.getClientFromDb()
            Log.d("DesignDetailsViewModel", "started call with design: ${designId.value}")

            if (clientAuthDetails != null) {
                designRepository.getDesignById(designId.value, clientAuthDetails.token).collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            Log.d("DesignDetailsViewModel", "Loading...")
                            _state.value = _state.value.copy(isLoading = true, error = "")
                        }
                        is Resource.Success -> {
                            Log.d("DesignDetailsViewModel", "Success: ${result.data}")
                            val design = mapToDesign(result.data ?: DesignDto())
                            _state.value = _state.value.copy(isLoading = false, design = design)
                        }
                        is Resource.Error -> {
                            Log.d("DesignDetailsViewModel", "Error: ${result.message}")
                            _state.value = _state.value.copy(
                                isLoading = false,
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

}