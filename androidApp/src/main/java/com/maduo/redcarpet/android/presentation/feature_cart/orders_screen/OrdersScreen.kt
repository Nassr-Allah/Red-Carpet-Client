package com.maduo.redcarpet.android.presentation.feature_cart.orders_screen

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.maduo.redcarpet.android.R
import com.maduo.redcarpet.android.presentation.components.BoxShimmerLoading
import com.maduo.redcarpet.android.presentation.components.Chips
import com.maduo.redcarpet.android.presentation.components.ChipsAsPairs
import com.maduo.redcarpet.android.presentation.destinations.CustomOrderScreenDestination
import com.maduo.redcarpet.android.presentation.destinations.NormalOrderScreenDestination
import com.maduo.redcarpet.android.presentation.navigation.MainAppNavGraph
import com.maduo.redcarpet.domain.model.Order
import com.maduo.redcarpet.presentation.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.valentinilk.shimmer.shimmer

@MainAppNavGraph
@Destination
@Composable
fun OrdersScreen(
    viewModel: OrdersScreenViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current.applicationContext
    val activity = LocalContext.current as Activity

    var orders by remember {
        mutableStateOf(listOf<Order>())
    }

    val statusList = listOf(
        Pair("all", stringResource(R.string.all)),
        Pair("pending", stringResource(R.string.pending)),
        Pair("accepted", stringResource(R.string.accepted)),
        Pair("rejected", stringResource(R.string.rejected)),
        Pair("on progress", stringResource(R.string.on_progress)),
        Pair("canceled", stringResource(R.string.canceled)),
        Pair("complete", stringResource(R.string.complete)),
    )

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

    LaunchedEffect(key1 = viewModel.filteredList) {
        orders = viewModel.filteredList.toList()
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
            Text(
                text = stringResource(R.string.my_orders),
                style = MaterialTheme.typography.headlineMedium,
                color = Color(Black),
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(25.dp))
            ChipsAsPairs(
                items = statusList,
                label = stringResource(R.string.order_status),
                onSelected = {
                    viewModel.filterOrders(it)
                    Log.d("OrdersScreen", "filter to: $it")
                }
            )
            Spacer(modifier = Modifier.height(35.dp))
            if (state.isLoading) {
                OrdersShimmerList()
            } else {
                OrdersList(
                    list = state.orders,
                    onClick = {
                        if (it.type.equals("custom", true)) {
                            navigator.navigate(CustomOrderScreenDestination(it.id))
                        } else {
                            navigator.navigate(NormalOrderScreenDestination(it.id))
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun OrdersList(list: List<Order>, onClick: (Order) -> Unit) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        items(list) { item ->
            OrderItem(order = item) {
                onClick(item)
            }
        }
    }
}

@Composable
fun OrderItem(order: Order, onClick: () -> Unit) {
    val statusColor = when(order.status.lowercase()) {
        "accepted" -> Color(Green)
        "rejected" -> Color(Red)
        "complete" -> Color(Green)
        "canceled" -> Color(Red)
        else -> Color(Orange)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10))
            .clickable { onClick() }
            .background(Color(WhiteSoft))
            .padding(15.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Order: ${order.id}",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(Black)
                )
            }
            Spacer(modifier = Modifier.height(7.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = order.status,
                    style = MaterialTheme.typography.titleMedium,
                    color = statusColor
                )
            }
            Spacer(modifier = Modifier.height(7.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = order.date,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(Black)
                )
                Text(
                    text = "${order.price} DA",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(Black),
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.height(25.dp))
        }
    }
}

@Composable
fun OrdersShimmerList() {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        items(6) {
            BoxShimmerLoading(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
        }
    }
}