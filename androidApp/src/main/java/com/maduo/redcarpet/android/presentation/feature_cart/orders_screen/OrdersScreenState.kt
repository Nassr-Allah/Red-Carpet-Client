package com.maduo.redcarpet.android.presentation.feature_cart.orders_screen

import com.maduo.redcarpet.domain.model.Order

data class OrdersScreenState(
    val isLoading: Boolean = false,
    val orders: List<Order> = emptyList(),
    val error: String = ""
)