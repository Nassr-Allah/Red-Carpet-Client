package com.maduo.redcarpet.android.presentation.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.maduo.redcarpet.domain.model.Design
import com.maduo.redcarpet.presentation.Black
import com.maduo.redcarpet.android.R
import com.maduo.redcarpet.presentation.Gray
import com.maduo.redcarpet.presentation.GraySolid
import com.valentinilk.shimmer.shimmer

@Composable
fun CustomListItem(design: Design, onClick: () -> Unit) {
    Log.d("DesignItem", design.toString())

    var isImageLoading by remember {
        mutableStateOf(false)
    }

    val modifier = if (isImageLoading) {
        Modifier
            .clip(RoundedCornerShape(15))
            .shimmer()
            .background(Color(GraySolid)) } else { Modifier }

    Column(
        modifier = Modifier
            .fillMaxHeight(0.4f)
            .clickable { onClick() }
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp),
            shape = RoundedCornerShape(15)
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = design.images[0],
                contentDescription = null,
                contentScale = ContentScale.Crop,
                onLoading = {
                    isImageLoading = true
                },
                onError = {
                    Log.d("DesignItem", "error: $it on design: ${design.name}")
                },
                onSuccess = {
                    Log.d("DesignItem", "image loaded successfully")
                    isImageLoading = false
                }
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = design.name,
            color = Color(Black),
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 5.dp)
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = "${design.price} DZD",
            color = Color(Black),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 5.dp)
        )
    }
}