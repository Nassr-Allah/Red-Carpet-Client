package com.maduo.redcarpet.domain.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DesignDto(
    @SerialName("id")
    val id: String = "",
    @SerialName("name")
    val name: String = "",
    @SerialName("designer")
    val designer: String = "",
    @SerialName("price")
    val price: Int = 0,
    @SerialName("images")
    val images: List<String> = emptyList(),
    @SerialName("sizes")
    val sizes: List<String> = emptyList(),
    @SerialName("colors")
    val colors: List<Long> = emptyList(),
    @SerialName("description")
    val description: String = "",
    @SerialName("category")
    val category: String = "",
    @SerialName("delivery")
    val delivery: DeliveryDto? = null,
    @SerialName("patternPrice")
    val patternPrice: Int = 0
)
