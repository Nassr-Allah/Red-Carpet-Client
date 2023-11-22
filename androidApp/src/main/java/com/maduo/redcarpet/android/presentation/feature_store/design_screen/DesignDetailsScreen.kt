package com.maduo.redcarpet.android.presentation.feature_store.design_screen

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.maduo.redcarpet.android.MyApplicationTheme
import com.maduo.redcarpet.android.presentation.components.*
import com.maduo.redcarpet.domain.model.Design
import com.maduo.redcarpet.presentation.Black
import com.maduo.redcarpet.presentation.WhiteSoft
import com.maduo.redcarpet.android.R
import com.maduo.redcarpet.android.presentation.destinations.ConfirmationScreenDestination
import com.maduo.redcarpet.android.presentation.navigation.MainAppNavGraph
import com.maduo.redcarpet.presentation.GraySolid
import com.maduo.redcarpet.presentation.Red
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.valentinilk.shimmer.shimmer

@MainAppNavGraph
@Destination
@Composable
fun DesignDetailsScreen(
    designId: String,
    viewModel: DesignDetailsViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current.applicationContext

    var image by remember {
        mutableStateOf("")
    }

    var isVisible by remember {
        mutableStateOf(false)
    }

    var isImageLoading by remember {
        mutableStateOf(true)
    }

    var design by remember {
        mutableStateOf(viewModel.design.value)
    }

    val modifier = if (isImageLoading) Modifier
        .shimmer()
        .background(Color(GraySolid)) else Modifier

    LaunchedEffect(key1 = true) {
        viewModel.designId.value = designId
        viewModel.getDesignById()
    }

    LaunchedEffect(key1 = state) {
        if (!state.isLoading) {
            if (state.error.isNotEmpty()) {
                Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
            }
            if (state.isOrderCreated && state.response.isNotEmpty()) {
                navigator.navigate(ConfirmationScreenDestination)
            }
            if (state.design != null) {
                image = state.design.images[0]
                design = state.design
                isVisible = true
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
            ) {
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier.fillMaxSize(),
                    onSuccess = {
                        isImageLoading = false
                    },
                    onLoading = {
                        isImageLoading = true
                    }
                )
                Row(modifier = Modifier.padding(20.dp)) {
                    IconRoundBackground(icon = R.drawable.arrow_left) {
                        navigator.navigateUp()
                    }
                }
            }
            Spacer(modifier = Modifier.height(25.dp))
            DetailsSection(
                design = design ?: Design(category = ""),
                isLoading = state.isLoading,
                isVisible = isVisible,
                onCheck = { viewModel.order.value.isPatternIncluded = it },
                onClick = {
                    viewModel.order.value.totalPrice = it
                    viewModel.sendOrder()
                },
                onImageClick = { image = it },
                onSizeClick = { viewModel.size.value = it }
            )
        }
    }
}

@Composable
fun DetailsSection(
    design: Design,
    isLoading: Boolean,
    isVisible: Boolean,
    onCheck: (Boolean) -> Unit,
    onClick: (Int) -> Unit,
    onImageClick: (String) -> Unit,
    onSizeClick: (String) -> Unit
) {

    val scrollState = rememberScrollState()
    var totalPrice by remember {
        mutableStateOf(design.price + design.deliveryPrice)
    }
    val sendOrderText = stringResource(R.string.send_order)

    LaunchedEffect(key1 = design) {
        totalPrice = design.price + design.deliveryPrice
    }

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ItemDetailTitle(
            title = design.name,
            price = "${design.price} DA",
            description = design.designer,
            delay = 200,
            isVisible = isVisible
        )
        Spacer(modifier = Modifier.height(25.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn(
                    tween(durationMillis = 500, delayMillis = 500)
                ) + slideInHorizontally(
                    animationSpec = tween(durationMillis = 500, delayMillis = 500)
                )
            ) {
                ImageChipsList(list = design.images, onClick = { onImageClick(it) })
            }
        }
        Spacer(modifier = Modifier.height(25.dp))
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(
                tween(durationMillis = 500, delayMillis = 800)
            ) + slideInHorizontally(
                animationSpec = tween(durationMillis = 500, delayMillis = 800)
            )
        ) {
            SizeChipsList(list = design.sizes) {
                onSizeClick(it)
            }
        }
        Spacer(modifier = Modifier.height(25.dp))
        ItemDetail(title = stringResource(R.string.description), description = design.description, delay = 1100, isVisible = isVisible)
        Spacer(modifier = Modifier.height(25.dp))
        ItemDetail(
            title = stringResource(R.string.delivery),
            description = design.deliveryPlaces,
            price = "${design.price} DA",
            delay = 1400,
            isVisible = isVisible
        )
        Spacer(modifier = Modifier.height(25.dp))
        ItemDetailWithCheckbox(
            title = stringResource(R.string.original_pattern),
            description = stringResource(R.string.purchase_original_pattern),
            delay = 1700,
            isVisible = isVisible
        ) { isChecked ->
            onCheck(isChecked)
            if (isChecked) totalPrice += design.patternPrice else totalPrice -= design.patternPrice
        }
        Spacer(modifier = Modifier.height(35.dp))
        if (isLoading) {
            LoadingAnimation(
                circleColor = Color(Red),
                circleSize = 10.dp
            )
        } else {
            CustomButton(
                modifier = Modifier.fillMaxWidth(0.9f),
                label = "$sendOrderText - $totalPrice DA"
            ) {
                onClick(totalPrice)
            }
        }
        Spacer(modifier = Modifier.height(25.dp))
    }
}

@Composable
fun SizeChip(modifier: Modifier = Modifier, size: String, isSelected: Boolean, onClick: () -> Unit) {

    val borderColor = animateColorAsState(
        if (isSelected) Color(Red) else Color.Transparent
    )
    val textColor = animateColorAsState(
        if (isSelected) Color(Red) else Color(Black)
    )

    Box(
        modifier = modifier
            .size(50.dp)
            .border(width = 1.dp, color = borderColor.value, shape = RoundedCornerShape(12))
            .clip(RoundedCornerShape(12))
            .background(Color(WhiteSoft))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = size,
            style = MaterialTheme.typography.titleMedium,
            color = textColor.value,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun ImageChip(modifier: Modifier = Modifier, imageUrl: String, onClick: (String) -> Unit) {
    Box(
        modifier = modifier
            .size(50.dp)
            .clip(RoundedCornerShape(12))
            .clickable { onClick(imageUrl) }
    ) {
        AsyncImage(model = imageUrl, contentDescription = null, contentScale = ContentScale.Crop)
    }
}

@Composable
fun ImageChipsList(list: List<String>, onClick: (String) -> Unit) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        items(list) { item ->  
            ImageChip(imageUrl = item, onClick = { onClick(it) })
        }
    }
}

@Composable
fun SizeChipsList(list: List<String>, onClick: (String) -> Unit) {
    var selectedIndex by remember {
        mutableStateOf(0)
    }
    Column {
        Text(
            text = stringResource(R.string.size),
            style = MaterialTheme.typography.titleMedium,
            color = Color(Black),
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(5.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(list.size) { index ->
                SizeChip(size = list[index], isSelected = selectedIndex == index) {
                    onClick(list[index])
                    selectedIndex = index
                }
            }
        }
    }
}
