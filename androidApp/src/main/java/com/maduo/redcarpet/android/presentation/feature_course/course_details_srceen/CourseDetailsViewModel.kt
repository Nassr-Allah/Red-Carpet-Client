package com.maduo.redcarpet.android.presentation.feature_course.course_details_srceen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maduo.redcarpet.data.mappers.mapToCourse
import com.maduo.redcarpet.domain.dto.RegistrationDto
import com.maduo.redcarpet.domain.repositories.ClientCacheRepository
import com.maduo.redcarpet.domain.repositories.CourseRepository
import com.maduo.redcarpet.domain.repositories.RegistrationRepository
import com.maduo.redcarpet.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CourseDetailsViewModel @Inject constructor(
    private val registrationRepository: RegistrationRepository,
    private val courseRepository: CourseRepository,
    private val clientCacheRepository: ClientCacheRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CourseDetailsScreenState())
    val state: MutableStateFlow<CourseDetailsScreenState> = _state

    fun getCourseDetails(courseId: String) {
        viewModelScope.launch {
            val clientAuthDetails = clientCacheRepository.getClientFromDb()
            if (clientAuthDetails != null) {
                courseRepository.getCourseById(courseId, clientAuthDetails.token).collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _state.value = _state.value.copy(isLoading = true, error = "")
                        }
                        is Resource.Success -> {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                course = result.data?.mapToCourse()
                            )
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
                _state.value = _state.value.copy(isLoading = false, error = "Unauthorized")
            }
        }
    }

    fun createRegistration(courseId: String) {
        val formatter = SimpleDateFormat("dd MMMM yyyy", Locale("en"))
        val date = formatter.format(Date())

        viewModelScope.launch {
            val clientAuthDetails = clientCacheRepository.getClientFromDb()
            if (clientAuthDetails != null) {
                val registrationDto = RegistrationDto(
                    clientId = clientAuthDetails.clientId,
                    courseId = courseId,
                    date = date
                )
                registrationRepository.createRegistration(registrationDto, clientAuthDetails.token).collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _state.value = _state.value.copy(isLoading = true, error = "")
                        }
                        is Resource.Success -> {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                isRegistrationCreated = true,
                                response = result.data ?: "",
                                error = ""
                            )
                        }
                        is Resource.Error -> {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                isRegistrationCreated = false,
                                error = result.message ?: "Unexpected Error"
                            )
                        }
                    }
                }
            }
        }
    }

}