package com.maduo.redcarpet.domain.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegistrationDto(
    @SerialName("id")
    val id: String? = null,
    @SerialName("date")
    val date: String = "",
    @SerialName("courseId")
    val courseId: String = "",
    @SerialName("clientId")
    val clientId: String = ""
)
