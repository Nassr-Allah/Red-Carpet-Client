package com.maduo.redcarpet.android.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maduo.redcarpet.android.MyApplicationTheme
import com.maduo.redcarpet.presentation.Black
import com.maduo.redcarpet.presentation.Red
import com.maduo.redcarpet.presentation.White

@Composable
fun PagePicker(
    labelRight: String,
    labelLeft: String,
    onRightSelected: (Boolean) -> Unit,
    onLeftSelected: (Boolean) -> Unit
) {

    var isRight by remember {
        mutableStateOf(false)
    }
    val rightBgColor by animateColorAsState(
        if (isRight) Color(Red) else Color.Transparent
    )
    val rightBorderColor by animateColorAsState(
        if (isRight) Color.Transparent else Color(Red)
    )
    val rightTextColor by animateColorAsState(
        if (isRight) Color(White) else Color(Red)
    )

    var isLeft by remember {
        mutableStateOf(true)
    }
    val leftBgColor by animateColorAsState(
        if (isLeft) Color(Red) else Color.Transparent
    )
    val leftBorderColor by animateColorAsState(
        if (isLeft) Color.Transparent else Color(Red)
    )
    val leftTextColor by animateColorAsState(
        if (isLeft) Color(White) else Color(Red)
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .clickable {
                    isLeft = true
                    isRight = false
                    onLeftSelected(true)
                }
                .weight(0.45f, true)
                .clip(RoundedCornerShape(15))
                .background(leftBgColor)
                .border(1.dp, leftBorderColor, RoundedCornerShape(15))
                .padding(vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = labelLeft, color = leftTextColor)
        }

        Spacer(modifier = Modifier.weight(0.1f))

        Box(
            modifier = Modifier
                .clickable {
                    isRight = true
                    isLeft = false
                    onRightSelected(false)
                }
                .weight(0.45f, true)
                .clip(RoundedCornerShape(15))
                .background(rightBgColor)
                .border(1.dp, rightBorderColor, RoundedCornerShape(15))
                .padding(vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = labelRight, color = rightTextColor)
        }
    }
}