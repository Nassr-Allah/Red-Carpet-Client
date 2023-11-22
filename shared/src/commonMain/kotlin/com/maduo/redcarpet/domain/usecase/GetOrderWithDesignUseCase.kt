package com.maduo.redcarpet.domain.usecase

import com.maduo.redcarpet.data.data_source.DesignRepositoryImpl
import com.maduo.redcarpet.data.data_source.NormalOrderRepositoryImpl
import com.maduo.redcarpet.data.mappers.mapToDesign
import com.maduo.redcarpet.data.mappers.mapToOrder
import com.maduo.redcarpet.domain.dto.DesignDto
import com.maduo.redcarpet.domain.dto.NormalOrderDto
import com.maduo.redcarpet.domain.model.Design
import com.maduo.redcarpet.domain.model.RegularOrder
import com.maduo.redcarpet.domain.repositories.DesignRepository
import com.maduo.redcarpet.domain.repositories.NormalOrderRepository
import com.maduo.redcarpet.util.Resource
import kotlinx.coroutines.flow.*

class GetOrderWithDesignUseCase(
    private val orderRepository: NormalOrderRepository = NormalOrderRepositoryImpl(),
    private val designRepository: DesignRepository = DesignRepositoryImpl()
) {

    operator fun invoke(orderId: String, token: String): Flow<Resource<RegularOrder>> = flow {
        try {
            emit(Resource.Loading<RegularOrder>())
            println("GetOrderWithDesign:  started call...")
            orderRepository.getOrderById(orderId, token).collect { orderResult ->
                when(orderResult) {
                    is Resource.Success -> {
                        println("GetOrderWithDesign:  ${orderResult.data}")
                        val orderDto = orderResult.data
                        designRepository.getDesignById(orderResult.data?.designId ?: "", token).collect {
                            when(it) {
                                is Resource.Success -> {
                                    val order = mapToOrder(orderDto ?: NormalOrderDto())
                                    val design = mapToDesign(it.data ?: DesignDto())
                                    order.design = design
                                    emit(Resource.Success(order))
                                }
                                is Resource.Error -> {
                                    emit(Resource.Error<RegularOrder>(it.message ?: "Unexpected Error"))
                                }
                                is Resource.Loading -> {
                                    emit(Resource.Loading<RegularOrder>())
                                }
                            }
                        }
                    }
                    is Resource.Error -> {
                        println("GetOrderWithDesign: error ${orderResult.message}")
                        emit(Resource.Error<RegularOrder>(orderResult.message ?: "Unexpected Error"))
                    }
                    is Resource.Loading -> {
                        println("GetOrderWithDesign:  loading...")
                        emit(Resource.Loading<RegularOrder>())
                    }
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error<RegularOrder>(e.message ?: "Unexpected Error"))
        }
    }

}