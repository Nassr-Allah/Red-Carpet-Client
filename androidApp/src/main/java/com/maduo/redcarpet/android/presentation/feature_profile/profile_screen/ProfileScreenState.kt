package com.maduo.redcarpet.android.presentation.feature_profile.profile_screen

data class ProfileScreenState(
    val isLoading: Boolean = false,
    val isLoggedOut: Boolean = false,
    val error: String = ""
)
