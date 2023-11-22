package com.maduo.redcarpet.data.mappers

import com.maduo.redcarpet.domain.dto.CustomOrderDto
import com.maduo.redcarpet.domain.dto.DesignCategoryDto
import com.maduo.redcarpet.domain.model.CustomOrder

fun mapToCustomOrder(customOrderDto: CustomOrderDto): CustomOrder {
    return CustomOrder(
        id = customOrderDto.id ?: "",
        size = customOrderDto.size,
        colors = customOrderDto.colors,
        cloth = customOrderDto.material,
        category = customOrderDto.category,
        budget = customOrderDto.budget,
        period = customOrderDto.deliveryTime,
        status = customOrderDto.status,
        date = customOrderDto.date
    )
}