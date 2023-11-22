package com.maduo.redcarpet.data.data_source

import com.maduo.redcarpet.data.mappers.toClientAuthDetails
import com.maduo.redcarpet.domain.entity.ClientAuthDetails
import com.maduo.redcarpet.domain.repositories.ClientCacheRepository
import com.maduo.redcarpet.kmm.shared.database.AppDatabase

class ClientCacheRepositoryImpl(db: AppDatabase): ClientCacheRepository {

    private val queries = db.clientQueries

    override suspend fun getClientFromDb(): ClientAuthDetails? {
        return queries.getClient().executeAsOneOrNull()?.toClientAuthDetails()
    }

    override suspend fun insertClient(clientAuthDetails: ClientAuthDetails) {
        queries.insertClientToDb(
            id = clientAuthDetails.id,
            clientId = clientAuthDetails.clientId,
            token = clientAuthDetails.token
        )
    }

    override suspend fun deleteClientFromDb() {
        queries.deleteClientFromDb()
    }

}