package com.maduo.redcarpet.android.presentation.feature_cart.cutom_order_details

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.maduo.redcarpet.android.MyApplicationTheme
import com.maduo.redcarpet.android.presentation.components.BoxShimmerLoading
import com.maduo.redcarpet.android.presentation.components.CustomButton
import com.maduo.redcarpet.android.presentation.components.OrderDetail
import com.maduo.redcarpet.android.presentation.components.ScreenHeader
import com.maduo.redcarpet.android.presentation.destinations.CcpScreenDestination
import com.maduo.redcarpet.android.presentation.navigation.MainAppNavGraph
import com.maduo.redcarpet.domain.model.CustomOrder
import com.maduo.redcarpet.presentation.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.maduo.redcarpet.android.R

@MainAppNavGraph
@Destination
@Composable
fun CustomOrderScreen(
    viewModel: CustomOrderViewModel = hiltViewModel(),
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
            ScreenHeader(label = stringResource(R.string.order_details)) {
                navigator.navigateUp()
            }
            Spacer(modifier = Modifier.height(35.dp))
            DesignDetails(order = state.order ?: CustomOrder(), isLoading = state.isLoading)
            Spacer(modifier = Modifier.height(15.dp))
            OrderDetails(order = state.order ?: CustomOrder(), state = state)
            Spacer(modifier = Modifier.height(30.dp))
            CustomButton(
                modifier = Modifier.fillMaxWidth(0.9f),
                label = "$payText ${state.order?.budget} $dzdText",
                color = Green
            ) {
                navigator.navigate(CcpScreenDestination(orderId))
            }
            Spacer(modifier = Modifier.height(25.dp))
        }
    }
}

@Composable
fun OrderDetails(order: CustomOrder, state: CustomOrderScreenState) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OrderDetail(label = stringResource(R.string.category), description = order.category, isLoading = state.isLoading)
        Spacer(modifier = Modifier.height(15.dp))
        OrderDetail(label = stringResource(R.string.your_budget), description = "${order.budget} DA", isLoading = state.isLoading)
        Spacer(modifier = Modifier.height(15.dp))
        OrderDetail(label = stringResource(R.string.order_status), description = order.status, isLoading = state.isLoading)
        Spacer(modifier = Modifier.height(15.dp))
        OrderDetail(label = stringResource(R.string.delivery_time), description = order.period, isLoading = state.isLoading)
    }
}

@Composable
fun DesignDetails(order: CustomOrder, isLoading: Boolean) {

    val sizeText = stringResource(R.string.size)
    val colorText = stringResource(R.string.color)
    val clothText = stringResource(R.string.cloth)

    Column {
        Text(
            text = stringResource(R.string.item_ordered),
            style = MaterialTheme.typography.titleMedium,
            color = Color(Black),
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(10.dp))
        if (isLoading) {
            BoxShimmerLoading(modifier = Modifier
                .fillMaxWidth()
                .height(120.dp))
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12))
                    .background(Color(WhiteSoft))
                    .padding(horizontal = 25.dp, vertical = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(R.string.custom_design),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Medium,
                        color = Color(Black)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "$sizeText: ${order.size}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(Gray)
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = "$colorText: ${order.colors}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(Gray)
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = "$clothText: ${order.cloth}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(Gray)
                    )
                }
            }
        }
    }
}
