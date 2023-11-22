package com.maduo.redcarpet.android.presentation.feature_auth.login_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maduo.redcarpet.android.MyApplicationTheme
import com.maduo.redcarpet.android.presentation.components.CustomTextField
import com.maduo.redcarpet.android.R
import com.maduo.redcarpet.presentation.Black
import com.maduo.redcarpet.presentation.Gray
import com.maduo.redcarpet.presentation.White

@Composable
fun LoginSection(
    isSelected: Boolean,
    onPhoneNumberChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit
) {

    var phoneNumber by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    var isPasswordVisible by remember {
        mutableStateOf(false)
    }
    val tint = animateColorAsState(
        if (isPasswordVisible) Color(White) else Color(Gray)
    )
    val visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
    val phoneInput = KeyboardOptions(keyboardType = KeyboardType.Number)
    val passwordInput = KeyboardOptions(keyboardType = KeyboardType.Password)

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTextField(
            value = phoneNumber,
            label = stringResource(R.string.phone_number),
            onValueChange = {
                phoneNumber = it
                onPhoneNumberChange(it)
            },
            keyboardOptions = phoneInput,
            prefix = {
                Text(text = "0", color = Color(White))
            }
        )
        Spacer(modifier = Modifier.height(15.dp))
        CustomTextField(
            value = password,
            label = stringResource(R.string.password),
            onValueChange = {
                password = it
                onPasswordChange(it)
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_eye),
                    contentDescription = "Eye",
                    tint = tint.value,
                    modifier = Modifier.clickable {
                        isPasswordVisible = !isPasswordVisible
                    }
                )
            },
            visualTransformation = visualTransformation,
            keyboardOptions = passwordInput
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.forgot_password),
                color = Color(White),
                textDecoration = TextDecoration.Underline
            )
        }
    }
}

@Preview
@Composable
fun LoginSectionPreview() {
    MyApplicationTheme {
        Surface(color = Color(Black)) {
            LoginSection(
                isSelected = true,
                onPhoneNumberChange = {},
                onPasswordChange = {}
            )
        }
    }
}