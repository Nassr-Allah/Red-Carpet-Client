package com.maduo.redcarpet.android.presentation.feature_auth.login_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.maduo.redcarpet.android.presentation.components.CustomTextField
import com.maduo.redcarpet.android.presentation.navigation.SignupNavGraph
import com.ramcosta.composedestinations.annotation.Destination

@SignupNavGraph
@Destination
@Composable
fun PasswordSection(
    onPasswordChange: (String) -> Unit,
    onConfirmedPasswordChange: (String) -> Unit
) {
    var password by remember {
        mutableStateOf("")
    }
    var confirmedPassword by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTextField(
            value = password,
            label = "Password",
            onValueChange = {
                onPasswordChange(it)
                password = it
            },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(15.dp))
        CustomTextField(
            value = confirmedPassword,
            label = "Confirm Password",
            onValueChange = {
                confirmedPassword = it
                onConfirmedPasswordChange(it)
            },
            visualTransformation = PasswordVisualTransformation()
        )
    }
}