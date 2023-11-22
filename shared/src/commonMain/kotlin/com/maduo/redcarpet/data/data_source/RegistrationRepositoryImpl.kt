package com.maduo.redcarpet.data.data_source

import com.maduo.redcarpet.data.remote.RestApi
import com.maduo.redcarpet.domain.dto.RegistrationDto
import com.maduo.redcarpet.domain.repositories.RegistrationRepository
import com.maduo.redcarpet.util.Resource
import io.ktor.client.call.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RegistrationRepositoryImpl(private val restApi: RestApi = RestApi()): RegistrationRepository {

    override suspend fun createRegistration(registrationDto: RegistrationDto, token: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading<String>())
        try {
            val response = restApi.postRegistration(registrationDto, token)
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