package com.maduo.redcarpet.android.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.maduo.redcarpet.presentation.Gray
import com.maduo.redcarpet.presentation.GraySolid
import com.maduo.redcarpet.presentation.WhiteSoft
import com.valentinilk.shimmer.shimmer

@Composable
fun BoxShimmerLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12))
            .shimmer()
            .background(Color(GraySolid))
    )
}