package com.maduo.redcarpet.android.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.maduo.redcarpet.presentation.Black
import com.maduo.redcarpet.presentation.WhiteSoft

@Composable
fun OrderDetail(label: String, description: String, price: String = "", isLoading: Boolean) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            color = Color(Black),
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(10.dp))
        if (isLoading) {
            BoxShimmerLoading(modifier = Modifier.fillMaxWidth().height(45.dp))
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12))
                    .background(Color(WhiteSoft))
                    .padding(horizontal = 20.dp, vertical = 15.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(Black),
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = price,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(Black),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}