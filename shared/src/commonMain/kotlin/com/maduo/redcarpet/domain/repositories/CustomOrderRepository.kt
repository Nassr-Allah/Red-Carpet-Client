package com.maduo.redcarpet.domain.repositories

import com.maduo.redcarpet.domain.dto.CustomOrderDto
import com.maduo.redcarpet.domain.dto.OrderDto
import com.maduo.redcarpet.util.Resource
import kotlinx.coroutines.flow.Flow

interface CustomOrderRepository {

    suspend fun getClientOrders(clientId: String, token: String): Flow<Resource<List<CustomOrderDto>>>

    suspend fun getOrderById(id: String, token: String): Flow<Resource<CustomOrderDto>>

    suspend fun createCustomOrder(orderDto: CustomOrderDto, token: String): Flow<Resource<String>>

}