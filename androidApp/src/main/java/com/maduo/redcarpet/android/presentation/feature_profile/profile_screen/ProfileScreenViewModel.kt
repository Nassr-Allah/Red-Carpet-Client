package com.maduo.redcarpet.android.presentation.feature_profile.profile_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maduo.redcarpet.domain.repositories.ClientCacheRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val clientCacheRepository: ClientCacheRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileScreenState())
    val state: MutableStateFlow<ProfileScreenState> = _state

    fun logOut() {
        try {
            viewModelScope.launch {
                _state.value = _state.value.copy(isLoading = true, error = "")
                clientCacheRepository.deleteClientFromDb()
                _state.value = _state.value.copy(isLoading = false, isLoggedOut = true)
            }
        } catch (e: Exception) {
            _state.value = _state.value.copy(isLoading = false, isLoggedOut = false, error = e.localizedMessage ?: "Unknown Error")
        }
    }

}