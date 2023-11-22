package com.maduo.redcarpet.android.presentation.feature_profile.personal_info_screen

import com.maduo.redcarpet.domain.dto.ClientDto

data class PersonalInfoScreenState(
    val isLoading: Boolean = false,
    val clientDto: ClientDto? = null,
    val isClientUpdated: Boolean = false,
    val response: String = "",
    val error: String = ""
)
