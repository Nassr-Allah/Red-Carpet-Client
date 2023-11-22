package com.maduo.redcarpet.android.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maduo.redcarpet.android.MyApplicationTheme
import com.maduo.redcarpet.presentation.*

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    maxLines: Int = 1,
    enabled: Boolean = true
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = { onValueChange(it) },
        label = { Text(text = label) },
        trailingIcon = trailingIcon,
        leadingIcon = leadingIcon,
        prefix = prefix,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        maxLines = maxLines,
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
        enabled = enabled
    )
}

@Preview
@Composable
fun CustomTextFieldPreview() {
    MyApplicationTheme {
        Box(modifier = Modifier.padding(20.dp).background(Color(Black))) {
            CustomTextField(value = "", label = "Label", onValueChange = {})
        }
    }
}