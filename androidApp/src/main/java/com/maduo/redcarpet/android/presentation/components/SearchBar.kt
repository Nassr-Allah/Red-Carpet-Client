package com.maduo.redcarpet.android.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maduo.redcarpet.android.MyApplicationTheme
import com.maduo.redcarpet.android.R
import com.maduo.redcarpet.presentation.*

@Composable
fun SearchBar(query: String, onValueChange: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().height(55.dp),
            value = query,
            onValueChange = { onValueChange(it) },
            placeholder = {
                Text(text = "Search...")
            },
            shape = RoundedCornerShape(12),
            colors = OutlinedTextFieldDefaults.colors(
                selectionColors = TextSelectionColors(
                    handleColor = Color.Transparent,
                    backgroundColor = Color(GraySolid)
                ),
                cursorColor = Color(Black),
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color(Black),
                unfocusedContainerColor = Color(GraySolid),
                focusedContainerColor = Color(GraySolid),
                focusedTextColor = Color(Black),
                focusedPlaceholderColor = Color(Gray),
                unfocusedPlaceholderColor = Color(Gray)
            )
        )

    }
}

@Preview
@Composable
fun SearchBarPreview() {
    MyApplicationTheme {
        Surface(color = Color(0xFFFEFEFE)) {
            SearchBar(query = "", onValueChange = {})
        }
    }
}