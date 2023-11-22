package com.maduo.redcarpet.domain.repositories

import com.maduo.redcarpet.domain.dto.RegistrationDto
import com.maduo.redcarpet.util.Resource
import kotlinx.coroutines.flow.Flow

interface RegistrationRepository {

    suspend fun createRegistration(registrationDto: RegistrationDto, token: String): Flow<Resource<String>>

}