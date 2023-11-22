package com.maduo.redcarpet.domain.model

data class Cart(
    val id: String = "",
    val items: List<Design>,
    val deliveryPlaces: String = "",
    val deliveryPrice: Int = 0
)
