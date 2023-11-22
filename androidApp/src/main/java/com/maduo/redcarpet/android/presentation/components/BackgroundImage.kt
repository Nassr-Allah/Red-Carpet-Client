package com.maduo.redcarpet.android.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maduo.redcarpet.android.MyApplicationTheme
import com.maduo.redcarpet.android.R
import com.maduo.redcarpet.presentation.Black
import com.skydoves.cloudy.Cloudy

@Composable
fun BackgroundImage() {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(R.drawable.bg),
                contentDescription = "Background Image",
                contentScale = ContentScale.Crop,
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.8f)
                    .background(Color.Black)
            )
        }
}

@Preview(device = Devices.PIXEL_3A)
@Composable
fun BackgroundImagePreview() {
    MyApplicationTheme {
        Surface(color = Color(Black)) {
            BackgroundImage()
        }
    }
}