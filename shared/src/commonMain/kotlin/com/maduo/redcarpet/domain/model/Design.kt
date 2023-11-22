package com.maduo.redcarpet.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Design(
    val id: String = "",
    val name: String = "",
    val designer: String = "",
    val price: Int = 0,
    val images: List<String> = emptyList(),
    val sizes: List<String> = emptyList(),
    val colors: List<Long> = emptyList(),
    val description: String = "",
    val deliveryPlaces: String = "",
    val deliveryPrice: Int = 0,
    val patternPrice: Int = 0,
    val category: String
)
