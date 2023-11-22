package com.maduo.redcarpet.android.presentation.feature_cart.cutom_order_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maduo.redcarpet.data.mappers.mapToCustomOrder
import com.maduo.redcarpet.domain.model.CustomOrder
import com.maduo.redcarpet.domain.repositories.ClientCacheRepository
import com.maduo.redcarpet.domain.repositories.CustomOrderRepository
import com.maduo.redcarpet.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomOrderViewModel @Inject constructor(
    private val repository: CustomOrderRepository,
    private val clientCacheRepository: ClientCacheRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CustomOrderScreenState())
    val state: MutableStateFlow<CustomOrderScreenState> = _state

    fun getOrderDetails(orderId: String) {
        viewModelScope.launch {
            val clientAuthDetails = clientCacheRepository.getClientFromDb()
            if (clientAuthDetails != null) {
                repository.getOrderById(orderId, clientAuthDetails.token).collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _state.value = _state.value.copy(isLoading = true, error = "")
                        }
                        is Resource.Success -> {
                            var order = CustomOrder()
                            if (result.data != null) {
                                order = mapToCustomOrder(result.data!!)
                            }
                            _state.value = _state.value.copy(isLoading = false, order = order)
                        }
                        is Resource.Error -> {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                error = result.message ?: "Unknown Error"
                            )
                        }
                    }
                }
            } else {
                _state.value = _state.value.copy(isLoading = false, error = "Unauthorized")
            }
        }
    }

    fun deleteClientFromDb() {
        viewModelScope.launch {
            clientCacheRepository.deleteClientFromDb()
        }
    }

}