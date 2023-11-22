package com.maduo.redcarpet.data.mappers

import com.maduo.redcarpet.domain.dto.CourseCategoryDto
import com.maduo.redcarpet.domain.dto.CourseDto
import com.maduo.redcarpet.domain.model.Course

fun CourseDto.mapToCourse(): Course {
    return Course(
        id = id,
        title = title,
        price = price,
        description = description,
        type = type,
        duration = duration,
        category = category,
        image = image
    )
}