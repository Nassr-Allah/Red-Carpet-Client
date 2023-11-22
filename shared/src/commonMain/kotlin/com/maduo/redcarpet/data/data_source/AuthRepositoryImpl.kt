package com.maduo.redcarpet.data.data_source

import com.maduo.redcarpet.data.remote.RestApi
import com.maduo.redcarpet.domain.dto.AuthRequestDto
import com.maduo.redcarpet.domain.dto.AuthResponseDto
import com.maduo.redcarpet.domain.entity.ClientAuthDetails
import com.maduo.redcarpet.domain.repositories.AuthRepository
import com.maduo.redcarpet.domain.repositories.ClientRepository
import com.maduo.redcarpet.util.Resource
import io.ktor.client.call.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl(
    private val restApi: RestApi = RestApi(),
): AuthRepository {

    override suspend fun login(authRequestDto: AuthRequestDto): Flow<Resource<AuthResponseDto>> = flow {
        emit(Resource.Loading<AuthResponseDto>())
        try {
            val response = restApi.login(authRequestDto)
            if (response.status.value == 200) {
                val authResponse = response.body<AuthResponseDto>()
                val clientAuthDetails =
                    ClientAuthDetails(token = authResponse.token, clientId = authResponse.clientId)
                emit(Resource.Success(authResponse))
                println("Auth Response Status: Success --> $response with $clientAuthDetails")
            } else {
                val message = response.body<String>().ifEmpty { response.status.description }
                emit(Resource.Error<AuthResponseDto>(message))
                println("Auth Response Status: Failed --> $response")
            }
        } catch (e: Exception) {
            emit(Resource.Error<AuthResponseDto>(e.message ?: "Unexpected Error"))
            println("Auth Response Status: Exception --> ${e.message}")
        }
    }
}