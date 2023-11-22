package com.maduo.redcarpet.domain.repositories

import com.maduo.redcarpet.domain.dto.DesignDto
import com.maduo.redcarpet.util.Resource
import kotlinx.coroutines.flow.Flow

interface DesignRepository {

    suspend fun getAllDesigns(token: String): Flow<Resource<List<DesignDto>>>

    suspend fun getDesignById(id: String, token: String): Flow<Resource<DesignDto>>

}