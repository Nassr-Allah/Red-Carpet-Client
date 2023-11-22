package com.maduo.redcarpet.android.presentation.feature_auth.login_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maduo.redcarpet.android.MyApplicationTheme
import com.maduo.redcarpet.android.presentation.components.CustomTextField
import com.maduo.redcarpet.android.presentation.navigation.SignupNavGraph
import com.maduo.redcarpet.presentation.Black
import com.ramcosta.composedestinations.annotation.Destination

@Composable
fun SignupSection(
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit
) {

    var firstName by remember {
        mutableStateOf("")
    }
    var lastName by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTextField(
            value = firstName,
            label = "First Name",
            onValueChange = {
                firstName = it
                onFirstNameChange(it)
            }
        )
        Spacer(modifier = Modifier.height(15.dp))
        CustomTextField(
            value = lastName,
            label = "Last Name",
            onValueChange = {
                lastName = it
                onLastNameChange(it)
            }
        )
        Spacer(modifier = Modifier.height(15.dp))
        CustomTextField(
            value = email,
            label = "Email",
            onValueChange = {
                email = it
                onEmailChange(it)
            }
        )
    }
}

@Preview
@Composable
fun SignupSectionPreview() {
    MyApplicationTheme {
        Surface(color = Color(Black)) {
            SignupSection(
                onFirstNameChange = {},
                onLastNameChange = {},
                onEmailChange = {}
            )
        }
    }
}