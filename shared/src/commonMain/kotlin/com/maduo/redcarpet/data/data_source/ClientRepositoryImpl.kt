package com.maduo.redcarpet.data.data_source

import com.maduo.redcarpet.data.mappers.toClientAuthDetails
import com.maduo.redcarpet.data.remote.RestApi
import com.maduo.redcarpet.domain.dto.ClientDto
import com.maduo.redcarpet.domain.entity.ClientAuthDetails
import com.maduo.redcarpet.domain.repositories.ClientRepository
import com.maduo.redcarpet.kmm.shared.database.AppDatabase
import com.maduo.redcarpet.util.Resource
import io.ktor.client.call.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ClientRepositoryImpl(
    private val restApi: RestApi = RestApi(),
): ClientRepository {

    override suspend fun getClientById(id: String, token: String): Flow<Resource<ClientDto>> = flow {
        println("Client Response Status: Loading...")
        emit(Resource.Loading<ClientDto>())
        try {
            val response = restApi.fetchClientById(id, token)
            if (response.status.value == 200) {
                val clientDto = response.body<ClientDto>()
                emit(Resource.Success(clientDto))
                println("Client Response Status: Success --> $response")
            } else {
                val message = response.body<String>().ifEmpty { response.status.description }
                emit(Resource.Error<ClientDto>(message))
                println("Client Response Status: Failed --> $response")
            }
        } catch (e: Exception) {
            emit(Resource.Error<ClientDto>(e.message ?: "Unexpected Error"))
            println("Client Response Status: Exception --> $e")
        }
    }

    override suspend fun getClientByPhoneNumber(phoneNumber: String, token: String): Flow<Resource<ClientDto>> = flow {
        emit(Resource.Loading<ClientDto>())
        try {
            val response = restApi.fetchClientByPhoneNumber(phoneNumber, token)
            if (response.status.value == 200) {
                val clientDto = response.body<ClientDto>()
                emit(Resource.Success(clientDto))
                println("Client Response Status: Success --> $response")
            } else {
                val message = response.body<String>().ifEmpty { response.status.description }
                emit(Resource.Error<ClientDto>(message))
                println("Client Response Status: Failed --> $response")
            }
        } catch (e: Exception) {
            emit(Resource.Error<ClientDto>(e.message ?: "Unexpected Error"))
            println("Client Response Status: Exception --> $e")
        }
    }

    override suspend fun createClient(clientDto: ClientDto): Flow<Resource<String>> = flow {
        emit(Resource.Loading<String>())
        try {
            val response = restApi.postClient(clientDto)
            if (response.status.value == 201) {
                val message = response.body<String>()
                emit(Resource.Success(message))
                println("Client Response Status: Success --> $response")
            } else {
                val message = response.body<String>().ifEmpty { response.status.description }
                emit(Resource.Error<String>(message))
                println("Client Response Status: Failed --> $response")
            }
        } catch (e: Exception) {
            emit(Resource.Error<String>(e.message ?: "Unexpected Error"))
            println("Client Response Status: Exception --> $e")
        }
    }

    override suspend fun updateClient(clientDto: ClientDto, token: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading<String>())
        try {
            val response = restApi.updateClient(clientDto, token)
            if (response.status.value == 200) {
                val message = response.body<String>()
                emit(Resource.Success(message))
                println("Client Response Status: Success --> $response")
            } else {
                val message = response.body<String>().ifEmpty { response.status.description }
                emit(Resource.Error<String>(message))
                println("Client Response Status: Failed --> $response")
            }
        } catch (e: Exception) {
            emit(Resource.Error<String>(e.message ?: "Unexpected Error"))
            println("Client Response Status: Exception --> $e")
        }
    }
}