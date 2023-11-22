package com.maduo.redcarpet.android.presentation.feature_course.course_details_srceen

import com.maduo.redcarpet.domain.model.Course

data class CourseDetailsScreenState(
    val isLoading: Boolean = false,
    val course: Course? = null,
    val isRegistrationCreated: Boolean = false,
    val response: String = "",
    val error: String = ""
)