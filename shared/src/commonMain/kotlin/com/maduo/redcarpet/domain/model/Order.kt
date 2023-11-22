package com.maduo.redcarpet.domain.model

data class Order(
    val id: String,
    val status: String,
    val date: String,
    val price: Int,
    val type: String
)
