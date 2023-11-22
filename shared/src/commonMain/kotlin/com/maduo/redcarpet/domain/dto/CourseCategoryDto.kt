package com.maduo.redcarpet.domain.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseCategoryDto(
    @SerialName("id")
    val id: String = "",
    @SerialName("name")
    val name: String = ""
)
