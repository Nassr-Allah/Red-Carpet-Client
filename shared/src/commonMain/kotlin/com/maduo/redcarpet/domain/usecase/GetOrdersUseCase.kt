package com.maduo.redcarpet.domain.usecase

import com.maduo.redcarpet.data.data_source.CustomOrderRepositoryImpl
import com.maduo.redcarpet.data.data_source.NormalOrderRepositoryImpl
import com.maduo.redcarpet.data.mappers.mapToOrder
import com.maduo.redcarpet.domain.dto.OrderDto
import com.maduo.redcarpet.domain.model.Order
import com.maduo.redcarpet.domain.repositories.CustomOrderRepository
import com.maduo.redcarpet.domain.repositories.NormalOrderRepository
import com.maduo.redcarpet.util.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class GetOrdersUseCase(
    private val normalOrderRepository: NormalOrderRepository = NormalOrderRepositoryImpl(),
    private val customOrderRepository: CustomOrderRepository = CustomOrderRepositoryImpl()
) {

    operator fun invoke(clientId: String, token: String): Flow<Resource<List<Order>>> = flow {
        try {
            println("GetOrdersUseCase:  started call...")
            emit(Resource.Loading<List<Order>>())
            val normalOrderFlow = normalOrderRepository.getClientOrders(clientId, token)
            val customOrderFlow = customOrderRepository.getClientOrders(clientId, token)

            var orders = listOf<Order>()

            normalOrderFlow.zip(customOrderFlow) { normalOrders, customOrders ->
                println("GetOrdersUseCase: normal ones: ${normalOrders.data}")
                println("GetOrdersUseCase: custom ones: ${customOrders.data}")
                val normalOrdersList =
                    normalOrders.data?.map { mapToOrder(normalOrder = it) } ?: emptyList()
                val customOrdersList =
                    customOrders.data?.map { mapToOrder(customOrder = it) } ?: emptyList()
                orders = normalOrdersList.reversed() + customOrdersList.reversed()
            }.collect()
            emit(Resource.Success(orders))
            delay(5000)
        } catch (e: Exception) {
            println("GetOrdersUseCase:  exception -- $e")
            emit(Resource.Error<List<Order>>(e.message ?: "Unexpected error"))
        }
    }

}