package com.maduo.redcarpet.domain.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NormalOrderDto(
    @SerialName("id")
    val id: String? = null,
    @SerialName("order")
    val order: OrderDto? = null,
    @SerialName("clientId")
    val clientId: String = "",
    @SerialName("designId")
    val designId: String = "",
    @SerialName("delivery")
    val deliveryDto: DeliveryDto? = null,
    @SerialName("size")
    val size: String = ""
)
