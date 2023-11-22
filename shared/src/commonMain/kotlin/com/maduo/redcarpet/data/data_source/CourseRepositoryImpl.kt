package com.maduo.redcarpet.data.data_source

import com.maduo.redcarpet.data.remote.RestApi
import com.maduo.redcarpet.domain.dto.CourseDto
import com.maduo.redcarpet.domain.model.Course
import com.maduo.redcarpet.domain.repositories.CourseRepository
import com.maduo.redcarpet.util.Resource
import io.ktor.client.call.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CourseRepositoryImpl(private val restApi: RestApi = RestApi()): CourseRepository {

    override suspend fun getAllCourses(token: String): Flow<Resource<List<CourseDto>>> = flow {
        emit(Resource.Loading<List<CourseDto>>())
        try {
            val response = restApi.fetchAllCourses(token)
            if (response.status.value == 200) {
                val courses = response.body<List<CourseDto>>()
                emit(Resource.Success(courses))
            } else {
                val message = response.body<String>()
                emit(Resource.Error<List<CourseDto>>(message))
            }
        } catch (e: Exception) {
            emit(Resource.Error<List<CourseDto>>(e.message ?: "Unexpected Error"))
        }
    }

    override suspend fun getCourseById(id: String, token: String): Flow<Resource<CourseDto>> = flow {
        emit(Resource.Loading<CourseDto>())
        try {
            val response = restApi.fetchCourseById(id, token)
            if (response.status.value == 200) {
                val course = response.body<CourseDto>()
                emit(Resource.Success(course))
            } else {
                val message = response.body<String>()
                emit(Resource.Error<CourseDto>(message))
            }
        } catch (e: Exception) {
            emit(Resource.Error<CourseDto>(e.message ?: "Unexpected Error"))
        }
    }
}