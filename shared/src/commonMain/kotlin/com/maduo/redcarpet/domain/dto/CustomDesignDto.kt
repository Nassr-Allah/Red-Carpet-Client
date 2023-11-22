package com.maduo.redcarpet.domain.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CustomDesignDto(
    @SerialName("id")
    val id: String = "",
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
    @SerialName("categoryId")
    val categoryId: String
)