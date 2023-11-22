package com.maduo.redcarpet.data.data_source

import com.maduo.redcarpet.data.remote.RestApi
import com.maduo.redcarpet.domain.dto.OriginalPatternDto
import com.maduo.redcarpet.domain.repositories.PatternRepository
import com.maduo.redcarpet.util.Resource
import io.ktor.client.call.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PatternRepositoryImpl(private val restApi: RestApi = RestApi()): PatternRepository {

    override suspend fun getPatternById(id: String, token: String): Flow<Resource<OriginalPatternDto>> = flow {
        emit(Resource.Loading<OriginalPatternDto>())
        try {
            val response = restApi.fetchPatternById(id, token)
            if (response.status.value == 200) {
                val pattern = response.body<OriginalPatternDto>()
                emit(Resource.Success(pattern))
            } else {
                val message = response.body<String>()
                emit(Resource.Error<OriginalPatternDto>(message))
            }
        } catch (e: Exception) {
            emit(Resource.Error<OriginalPatternDto>(e.message ?: "Unexpected Error"))
        }
    }
}