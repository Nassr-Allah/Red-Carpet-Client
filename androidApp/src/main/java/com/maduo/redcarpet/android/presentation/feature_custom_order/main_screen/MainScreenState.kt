package com.maduo.redcarpet.android.presentation.feature_custom_order.main_screen

data class MainScreenState(
    val isLoading: Boolean = false,
    val isOrderSent: Boolean = false,
    val isImageUploaded: Boolean = false,
    val imageUrl: String = "",
    val message: String = "",
    val error: String = ""
)
