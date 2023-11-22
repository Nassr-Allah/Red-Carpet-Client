package com.maduo.redcarpet.domain.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthRequestDto(
    @SerialName("phoneNumber")
    val phoneNumber: String = "",
    @SerialName("password")
    val password: String = ""
)
