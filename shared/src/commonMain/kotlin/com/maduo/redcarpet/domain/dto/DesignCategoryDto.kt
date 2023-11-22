package com.maduo.redcarpet.domain.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DesignCategoryDto(
    @SerialName("id")
    val id: String = "",
    @SerialName("name")
    val name: String = ""
)
