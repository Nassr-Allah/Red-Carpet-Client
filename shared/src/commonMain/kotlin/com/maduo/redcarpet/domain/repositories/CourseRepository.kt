package com.maduo.redcarpet.domain.repositories

import com.maduo.redcarpet.domain.dto.CourseDto
import com.maduo.redcarpet.domain.model.Course
import com.maduo.redcarpet.util.Resource
import kotlinx.coroutines.flow.Flow

interface CourseRepository {

    suspend fun getAllCourses(token: String): Flow<Resource<List<CourseDto>>>

    suspend fun getCourseById(id: String, token: String): Flow<Resource<CourseDto>>

}