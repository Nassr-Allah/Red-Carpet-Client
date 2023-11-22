package com.maduo.redcarpet.domain.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OriginalPatternDto(
    @SerialName("id")
    val id: String = "",
    @SerialName("image")
    val image: String = "",
    @SerialName("price")
    val price: Int = 0
)
