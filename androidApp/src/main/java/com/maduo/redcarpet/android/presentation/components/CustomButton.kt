package com.maduo.redcarpet.android.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maduo.redcarpet.android.MyApplicationTheme
import com.maduo.redcarpet.presentation.Red
import com.maduo.redcarpet.presentation.RedLight
import com.maduo.redcarpet.presentation.White
import com.maduo.redcarpet.android.R

@Composable
fun CustomButton(modifier: Modifier, label: String, color: Long = Red, onClick: () -> Unit) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = Color(color)
        ),
        onClick = { onClick() }
    ) {
        Text(text = label, color = Color(White))
    }
}

@Composable
fun CustomOutlinedButton(modifier: Modifier, label: String, icon: Int, onClick: () -> Unit) {
    OutlinedButton(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.outlinedButtonColors(
          contentColor = Color(Red),
          containerColor = Color(RedLight)
        ),
        border = BorderStroke(1.dp, Color(Red)),
        onClick = { onClick() }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = label, color = Color(Red))
            Icon(
                painter = painterResource(icon),
                contentDescription = "logout",
                tint = Color(Red),
                modifier = Modifier.size(25.dp)
            )
        }
    }
}

@Preview
@Composable
fun CustomButtonPreview() {
    MyApplicationTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column {
                CustomButton(modifier = Modifier.fillMaxWidth(0.8f), label = "CLICK") {

                }
                Spacer(modifier = Modifier.height(10.dp))
                CustomOutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    label = "CLICK",
                    icon = R.drawable.logout,
                ) {

                }
            }
        }
    }
}