package com.maduo.redcarpet.data.data_source

import com.maduo.redcarpet.data.remote.RestApi
import com.maduo.redcarpet.domain.dto.DesignDto
import com.maduo.redcarpet.domain.repositories.DesignRepository
import com.maduo.redcarpet.util.Resource
import io.ktor.client.call.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DesignRepositoryImpl(private val restApi: RestApi = RestApi()): DesignRepository {

    override suspend fun getAllDesigns(token: String): Flow<Resource<List<DesignDto>>> = flow {
        emit(Resource.Loading<List<DesignDto>>())
        try {
            val response = restApi.fetchAllDesigns(token)
            if (response.status.value == 200) {
                val designs = response.body<List<DesignDto>>()
                emit(Resource.Success(designs))
            } else {
                val message = response.body<String>()
                emit(Resource.Error<List<DesignDto>>(message))
            }
        } catch (e: Exception) {
            emit(Resource.Error<List<DesignDto>>(e.message ?: "Unexpected Error"))
        }
    }

    override suspend fun getDesignById(id: String, token: String): Flow<Resource<DesignDto>> = flow {
        println("Design Repository:  called function...")
        emit(Resource.Loading<DesignDto>())
        try {
            val response = restApi.fetchDesignById(id, token)
            if (response.status.value == 200) {
                println("Design Repository:  Success")
                val design = response.body<DesignDto>()
                emit(Resource.Success(design))
            } else {
                println("Design Repository:  Failure")
                val message = response.body<String>()
                emit(Resource.Error<DesignDto>(message))
            }
        } catch (e: Exception) {
            println("Design Repository:  exception: $e")
            emit(Resource.Error<DesignDto>(e.message ?: "Unexpected Error"))
        }
    }
}