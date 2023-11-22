package com.maduo.redcarpet.domain.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeliveryDto(
    @SerialName("places")
    val places: String = "",
    @SerialName("price")
    val price: Int = 0
)
