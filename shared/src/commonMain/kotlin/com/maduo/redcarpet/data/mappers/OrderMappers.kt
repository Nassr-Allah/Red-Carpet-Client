package com.maduo.redcarpet.data.mappers

import com.maduo.redcarpet.domain.dto.CustomOrderDto
import com.maduo.redcarpet.domain.dto.NormalOrderDto
import com.maduo.redcarpet.domain.model.CustomOrder
import com.maduo.redcarpet.domain.model.Order

fun mapToOrder(customOrder: CustomOrderDto? = null, normalOrder: NormalOrderDto? = null): Order {
    return if(customOrder != null) {
        Order(
            id = customOrder.id ?: "",
            status = customOrder.status,
            date = customOrder.date,
            price = customOrder.totalPrice,
            type = "Custom"
        )
    } else {
        Order(
            id = normalOrder?.id ?: "",
            status = normalOrder?.order?.status ?: "",
            date = normalOrder?.order?.date ?: "",
            price = normalOrder?.order?.totalPrice ?: 0,
            type = "Normal"
        )
    }
}