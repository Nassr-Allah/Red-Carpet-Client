package com.maduo.redcarpet.android.presentation.feature_store.design_screen

import com.maduo.redcarpet.domain.model.Design

data class DesignDetailsScreenState(
    val isLoading: Boolean = false,
    val isOrderCreated: Boolean = false,
    val design: Design? = null,
    val response: String = "",
    val error: String = ""
)
