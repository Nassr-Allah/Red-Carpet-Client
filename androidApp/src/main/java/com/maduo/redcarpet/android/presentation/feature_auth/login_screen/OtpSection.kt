package com.maduo.redcarpet.android.presentation.feature_auth.login_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.maduo.redcarpet.android.R
import com.maduo.redcarpet.android.presentation.components.CustomTextField
import com.maduo.redcarpet.android.presentation.navigation.SignupNavGraph
import com.maduo.redcarpet.presentation.*
import com.ramcosta.composedestinations.annotation.Destination

@SignupNavGraph
@Destination
@Composable
fun OtpSection(
    onPhoneNumberChange: (String) -> Unit,
    onSmsCodeChange: (String) -> Unit,
    onVerify: () -> Unit
) {

    val phoneNumberTxt = stringResource(R.string.phone_number)

    val phoneNumberPrefix = "0"
    var phoneNumber by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTextField(
            value = phoneNumber,
            label = phoneNumberTxt,
            onValueChange = {
                phoneNumber = it
                onPhoneNumberChange(it)
            },
            trailingIcon = {
                Text(
                    text = stringResource(R.string.verify),
                    color = Color(White),
                    modifier = Modifier.clickable {
                        onVerify()
                    }
                )
            },
            prefix = {
                Text(text = "0", color = Color(White))
            }
        )
        Spacer(modifier = Modifier.height(15.dp))
        SmsCodeSection(onSmsCodeChange = { onSmsCodeChange(it) })
    }

}

@Composable
fun SmsCodeSection(onSmsCodeChange: (String) -> Unit) {
    var digit1 by remember {
        mutableStateOf("")
    }
    var digit2 by remember {
        mutableStateOf("")
    }
    var digit3 by remember {
        mutableStateOf("")
    }
    var digit4 by remember {
        mutableStateOf("")
    }
    var digit5 by remember {
        mutableStateOf("")
    }
    var digit6 by remember {
        mutableStateOf("")
    }
    var code by remember {
        mutableStateOf("000000")
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SmsCodeField(value = digit1, modifier = Modifier.weight(0.15f, true)) {
            digit1 = it
            code = digit1+digit2+digit3+digit4+digit5+digit6
            onSmsCodeChange(code)
        }
        Spacer(modifier = Modifier.weight(0.05f))
        SmsCodeField(value = digit2, modifier = Modifier.weight(0.15f)) {
            digit2 = it
            code = digit1+digit2+digit3+digit4+digit5+digit6
            onSmsCodeChange(code)
        }
        Spacer(modifier = Modifier.weight(0.05f))
        SmsCodeField(value = digit3, modifier = Modifier.weight(0.15f)) {
            digit3 = it
            code = digit1+digit2+digit3+digit4+digit5+digit6
            onSmsCodeChange(code)
        }
        Spacer(modifier = Modifier.weight(0.05f))
        SmsCodeField(value = digit4, modifier = Modifier.weight(0.15f)) {
            digit4 = it
            code = digit1+digit2+digit3+digit4+digit5+digit6
            onSmsCodeChange(code)
        }
        Spacer(modifier = Modifier.weight(0.05f))
        SmsCodeField(value = digit5, modifier = Modifier.weight(0.15f)) {
            digit5 = it
            code = digit1+digit2+digit3+digit4+digit5+digit6
            onSmsCodeChange(code)
        }
        Spacer(modifier = Modifier.weight(0.05f))
        SmsCodeField(value = digit6, isLast = true, modifier = Modifier.weight(0.15f)) {
            digit6 = it
            code = digit1+digit2+digit3+digit4+digit5+digit6
            onSmsCodeChange(code)
        }
    }
}

@Composable
fun SmsCodeField(
    modifier: Modifier = Modifier,
    value: String,
    isLast: Boolean = false,
    onValueChange: (String) -> Unit
) {
    var digit by remember {
        mutableStateOf(value)
    }
    val focusManager = LocalFocusManager.current
    val maxLength = 1
    TextField(
        value = digit,
        modifier = modifier,
        onValueChange = {
            if (it.length <= maxLength) {
                digit = it
                onValueChange(it)
                if (!isLast && it.length == 1) {
                    focusManager.moveFocus(FocusDirection.Next)
                }
            }
        },
        shape = RoundedCornerShape(10),
        colors = TextFieldDefaults.colors(
            selectionColors = TextSelectionColors(
                handleColor = Color.Transparent,
                backgroundColor = Color.Transparent
            ),
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            focusedLabelColor = Color(White),
            unfocusedLabelColor = Color(Gray),
            cursorColor = Color(White),
            focusedTextColor = Color(White),
            errorIndicatorColor = Color(Red),
            errorLabelColor = Color(Red),
            focusedIndicatorColor = Color(White),
            unfocusedTextColor = Color(White),
            unfocusedIndicatorColor = Color(GrayLight),
            disabledContainerColor = Color.Transparent,
            disabledLabelColor = Color(Gray),
            disabledTextColor = Color(White),
            disabledIndicatorColor = Color(GrayLight)
        ),
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
    )
}
