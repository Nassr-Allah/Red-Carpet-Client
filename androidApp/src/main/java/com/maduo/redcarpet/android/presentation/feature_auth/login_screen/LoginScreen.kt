package com.maduo.redcarpet.android.presentation.feature_auth.login_screen

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.maduo.redcarpet.android.R
import com.maduo.redcarpet.android.AppActivity
import com.maduo.redcarpet.android.MainActivity
import com.maduo.redcarpet.android.MyApplicationTheme
import com.maduo.redcarpet.android.presentation.components.BackgroundImage
import com.maduo.redcarpet.android.presentation.components.CustomButton
import com.maduo.redcarpet.android.presentation.components.LoadingAnimation
import com.maduo.redcarpet.android.presentation.components.PagePicker
import com.maduo.redcarpet.android.presentation.navigation.SignupNavGraph
import com.maduo.redcarpet.presentation.Black
import com.maduo.redcarpet.presentation.White
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class)
@SignupNavGraph(start = true)
@Destination
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
) {

    val state = viewModel.state.collectAsState()
    val context = LocalContext.current.applicationContext
    val activity = LocalContext.current as Activity

    var isLogin by remember {
        mutableStateOf(true)
    }

    var sectionIndex by remember {
        mutableStateOf(1)
    }

    val loginText = stringResource(R.string.login)
    val nextText = stringResource(R.string.next)
    val clientLoggedIn = stringResource(R.string.client_logged_in)
    val clientCreated = stringResource(R.string.client_created)
    val error = stringResource(R.string.error)
    val codeSent = stringResource(R.string.code_sent)
    val confirm = stringResource(R.string.confirm)
    val emptyFields = stringResource(R.string.empty_fields)
    val phoneNotVerified = stringResource(R.string.phone_not_verified)

    var buttonLabel by remember {
        mutableStateOf(loginText)
    }

    LaunchedEffect(key1 = state.value) {
        if (!state.value.isLoading) {
            if (state.value.isLogged) {
                Toast.makeText(context, clientLoggedIn, Toast.LENGTH_SHORT).show()
                val intent = Intent(activity, AppActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
            if (state.value.isCodeSent && sectionIndex == 3) {
                Toast.makeText(context, codeSent, Toast.LENGTH_SHORT).show()
            }
            if (state.value.isClientCrated) {
                Toast.makeText(context, clientCreated, Toast.LENGTH_SHORT).show()
                isLogin = true
            }
            if (state.value.error.isNotEmpty()) {
                Toast.makeText(context, state.value.error, Toast.LENGTH_SHORT).show()
                Log.d("LoginScreen", state.value.error)
            }
            if (state.value.isPhoneVerified) {
                sectionIndex++
            }
        }
    }

    BackgroundImage()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = stringResource(R.string.login_to_your_account),
                color = Color(White),
                style = MaterialTheme.typography.titleLarge
            )
            PagePicker(
                labelRight = stringResource(R.string.singup),
                labelLeft = stringResource(R.string.login),
                onLeftSelected = {
                    isLogin = true
                    sectionIndex = 1
                    buttonLabel = loginText
                },
                onRightSelected = {
                    isLogin = false
                    buttonLabel = nextText
                }
            )
            AnimatedContent(
                targetState = isLogin,
                transitionSpec = {
                    slideInHorizontally {
                        if (isLogin) -it else it
                    } + fadeIn() with slideOutHorizontally {
                        if (isLogin) it else -it
                    } + fadeOut()
                }
            ) {
                if (isLogin) {
                    LoginSection(
                        isSelected = isLogin,
                        onPhoneNumberChange = {
                            viewModel.phoneNumber.value = it
                        },
                        onPasswordChange = {
                            viewModel.password.value = it
                        }
                    )
                } else {
                    AnimatedContent(targetState = sectionIndex) {
                        when(sectionIndex) {
                            1 -> {
                                buttonLabel = nextText
                                SignupSection(
                                    onFirstNameChange = {
                                        viewModel.firstName.value = it
                                    },
                                    onLastNameChange = {
                                        viewModel.lastName.value = it
                                    },
                                    onEmailChange = {
                                        viewModel.email.value = it
                                    }
                                )
                            }
                            2 -> {
                                DetailsSection(
                                    onBirthDateChange = {
                                        viewModel.dateOfBirth.value = it
                                    },
                                    onAddressChange = {
                                        viewModel.address.value = it
                                    },
                                    onGenderChange = {
                                        viewModel.gender.value = it
                                    }
                                )
                            }
                            3 -> {
                                OtpSection(
                                    onPhoneNumberChange = {
                                        viewModel.otpPhoneNumber.value = it
                                    },
                                    onSmsCodeChange = {
                                        viewModel.input.value = it
                                    },
                                    onVerify = {
                                        if (viewModel.otpPhoneNumber.value.isNotEmpty()) {
                                            viewModel.verifyPhoneNumber(activity)
                                        } else {
                                            Toast.makeText(context, emptyFields, Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                )
                            }
                            4 -> {
                                buttonLabel = confirm
                                PasswordSection(
                                    onPasswordChange = {
                                        viewModel.signupPassword.value = it
                                    },
                                    onConfirmedPasswordChange = {
                                        viewModel.confirmedPassword.value = it
                                    }
                                )
                            }
                        }
                    }
                }
            }
            if (state.value.isLoading) {
                LoadingAnimation(
                    circleColor = Color(White),
                    circleSize = 10.dp
                )
            } else {
                CustomButton(modifier = Modifier.fillMaxWidth(0.85f), label = buttonLabel) {
                    if (isLogin) {
                        viewModel.loginClient()
                    } else {
                        when(sectionIndex) {
                            1 -> {
                                val isValid = viewModel.validateInput(
                                    viewModel.firstName.value,
                                    viewModel.lastName.value,
                                    viewModel.email.value
                                )
                                if (isValid) {
                                    sectionIndex++
                                } else {
                                    Toast.makeText(context, emptyFields, Toast.LENGTH_SHORT).show()
                                }
                            }
                            2 -> {
                                val isValid = viewModel.validateInput(
                                    viewModel.dateOfBirth.value,
                                    viewModel.address.value
                                )
                                if (isValid) {
                                    sectionIndex++
                                } else {
                                    Toast.makeText(context, emptyFields, Toast.LENGTH_SHORT).show()
                                }
                            }
                            3 -> {
                                val isValid = viewModel.validateInput(
                                    viewModel.otpPhoneNumber.value,
                                    viewModel.input.value
                                )
                                if (viewModel.otpPhoneNumber.value.isEmpty()) {
                                    Toast.makeText(
                                        context,
                                        emptyFields,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                if (viewModel.verificationId.value.isNotEmpty()) {
                                    if (isValid) {
                                        viewModel.isCodeCorrect()
                                    } else {
                                        Toast.makeText(
                                            context,
                                            emptyFields,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } else {
                                    Toast.makeText(context, phoneNotVerified, Toast.LENGTH_SHORT).show()
                                }
                            }
                            4 -> {
                                val isValid = viewModel.validateInput(
                                    viewModel.signupPassword.value
                                )
                                if (isValid) {
                                    viewModel.createClient()
                                } else {
                                    Toast.makeText(context, emptyFields, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
