package com.maduo.redcarpet.android.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maduo.redcarpet.android.MyApplicationTheme
import com.maduo.redcarpet.presentation.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropDownMenu(
    list: List<String>,
    onItemSelected: (String) -> Unit
) {
    var selectedIndex by remember {
        mutableStateOf(0)
    }

    var selectedText by remember {
        mutableStateOf(list[0])
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedText,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor(),
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
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            list.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        selectedText = item
                        onItemSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun MenuPreview() {
    MyApplicationTheme {
        Surface(color = Color(Black), modifier = Modifier.height(640.dp)) {
            CustomDropDownMenu(list = listOf("1", "2", "3")) {}
        }
    }

}
