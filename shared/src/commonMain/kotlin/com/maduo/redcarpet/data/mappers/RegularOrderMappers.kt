package com.maduo.redcarpet.data.mappers

import com.maduo.redcarpet.domain.dto.NormalOrderDto
import com.maduo.redcarpet.domain.model.Design
import com.maduo.redcarpet.domain.model.RegularOrder

fun mapToOrder(orderDto: NormalOrderDto): RegularOrder {
    return RegularOrder(
        id = orderDto.id ?: "",
        design = Design(category = ""),
        deliveryPrice = orderDto.deliveryDto?.price ?: 0,
        deliveryPlaces = orderDto.deliveryDto?.places ?: "",
        totalPrice = orderDto.order?.totalPrice ?: 0,
        status = orderDto.order?.status ?: "",
        size = orderDto.size,
        isPatternIncluded = orderDto.order?.isPatternIncluded ?: false
    )
}