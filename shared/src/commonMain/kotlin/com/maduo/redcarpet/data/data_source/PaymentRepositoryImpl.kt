package com.maduo.redcarpet.data.data_source

import com.maduo.redcarpet.data.remote.RestApi
import com.maduo.redcarpet.domain.dto.PaymentDto
import com.maduo.redcarpet.domain.repositories.PaymentRepository
import com.maduo.redcarpet.util.Resource
import io.ktor.client.call.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PaymentRepositoryImpl(private val restApi: RestApi = RestApi()): PaymentRepository {

    override suspend fun getPaymentById(id: String, token: String): Flow<Resource<PaymentDto>> = flow {
        TODO("Not yet implemented")
    }

    override suspend fun createPayment(paymentDto: PaymentDto, token: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading<String>())
        try {
            val response = restApi.postPayment(paymentDto, token)
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