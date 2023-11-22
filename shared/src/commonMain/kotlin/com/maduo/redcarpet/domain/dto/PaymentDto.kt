package com.maduo.redcarpet.domain.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaymentDto(
    @SerialName("id")
    val id: String = "",
    @SerialName("method")
    val method: String = "",
    @SerialName("receipt")
    val receipt: String = "",
    @SerialName("date")
    val date: String = "",
    @SerialName("clientId")
    val clientId: String = "",
    @SerialName("orderId")
    val orderId: String = ""
)
