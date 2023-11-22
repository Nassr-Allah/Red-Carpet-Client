package com.maduo.redcarpet.android.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maduo.redcarpet.android.MyApplicationTheme
import com.maduo.redcarpet.presentation.Black
import com.maduo.redcarpet.presentation.GrayLight
import com.maduo.redcarpet.presentation.Red
import com.maduo.redcarpet.presentation.WhiteSoft

@Composable
fun Chips(
    items: List<String> = listOf(),
    label: String,
    onSelected: (String) -> Unit
) {

    var selectedIndex by remember {
        mutableStateOf(-1)
    }

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        Text(
            text = label,
            color = Color(Black),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(15.dp))
        LazyRow {
            items(items.size) { index ->
                ChipItem(
                    label = items[index],
                    isSelected = index == selectedIndex,
                    onSelected = {
                        selectedIndex = index
                        onSelected(items[index])
                    }
                )
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }

}

@Composable
fun ChipsAsPairs(
    items: List<Pair<String, String>>,
    label: String,
    onSelected: (String) -> Unit
) {
    var selectedIndex by remember {
        mutableStateOf(-1)
    }

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        Text(
            text = label,
            color = Color(Black),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(15.dp))
        LazyRow {
            items(items.size) { index ->
                ChipItem(
                    label = items[index].second,
                    isSelected = index == selectedIndex,
                    onSelected = {
                        selectedIndex = index
                        onSelected(items[index].first)
                    }
                )
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
}

@Composable
fun ChipItem(label: String, isSelected: Boolean, onSelected: () -> Unit) {

    val borderColor = animateColorAsState(
        if (isSelected) Color(Red) else Color.Transparent
    )
    val textColor = animateColorAsState(
        if (isSelected) Color(Red) else Color(Black)
    )

    Box(
        modifier = Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(15))
            .clickable { onSelected() }
            .border(width = 1.dp, shape = RoundedCornerShape(12), color = borderColor.value)
            .background(Color(WhiteSoft))
            .padding(horizontal = 25.dp, vertical = 15.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = textColor.value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal
        )
    }
}
