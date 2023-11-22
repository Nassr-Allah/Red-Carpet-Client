package com.maduo.redcarpet.domain.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponseDto(
    @SerialName("token")
    val token: String = "",
    @SerialName("clientId")
    val clientId: String = ""
)
