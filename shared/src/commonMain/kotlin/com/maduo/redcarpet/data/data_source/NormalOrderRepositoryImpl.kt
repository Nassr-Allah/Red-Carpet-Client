package com.maduo.redcarpet.data.data_source

import com.maduo.redcarpet.data.remote.RestApi
import com.maduo.redcarpet.domain.dto.NormalOrderDto
import com.maduo.redcarpet.domain.repositories.NormalOrderRepository
import com.maduo.redcarpet.util.Resource
import io.ktor.client.call.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NormalOrderRepositoryImpl(private val restApi: RestApi = RestApi()): NormalOrderRepository {

    override suspend fun getClientOrders(clientId: String, token: String): Flow<Resource<List<NormalOrderDto>>> = flow {
        emit(Resource.Loading<List<NormalOrderDto>>())
        println("NormalOrderRepo:  called function")
        try {
            val response = restApi.fetchClientOrders(clientId, token)
            if (response.status.value == 200) {
                val orders = response.body<List<NormalOrderDto>>()
                emit(Resource.Success(orders))
            } else {
                val message = response.body<String>()
                emit(Resource.Error<List<NormalOrderDto>>(message))
            }
        } catch (e: Exception) {
            emit(Resource.Error<List<NormalOrderDto>>(e.message ?: "Unexpected Error"))
        }
    }

    override suspend fun getOrderById(id: String, token: String): Flow<Resource<NormalOrderDto>> = flow {
        emit(Resource.Loading<NormalOrderDto>())
        println("NormalOrderRepo:  called function")
        try {
            val response = restApi.fetchOrderById(id, token)
            println("NormalOrderRepo:  $response")
            if (response.status.value == 200) {
                println("NormalOrderRepo:  Success")
                val order = response.body<NormalOrderDto>()
                emit(Resource.Success(order))
            } else {
                println("NormalOrderRepo:  Failed: $response")
                val message = response.body<String>()
                emit(Resource.Error<NormalOrderDto>(message))
            }
        } catch (e: Exception) {
            println("NormalOrderRepo:  exception $e")
            emit(Resource.Error<NormalOrderDto>(e.message ?: "Unexpected Error"))
        }
    }

    override suspend fun createOrder(orderDto: NormalOrderDto, token: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading<String>())
        try {
            val response = restApi.postOrder(orderDto, token)
            if (response.status.value == 201) {
                val message = response.body<String>()
                emit(Resource.Success(message))
            } else {
                val message = response.body<String>()
                emit(Resource.Error<String>(message))
            }
        } catch (e: Exception) {
            emit(Resource.Error<String>(e.message ?: "Unexpected Error"))
        }
    }
}