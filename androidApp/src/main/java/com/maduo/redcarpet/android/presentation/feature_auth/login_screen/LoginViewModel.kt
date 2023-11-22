package com.maduo.redcarpet.android.presentation.feature_auth.login_screen

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.PhoneAuthProvider
import com.maduo.redcarpet.domain.dto.AuthRequestDto
import com.maduo.redcarpet.domain.dto.ClientDto
import com.maduo.redcarpet.domain.entity.ClientAuthDetails
import com.maduo.redcarpet.domain.firebase.FirebasePhoneAuthManager
import com.maduo.redcarpet.domain.repositories.AuthRepository
import com.maduo.redcarpet.domain.repositories.ClientCacheRepository
import com.maduo.redcarpet.domain.repositories.ClientRepository
import com.maduo.redcarpet.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val clientRepository: ClientRepository,
    private val firebasePhoneAuth: FirebasePhoneAuthManager,
    private val clientCacheRepository: ClientCacheRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LoginScreenState())
    val state: MutableStateFlow<LoginScreenState> = _state

    val phoneNumber = mutableStateOf("")
    val password = mutableStateOf("")

    val firstName = mutableStateOf("")
    val lastName = mutableStateOf("")
    val email = mutableStateOf("")

    val dateOfBirth = mutableStateOf("")
    val address = mutableStateOf("")
    val gender = mutableStateOf("Male")

    val otpPhoneNumber = mutableStateOf("")
    val signupPassword = mutableStateOf("")
    val confirmedPassword = mutableStateOf("")

    val isPhoneNumberValid = mutableStateOf(true)
    val isPasswordValid = mutableStateOf(true)

    val input = mutableStateOf("")
    val verificationId = mutableStateOf("")

    fun loginClient() {
        val authRequest = AuthRequestDto("+213${phoneNumber.value}", password.value)
        Log.d("LoginViewModel", "started call")
        viewModelScope.launch {
            clientCacheRepository.deleteClientFromDb()
            authRepository.login(authRequest).collect { result ->
                when(result) {
                    is Resource.Loading -> {
                        Log.d("LoginViewModel", "Loading...")
                        _state.value = _state.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        Log.d("LoginViewModel", "success")
                        val authResponse = result.data
                        if (authResponse != null) {
                            val clientAuthDetails = ClientAuthDetails(
                                token = authResponse.token,
                                clientId = authResponse.clientId
                            )
                            clientCacheRepository.insertClient(clientAuthDetails)
                            _state.value =
                                _state.value.copy(isLoading = false, isLogged = true, error = "")
                        } else {
                            _state.value = _state.value.copy(isLoading = false, isLogged = false, error = "null response")
                        }
                    }
                    is Resource.Error -> {
                        Log.d("LoginViewModel", "error: ${result.message}")
                        _state.value = _state.value.copy(isLoading = false, isLogged = false, error = result.message ?: "Unexpected Error")
                    }
                }
            }
        }
    }

    fun createClient() {
        val client = ClientDto(
            firstName = firstName.value,
            lastName = lastName.value,
            email = email.value,
            phoneNumber = otpPhoneNumber.value,
            address = address.value,
            birthDate = dateOfBirth.value,
            gender = gender.value,
            password = signupPassword.value
        )
        Log.d("LoginViewModel", client.toString())
        viewModelScope.launch {
            clientRepository.createClient(client).collect { result ->
                when(result) {
                    is Resource.Loading -> {
                        Log.d("LoginViewModel", "Loading...")
                        _state.value = _state.value.copy(isLoading = true, error = "")
                    }
                    is Resource.Success -> {
                        Log.d("LoginViewModel", "success creation: ${result.data}")
                        loginClient()
                        _state.value = _state.value.copy(isLoading = false, isClientCrated = true, error = "")
                    }
                    is Resource.Error -> {
                        Log.d("LoginViewModel", "error: ${result.message}")
                        _state.value = _state.value.copy(
                            isLoading = false,
                            isClientCrated = false,
                            error = result.message ?: "Unknown Error"

                        )
                    }
                }
            }
        }
    }

    fun verifyPhoneNumber(activity: Activity) {
        firebasePhoneAuth.setActivity(activity)
        otpPhoneNumber.value = "+213${otpPhoneNumber.value}"
        viewModelScope.launch {
            firebasePhoneAuth.verifyPhoneNumber(otpPhoneNumber.value).collect { result ->
                when(result) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true, error = "")
                    }
                    is Resource.Success -> {
                        _state.value = _state.value.copy(isLoading = false, isCodeSent = true)
                        verificationId.value = result.data ?: ""
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(isLoading = false, error = result.message ?: "Unexpected Error")
                    }
                }
            }
        }
    }

    fun isCodeCorrect() {
        Log.d("LoginViewModel", verificationId.value)
        Log.d("LoginViewModel", input.value)
        val credential = PhoneAuthProvider.getCredential(verificationId.value, input.value)
        Log.d("LoginViewModel", "smsCode: ${credential.smsCode}")
        viewModelScope.launch {
            firebasePhoneAuth.signInUserWithCredential(credential).collect { result ->
                when(result) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true, error = "")
                    }
                    is Resource.Success -> {
                        _state.value = _state.value.copy(isLoading = false, isPhoneVerified = true, error = "")
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(isLoading = false, isPhoneVerified = false, error = result.message ?: "Unknown Error")
                    }
                }
            }
        }
    }

    fun validateInput(vararg fields: String): Boolean {
        fields.forEach { field ->
            if (field.isEmpty()) return false
        }
        return true
    }

}