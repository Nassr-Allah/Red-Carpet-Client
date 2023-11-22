package com.maduo.redcarpet.domain.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CustomOrderDto(
    @SerialName("id")
    val id: String? = null,
    @SerialName("clientId")
    val clientId: String = "",
    @SerialName("budget")
    val budget: Int = 0,
    @SerialName("status")
    val status: String,
    @SerialName("totalPrice")
    val totalPrice: Int,
    @SerialName("date")
    val date: String,
    @SerialName("deliveryTime")
    val deliveryTime: String = "",
    @SerialName("isSewingIncluded")
    val isSewingIncluded: Boolean,
    @SerialName("isPatternIncluded")
    val isPatternIncluded: Boolean,
    @SerialName("addition")
    val addition: String = "",
    @SerialName("attachment")
    val attachment: String = "",
    @SerialName("gender")
    val gender: String,
    @SerialName("age")
    val age: Int,
    @SerialName("type")
    val type: String,
    @SerialName("material")
    val material: String,
    @SerialName("size")
    val size: String,
    @SerialName("colors")
    val colors: String,
    @SerialName("category")
    val category: String
)

