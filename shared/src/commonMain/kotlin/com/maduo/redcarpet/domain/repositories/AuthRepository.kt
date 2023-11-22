package com.maduo.redcarpet.domain.repositories

import com.maduo.redcarpet.domain.dto.AuthRequestDto
import com.maduo.redcarpet.domain.dto.AuthResponseDto
import com.maduo.redcarpet.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun login(authRequestDto: AuthRequestDto): Flow<Resource<AuthResponseDto>>

}