package com.maduo.redcarpet.android.presentation.feature_cart.normal_order_details

import com.maduo.redcarpet.domain.model.RegularOrder

data class NormalOrderScreenState(
    val isLoading: Boolean = false,
    val order: RegularOrder? = null,
    val error: String = ""
)
