package com.maduo.redcarpet.android.presentation.feature_course.courses_screen

import com.maduo.redcarpet.domain.model.Course

data class CoursesScreenState(
    val isLoading: Boolean = false,
    val courses: List<Course> = emptyList(),
    val error: String = ""
)
