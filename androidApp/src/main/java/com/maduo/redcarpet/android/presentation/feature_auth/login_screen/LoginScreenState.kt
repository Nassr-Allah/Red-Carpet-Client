package com.maduo.redcarpet.android.presentation.feature_auth.login_screen

data class LoginScreenState(
    val isLoading: Boolean = false,
    val isLogged: Boolean = false,
    val isClientCrated: Boolean = false,
    val isPhoneVerified: Boolean = false,
    val isCodeSent: Boolean = false,
    val error: String = ""
)
