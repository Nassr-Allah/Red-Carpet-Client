package com.maduo.redcarpet.domain.repositories

import com.maduo.redcarpet.domain.dto.ClientDto
import com.maduo.redcarpet.domain.entity.ClientAuthDetails
import com.maduo.redcarpet.util.Resource
import kotlinx.coroutines.flow.Flow

interface ClientRepository {

    suspend fun getClientById(id: String, token: String): Flow<Resource<ClientDto>>

    suspend fun getClientByPhoneNumber(phoneNumber: String, token: String): Flow<Resource<ClientDto>>

    suspend fun createClient(clientDto: ClientDto): Flow<Resource<String>>

    suspend fun updateClient(clientDto: ClientDto, token: String): Flow<Resource<String>>

}