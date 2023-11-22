package com.maduo.redcarpet.domain.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseDto(
    @SerialName("id")
    val id: String,
    @SerialName("title")
    val title: String,
    @SerialName("image")
    val image: String,
    @SerialName("price")
    val price: Int,
    @SerialName("description")
    val description: String,
    @SerialName("type")
    val type: String,
    @SerialName("duration")
    val duration: String,
    @SerialName("category")
    val category: String
)
