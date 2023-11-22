package com.maduo.redcarpet.android.presentation.feature_profile.personal_info_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maduo.redcarpet.domain.dto.ClientDto
import com.maduo.redcarpet.domain.repositories.ClientCacheRepository
import com.maduo.redcarpet.domain.repositories.ClientRepository
import com.maduo.redcarpet.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalInfoViewModel @Inject constructor(
    private val repository: ClientRepository,
    private val clientCacheRepository: ClientCacheRepository
) : ViewModel() {

    private val _state = MutableStateFlow(PersonalInfoScreenState())
    val state: MutableStateFlow<PersonalInfoScreenState> = _state

    init {
        getClientDetails()
    }

    val firstName = mutableStateOf("")
    val lastName = mutableStateOf("")
    val email = mutableStateOf("")
    val address = mutableStateOf("")
    val dateOfBirth = mutableStateOf("")
    val gender = mutableStateOf("")

    private fun getClientDetails() {
        viewModelScope.launch {
            val clientAuthDetails = clientCacheRepository.getClientFromDb()
            if (clientAuthDetails != null) {
                repository.getClientById(clientAuthDetails.clientId, clientAuthDetails.token).collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _state.value = _state.value.copy(isLoading = true, error = "")
                        }
                        is Resource.Success -> {
                            val client = result.data ?: ClientDto()
                            firstName.value = client.firstName
                            lastName.value = client.lastName
                            email.value = client.email
                            address.value = client.address
                            dateOfBirth.value = client.birthDate
                            gender.value = client.gender
                            _state.value =
                                _state.value.copy(isLoading = false, clientDto = result.data)
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

    fun updateClient() {
        val clientDto = _state.value.clientDto?.copy(
            firstName = firstName.value,
            lastName = lastName.value,
            email = email.value,
            address = address.value,
            birthDate = dateOfBirth.value,
            gender = gender.value
        )
        if (_state.value.clientDto != null) {
            viewModelScope.launch {
                val clientAuthDetails = clientCacheRepository.getClientFromDb()
                if (clientAuthDetails != null) {
                    repository.updateClient(clientDto!!, clientAuthDetails.token).collect { result ->
                        when (result) {
                            is Resource.Loading -> {
                                _state.value = _state.value.copy(isLoading = true, error = "")
                            }
                            is Resource.Success -> {
                                _state.value = _state.value.copy(
                                    isLoading = false,
                                    isClientUpdated = true,
                                    response = result.data ?: ""
                                )
                            }
                            is Resource.Error -> {
                                _state.value = _state.value.copy(
                                    isLoading = false,
                                    isClientUpdated = false,
                                    error = result.message ?: "Unexpected Error"
                                )
                            }
                        }
                    }
                } else {
                    _state.value = _state.value.copy(isLoading = false, error = "Unauthorized Request")
                }
            }
        } else {
            _state.value = _state.value.copy(isLoading = false, error = "Could not update the details!")
        }
    }

}