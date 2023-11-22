package com.maduo.redcarpet.domain.repositories

import com.maduo.redcarpet.domain.dto.NormalOrderDto
import com.maduo.redcarpet.util.Resource
import kotlinx.coroutines.flow.Flow

interface NormalOrderRepository {

    suspend fun getClientOrders(clientId: String, token: String): Flow<Resource<List<NormalOrderDto>>>

    suspend fun getOrderById(id: String, token: String): Flow<Resource<NormalOrderDto>>

    suspend fun createOrder(orderDto: NormalOrderDto, token: String): Flow<Resource<String>>

}