package com.maduo.redcarpet.android.presentation.feature_course.courses_screen

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maduo.redcarpet.data.mappers.mapToCourse
import com.maduo.redcarpet.domain.model.Course
import com.maduo.redcarpet.domain.repositories.ClientCacheRepository
import com.maduo.redcarpet.domain.repositories.CourseRepository
import com.maduo.redcarpet.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoursesViewModel @Inject constructor(
    private val repository: CourseRepository,
    private val clientCacheRepository: ClientCacheRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CoursesScreenState())
    val state: MutableStateFlow<CoursesScreenState> = _state

    private var allCourses = listOf<Course>()
    var filteredCourses = mutableStateListOf<Course>()

    val query = mutableStateOf("")

    init {
        getAllCourses()
    }

    private fun getAllCourses() {
        viewModelScope.launch {
            val clientAuthDetails = clientCacheRepository.getClientFromDb()
            Log.d("CoursesViewModel", clientAuthDetails.toString())
            if (clientAuthDetails != null) {
                repository.getAllCourses(clientAuthDetails.token).collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _state.value = _state.value.copy(isLoading = true, error = "")
                        }
                        is Resource.Success -> {
                            val courses = result.data?.map { it.mapToCourse() } ?: emptyList()
                            allCourses = courses
                            filteredCourses = allCourses.toMutableStateList()
                            _state.value = _state.value.copy(isLoading = false, courses = courses)
                        }
                        is Resource.Error -> {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                error = result.message ?: "Unexpected Error"
                            )
                        }
                    }
                }
            } else {
                _state.value = _state.value.copy(isLoading = false, error = "Unauthorized Request")
            }
        }
    }

    fun filterCourses(query: String) {
        filteredCourses = allCourses.filter { it.title.contains(query, true) }.toMutableStateList()
        _state.value = _state.value.copy(courses = filteredCourses)
    }

    fun filterByCategory(category: String) {
        if (category.equals("all", true)) {
            filteredCourses = allCourses.toMutableStateList()
            _state.value = _state.value.copy(courses = allCourses)
        } else {
            filteredCourses = allCourses.filter { it.category.equals(category, true) }.toMutableStateList()
            _state.value = _state.value.copy(courses = filteredCourses)
        }
    }

}