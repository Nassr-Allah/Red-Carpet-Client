package com.maduo.redcarpet.android.presentation.feature_cart.cutom_order_details

import com.maduo.redcarpet.domain.model.CustomOrder

data class CustomOrderScreenState(
    val isLoading: Boolean = false,
    val order: CustomOrder? = null,
    val error: String = ""
)
