package com.maduo.redcarpet.android.presentation.feature_cart.normal_order_details

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.maduo.redcarpet.android.presentation.components.IconRoundBackground
import com.maduo.redcarpet.domain.model.Design
import com.maduo.redcarpet.domain.model.RegularOrder
import com.maduo.redcarpet.android.R
import com.maduo.redcarpet.android.presentation.components.BoxShimmerLoading
import com.maduo.redcarpet.android.presentation.components.CustomButton
import com.maduo.redcarpet.android.presentation.components.OrderDetail
import com.maduo.redcarpet.android.presentation.destinations.CcpScreenDestination
import com.maduo.redcarpet.android.presentation.navigation.MainAppNavGraph
import com.maduo.redcarpet.presentation.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@MainAppNavGraph
@Destination
@Composable
fun NormalOrderScreen(
    viewModel: NormalOrderViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    orderId: String
) {

    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current.applicationContext
    val activity = LocalContext.current as Activity
    val scrollState = rememberScrollState()

    val errorText = stringResource(R.string.error)
    val payText = stringResource(R.string.pay)
    val dzdText = stringResource(R.string.DZD)

    LaunchedEffect(key1 = true) {
        viewModel.getOrderDetails(orderId)
    }

    LaunchedEffect(key1 = state) {
        if (!state.isLoading) {
            if (state.error.isNotEmpty()) {
                Toast.makeText(context, errorText, Toast.LENGTH_SHORT).show()
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
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    text = stringResource(R.string.order_details),
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(Black),
                    fontWeight = FontWeight.SemiBold
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconRoundBackground(icon = R.drawable.arrow_left) {
                        navigator.navigateUp()
                    }
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            OrderDetails(
                order = state.order ?: RegularOrder(design = Design(category = "")),
                design = state.order?.design ?: Design(category = ""),
                state = state
            )
            Spacer(modifier = Modifier.height(30.dp))
            CustomButton(
                modifier = Modifier.fillMaxWidth(0.9f),
                label = "$payText ${state.order?.totalPrice} $dzdText",
                color = Green
            ) {
                navigator.navigate(CcpScreenDestination(orderId))
            }
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}

@Composable
fun OrderDetails(order: RegularOrder, design: Design, state: NormalOrderScreenState) {

    val dzdText = stringResource(R.string.DZD)

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.item_ordered),
            style = MaterialTheme.typography.titleMedium,
            color = Color(Black),
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(10.dp))
        DesignDetails(design = design, order = order, isLoading = state.isLoading)
        Spacer(modifier = Modifier.height(15.dp))
        OrderDetail(
            label = stringResource(R.string.delivery),
            description = order.deliveryPlaces,
            price = "${order.deliveryPrice} $dzdText",
            isLoading = state.isLoading
        )
        Spacer(modifier = Modifier.height(15.dp))
        OrderDetail(
            label = stringResource(R.string.total_price),
            description = stringResource(R.string.total_price),
            price = "${order.totalPrice} $dzdText",
            isLoading = state.isLoading
        )
        Spacer(modifier = Modifier.height(15.dp))
        OrderDetail(
            label = stringResource(R.string.order_status),
            description = order.status,
            isLoading = state.isLoading
        )
        Spacer(modifier = Modifier.height(15.dp))
        OrderDetail(
            label = stringResource(R.string.original_pattern),
            description = if (order.isPatternIncluded) stringResource(R.string.yes) else stringResource(R.string.no),
            isLoading = state.isLoading
        )
    }
}

@Composable
fun DesignDetails(design: Design, order: RegularOrder, isLoading: Boolean) {

    val sizeText = stringResource(R.string.size)
    val dzdText = stringResource(R.string.DZD)
    val originalPatternText = stringResource(R.string.original_pattern)

    if (isLoading) {
        BoxShimmerLoading(modifier = Modifier
            .fillMaxWidth()
            .height(120.dp))
    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10))
                .background(Color(WhiteSoft))
                .padding(15.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .height(120.dp)
                        .clip(RoundedCornerShape(10)),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = design.images[0],
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                Column {
                    Text(
                        text = design.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(Black),
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "$sizeText: ${order.size}",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color(Gray)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "$originalPatternText: ${order.isPatternIncluded}",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color(Gray)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${order.totalPrice} $dzdText",
                            style = MaterialTheme.typography.titleSmall,
                            color = Color(Black),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}
