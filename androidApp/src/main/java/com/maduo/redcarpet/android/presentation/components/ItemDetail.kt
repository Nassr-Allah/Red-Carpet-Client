package com.maduo.redcarpet.android.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.maduo.redcarpet.presentation.Black
import com.maduo.redcarpet.presentation.Gray
import com.maduo.redcarpet.presentation.Red
import com.maduo.redcarpet.presentation.White

@Composable
fun ItemDetail(
    title: String,
    description: String,
    price: String = "",
    delay: Int = 0,
    isVisible: Boolean = false
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(
            tween(durationMillis = 500, delayMillis = delay)
        ) + slideInHorizontally(
            animationSpec = tween(durationMillis = 500, delayMillis = delay)
        )
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(Black)
                )
                Text(
                    text = price,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(Black)
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(Gray),
                modifier = Modifier
                    .animateContentSize(animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessLow
                    ))
                    .clickable { expanded = !expanded },
                maxLines = if (expanded) 10 else 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun ItemDetailWithCheckbox(
    title: String,
    description: String,
    price: String = "",
    delay: Int = 0,
    isVisible: Boolean = false,
    onCheck: (Boolean) -> Unit
) {

    var isChecked by remember {
        mutableStateOf(false)
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(
            tween(durationMillis = 500, delayMillis = delay)
        ) + slideInHorizontally(
            animationSpec = tween(durationMillis = 500, delayMillis = delay)
        )
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(Black)
                )
                Text(
                    text = price,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(Black)
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = {
                        onCheck(it)
                        isChecked = !isChecked
                    },
                    colors = CheckboxDefaults.colors(
                        checkmarkColor = Color(White),
                        checkedColor = Color(Red)
                    )
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(Gray)
                )
            }
        }
    }
}

@Composable
fun ItemDetailTitle(
    title: String,
    description: String = "",
    price: String,
    delay: Int = 0,
    isVisible: Boolean = false
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(
            tween(durationMillis = 500, delayMillis = delay)
        ) + slideInHorizontally(
            animationSpec = tween(durationMillis = 500, delayMillis = delay)
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(0.65f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(Black),
                    fontWeight = FontWeight.SemiBold
                )
                if (description.isNotEmpty()) {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(Gray)
                    )
                }
            }
            Text(
                text = price,
                style = MaterialTheme.typography.titleLarge,
                color = Color(Black),
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
