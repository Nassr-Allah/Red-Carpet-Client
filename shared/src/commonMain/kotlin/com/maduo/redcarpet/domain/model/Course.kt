package com.maduo.redcarpet.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Course(
    val id: String = "",
    val title: String = "",
    val price: Int = 0,
    val description: String = "",
    val type: String = "",
    val duration: String = "",
    val category: String = "",
    val image: String = ""
)
