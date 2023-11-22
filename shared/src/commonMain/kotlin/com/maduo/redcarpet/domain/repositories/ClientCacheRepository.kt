package com.maduo.redcarpet.domain.repositories

import com.maduo.redcarpet.domain.entity.ClientAuthDetails

interface ClientCacheRepository {

    suspend fun getClientFromDb(): ClientAuthDetails?

    suspend fun insertClient(clientAuthDetails: ClientAuthDetails)

    suspend fun deleteClientFromDb()

}