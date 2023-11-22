package com.maduo.redcarpet.android.presentation.feature_collection.collection_screen

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.maduo.redcarpet.android.R
import com.maduo.redcarpet.android.presentation.components.*
import com.maduo.redcarpet.android.presentation.navigation.MainAppNavGraph
import com.maduo.redcarpet.data.mappers.mapToDesign
import com.maduo.redcarpet.domain.dto.OriginalPatternDto
import com.maduo.redcarpet.domain.model.Design
import com.maduo.redcarpet.presentation.Black
import com.maduo.redcarpet.presentation.Gray
import com.ramcosta.composedestinations.annotation.Destination
import com.valentinilk.shimmer.shimmer

@MainAppNavGraph
@Destination
@Composable
fun CollectionScreen(viewModel: CollectionViewModel = hiltViewModel()) {

    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current.applicationContext
    val activity = LocalContext.current as Activity

    var isDesignsPage by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(key1 = state) {
        if (!state.isLoading) {
            if (state.error.isNotEmpty()) {
                Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
            }
            if (state.error.equals("Unauthorized", true)) {
                activity.finish()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DesignListScreenHeader()
            Spacer(modifier = Modifier.height(30.dp))
            PagePicker(
                labelRight = stringResource(R.string.original_pattern),
                labelLeft = stringResource(R.string.design),
                onRightSelected = { isDesignsPage = false },
                onLeftSelected = { isDesignsPage = true }
            )
            Spacer(modifier = Modifier.height(30.dp))
            if (state.isLoading) {
                LoadingShimmerList()
            } else {
                if (isDesignsPage) {
                    DesignList(
                        designs = state.collection?.designs ?: emptyList(),
                        onClick = {}
                    )
                } else {
                    PatternList(
                        patterns = state.collection?.patterns ?: emptyList(),
                        onClick = {}
                    )
                }
            }
        }
    }
}


@Composable
fun DesignListScreenHeader() {
    Column {
        ScreenTitleSubtitle(title = stringResource(R.string.my_collection), subtitle = stringResource(R.string.browse_through_items))
        Spacer(modifier = Modifier.height(25.dp))
    }
}

@Composable
fun DesignList(designs: List<String>, onClick: (String) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(designs) { design ->
            PatternItem(pattern = design) {
                onClick(design)
            }
        }
    }
}

@Composable
fun PatternList(patterns: List<String>, onClick: (String) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(patterns) { pattern ->
            PatternItem(pattern = pattern) {
                onClick(pattern)
            }
        }
    }
}


@Composable
fun PatternItem(pattern: String, onClick: () -> Unit) {

    val dzdText = stringResource(R.string.DZD)

    var isImageLoading by remember {
        mutableStateOf(false)
    }

    val modifier = if (isImageLoading) {
        Modifier
            .clip(RoundedCornerShape(15))
            .shimmer()
            .background(Color(Gray)) } else { Modifier }

    Column(
        modifier = Modifier
            .fillMaxHeight(0.4f)
            .clickable { onClick() }
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(220.dp),
            shape = RoundedCornerShape(15)
        ) {
            AsyncImage(
                model = pattern,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                onLoading = {
                    isImageLoading = true
                },
                onError = {

                },
                onSuccess = {
                    Log.d("DesignItem", "image loaded successfully")
                    isImageLoading = false
                }
            )
        }
        Spacer(modifier = Modifier.height(3.dp))
    }
}
