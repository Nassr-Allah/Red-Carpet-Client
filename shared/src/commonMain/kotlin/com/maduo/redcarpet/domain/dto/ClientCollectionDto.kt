package com.maduo.redcarpet.domain.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClientCollectionDto(
    @SerialName("id")
    val id: String = "",
    @SerialName("designs")
    val designs: List<String> = emptyList(),
    @SerialName("patterns")
    val patterns: List<String> = emptyList(),
    @SerialName("clientId")
    val clientId: String = ""
)
