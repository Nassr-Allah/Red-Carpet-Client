package com.maduo.redcarpet.domain.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderDto(
    @SerialName("status")
    val status: String = "",
    @SerialName("totalPrice")
    var totalPrice: Int = 0,
    @SerialName("isPatternIncluded")
    var isPatternIncluded: Boolean,
    @SerialName("date")
    val date: String = ""
)
