package com.maduo.redcarpet.android.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maduo.redcarpet.android.MyApplicationTheme
import com.maduo.redcarpet.presentation.Gray
import com.maduo.redcarpet.presentation.GrayLight
import com.maduo.redcarpet.presentation.GraySolid
import com.maduo.redcarpet.presentation.WhiteSoft
import com.valentinilk.shimmer.shimmer

@Composable
fun LoadingShimmerList() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(10) {
            ShimmerListItem()
        }
    }
}

@Composable
fun ShimmerListItem() {
    Column(modifier = Modifier.fillMaxHeight(0.4f).shimmer()) {
        Box(
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12))
                .background(Color(GraySolid))
        )
        Spacer(modifier = Modifier.height(5.dp))
        Box(
            modifier = Modifier
                .height(30.dp)
                .fillMaxWidth(0.8f)
                .clip(RoundedCornerShape(100))
                .background(Color(GraySolid))
        )
        Spacer(modifier = Modifier.height(5.dp))
        Box(
            modifier = Modifier
                .height(30.dp)
                .fillMaxWidth(0.3f)
                .clip(RoundedCornerShape(100))
                .background(Color(GraySolid))
        )
    }
}

@Preview
@Composable
fun LoadingShimmerListPreview() {
    MyApplicationTheme {
        Surface(color = Color(0xFFFEFEFE), modifier = Modifier.fillMaxSize()) {
            LoadingShimmerList()
        }
    }
}