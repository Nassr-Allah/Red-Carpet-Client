package com.maduo.redcarpet.data.data_source

import com.maduo.redcarpet.data.remote.RestApi
import com.maduo.redcarpet.domain.dto.CustomOrderDto
import com.maduo.redcarpet.domain.dto.OrderDto
import com.maduo.redcarpet.domain.model.CustomOrder
import com.maduo.redcarpet.domain.repositories.ClientRepository
import com.maduo.redcarpet.domain.repositories.CustomOrderRepository
import com.maduo.redcarpet.util.Resource
import io.ktor.client.call.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CustomOrderRepositoryImpl(
    private val restApi: RestApi = RestApi(),
): CustomOrderRepository {

    override suspend fun getClientOrders(clientId: String, token: String): Flow<Resource<List<CustomOrderDto>>> = flow {
        emit(Resource.Loading<List<CustomOrderDto>>())
        val response = restApi.fetchCustomOrdersByClientId(clientId, token)
        println("Custom Orders Repo:   response: $response")
        try {
            if (response.status.value == 200) {
                val orders = response.body<List<CustomOrderDto>>()
                emit(Resource.Success(orders))
            } else {
                val message = response.body<String>()
                emit(Resource.Error<List<CustomOrderDto>>(message))
            }
        } catch (e: Exception) {
            emit(Resource.Error<List<CustomOrderDto>>(e.message ?: "Unexpected Error"))
        }
    }

    override suspend fun getOrderById(id: String, token: String): Flow<Resource<CustomOrderDto>> = flow {
        emit(Resource.Loading<CustomOrderDto>())
        val response = restApi.fetchCustomOrderById(id, token)
        try {
            if (response.status.value == 200) {
                val order = response.body<CustomOrderDto>()
                emit(Resource.Success(order))
            } else {
                val message = response.body<String>()
                emit(Resource.Error<CustomOrderDto>(message))
            }
        } catch (e: Exception) {
            emit(Resource.Error<CustomOrderDto>(e.message ?: "Unexpected Error"))
        }
    }

    override suspend fun createCustomOrder(orderDto: CustomOrderDto, token: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading<String>())
        val response = restApi.postCustomOrder(orderDto, token)
        println("CustomOrder Response: ${response.body<Any>()} -- ${response.status}")
        try {
            if (response.status.value == 201) {
                val message = response.body<String>()
                emit(Resource.Success(message))
            } else {
                val message = response.body<String>().ifEmpty { response.status.description }
                emit(Resource.Error<String>(message))
            }
        } catch (e: Exception) {
            println("CustomOrder Response: Exception: ${e.message}")
            emit(Resource.Error<String>(e.message ?: "Unexpected Error"))
        }
    }
}