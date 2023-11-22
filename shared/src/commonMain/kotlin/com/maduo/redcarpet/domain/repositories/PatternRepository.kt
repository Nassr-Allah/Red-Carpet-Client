package com.maduo.redcarpet.domain.repositories

import com.maduo.redcarpet.domain.dto.OriginalPatternDto
import com.maduo.redcarpet.util.Resource
import kotlinx.coroutines.flow.Flow

interface PatternRepository {

    suspend fun getPatternById(id: String, token: String): Flow<Resource<OriginalPatternDto>>

}