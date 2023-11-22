package com.maduo.redcarpet.android.presentation.feature_cart.normal_order_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maduo.redcarpet.domain.model.Design
import com.maduo.redcarpet.domain.model.RegularOrder
import com.maduo.redcarpet.domain.repositories.ClientCacheRepository
import com.maduo.redcarpet.domain.usecase.GetOrderWithDesignUseCase
import com.maduo.redcarpet.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NormalOrderViewModel @Inject constructor(
    private val useCase: GetOrderWithDesignUseCase,
    private val clientCacheRepository: ClientCacheRepository
) : ViewModel() {

    private val _state = MutableStateFlow(NormalOrderScreenState())
    val state: MutableStateFlow<NormalOrderScreenState> = _state

    fun getOrderDetails(orderId: String) {
        viewModelScope.launch {
            val clientAuthDetails = clientCacheRepository.getClientFromDb()
            if (clientAuthDetails != null) {
                useCase(orderId, clientAuthDetails.token).collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _state.value = _state.value.copy(isLoading = true, error = "")
                        }
                        is Resource.Success -> {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                order = result.data ?: RegularOrder(design = Design(category = ""))
                            )
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

}