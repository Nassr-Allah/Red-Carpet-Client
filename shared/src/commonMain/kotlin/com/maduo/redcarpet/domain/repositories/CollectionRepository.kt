package com.maduo.redcarpet.domain.repositories

import com.maduo.redcarpet.domain.dto.ClientCollectionDto
import com.maduo.redcarpet.util.Resource
import kotlinx.coroutines.flow.Flow

interface CollectionRepository {

    suspend fun getClientCollections(clientId: String, token: String): Flow<Resource<ClientCollectionDto>>

    suspend fun getCollectionById(id: String, token: String): Flow<Resource<ClientCollectionDto>>

    suspend fun createCollection(collectionDto: ClientCollectionDto, token: String): Flow<Resource<String>>

}