package com.maduo.redcarpet.data.data_source

import com.maduo.redcarpet.data.remote.RestApi
import com.maduo.redcarpet.domain.dto.ClientCollectionDto
import com.maduo.redcarpet.domain.repositories.ClientRepository
import com.maduo.redcarpet.domain.repositories.CollectionRepository
import com.maduo.redcarpet.kmm.shared.database.AppDatabase
import com.maduo.redcarpet.util.Resource
import io.ktor.client.call.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CollectionRepositoryImpl(
    private val restApi: RestApi = RestApi(),
): CollectionRepository {

    override suspend fun getClientCollections(clientId: String, token: String): Flow<Resource<ClientCollectionDto>> = flow {
        emit(Resource.Loading<ClientCollectionDto>())
        try {
            val response = restApi.fetchCollectionByClientId(clientId, token)
            if (response.status.value == 200) {
                val collection = response.body<ClientCollectionDto>()
                emit(Resource.Success(collection))
            } else {
                val message = response.body<String>()
                emit(Resource.Error<ClientCollectionDto>(message))
            }
        } catch (e: Exception) {
            emit(Resource.Error<ClientCollectionDto>(e.message ?: "Unexpected Error"))
        } catch (e: NullPointerException) {
            emit(Resource.Error<ClientCollectionDto>(e.message ?: "Client is null"))
        }
    }

    override suspend fun getCollectionById(id: String, token: String): Flow<Resource<ClientCollectionDto>> = flow {
        emit(Resource.Loading<ClientCollectionDto>())
        try {
            val response = restApi.fetchClientById(id, token)
            if (response.status.value == 200) {
                val collection = response.body<ClientCollectionDto>()
                emit(Resource.Success(collection))
            } else {
                val message = response.body<String>()
                emit(Resource.Error<ClientCollectionDto>(message))
            }
        } catch (e: Exception) {
            emit(Resource.Error<ClientCollectionDto>(e.message ?: "Unexpected Error"))
        }
    }

    override suspend fun createCollection(collectionDto: ClientCollectionDto, token: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading<String>())
        try {
            val response = restApi.postCollection(collectionDto, token)
            if (response.status.value == 200) {
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