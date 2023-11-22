package com.maduo.redcarpet.domain.repositories

import com.maduo.redcarpet.domain.dto.PaymentDto
import com.maduo.redcarpet.util.Resource
import kotlinx.coroutines.flow.Flow

interface PaymentRepository {

    suspend fun getPaymentById(id: String, token: String): Flow<Resource<PaymentDto>>

    suspend fun createPayment(paymentDto: PaymentDto, token: String): Flow<Resource<String>>

}