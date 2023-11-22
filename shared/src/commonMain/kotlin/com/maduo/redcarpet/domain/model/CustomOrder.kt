package com.maduo.redcarpet.domain.model

data class CustomOrder(
    val id: String = "",
    val size: String = "",
    val colors: String = "",
    val cloth: String = "",
    val category: String = "",
    val budget: Int = 0,
    val period: String = "",
    val status: String = "",
    val date: String = ""
)
