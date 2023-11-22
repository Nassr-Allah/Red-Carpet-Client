package com.maduo.redcarpet.android.presentation.feature_cart.orders_screen

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maduo.redcarpet.domain.model.Order
import com.maduo.redcarpet.domain.repositories.ClientCacheRepository
import com.maduo.redcarpet.domain.usecase.GetOrdersUseCase
import com.maduo.redcarpet.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersScreenViewModel @Inject constructor(
    private val useCase: GetOrdersUseCase,
    private val clientCacheRepository: ClientCacheRepository
) : ViewModel() {

    private val _state = MutableStateFlow(OrdersScreenState())
    val state: MutableStateFlow<OrdersScreenState> = _state

    private var allOrders = listOf<Order>()
    var filteredList = mutableStateListOf<Order>()
        private set

    init {
        getOrders()
    }

    private fun getOrders() {
        viewModelScope.launch {
            val clientAuthDetails = clientCacheRepository.getClientFromDb()
            if (clientAuthDetails != null) {
                useCase(clientAuthDetails.clientId, clientAuthDetails.token).collectLatest { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _state.value = _state.value.copy(isLoading = true, error = "")
                        }
                        is Resource.Success -> {
                            allOrders = result.data ?: emptyList()
                            if (filteredList.isEmpty()) {
                                filteredList = allOrders.toMutableStateList()
                            }
                            _state.value = _state.value.copy(
                                isLoading = false,
                                orders = result.data ?: emptyList()
                            )
                            Log.d("OrdersScreenViewModel", "${result.data}")
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

    fun filterOrders(query: String) {
        if (query.contains("all", true)) {
            filteredList = allOrders.toMutableStateList()
            _state.value = _state.value.copy(orders = filteredList)
        } else {
            filteredList = allOrders.filter {
                it.status.contains(query, true)
            }.toMutableStateList()
            _state.value = _state.value.copy(orders = filteredList)
            Log.d("OrdersScreenViewModel", "list: ${filteredList.toList()}")
        }
    }

}