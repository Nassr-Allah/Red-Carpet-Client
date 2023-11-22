package com.maduo.redcarpet.domain.model

data class RegularOrder(
    val id: String = "",
    var design: Design,
    val deliveryPlaces: String = "",
    val deliveryPrice: Int = 0,
    val totalPrice: Int = 0,
    val status: String = "",
    val size: String = "",
    val isPatternIncluded: Boolean = false
)
