package com.maduo.redcarpet.android.presentation.feature_payment.ccp_screen

data class CcpScreenState(
    val isLoading: Boolean = false,
    val isPaymentCreated: Boolean = false,
    val isImageUploaded: Boolean = false,
    val paymentResponse: String = "",
    val imageUrl: String = "",
    val error: String = ""
)
