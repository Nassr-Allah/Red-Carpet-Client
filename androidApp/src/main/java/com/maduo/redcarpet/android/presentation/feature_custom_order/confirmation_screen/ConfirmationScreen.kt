package com.maduo.redcarpet.android.presentation.feature_custom_order.confirmation_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maduo.redcarpet.android.MyApplicationTheme
import com.maduo.redcarpet.android.presentation.components.IconRoundBackground
import com.maduo.redcarpet.android.R
import com.maduo.redcarpet.android.presentation.components.CustomButton
import com.maduo.redcarpet.android.presentation.destinations.OrdersScreenDestination
import com.maduo.redcarpet.android.presentation.navigation.MainAppNavGraph
import com.maduo.redcarpet.presentation.Black
import com.maduo.redcarpet.presentation.Gray
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay

@MainAppNavGraph
@Destination
@Composable
fun ConfirmationScreen(
    navigator: DestinationsNavigator
) {

    var isVisible by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        delay(200)
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = 1000,
                delayMillis = 500
            )
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.4f),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.order_confirmed),
                            contentDescription = null,
                            contentScale = ContentScale.FillHeight
                        )
                    }
                    Spacer(modifier = Modifier.height(60.dp))
                    Text(
                        text = stringResource(R.string.order_sent),
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color(Black),
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = stringResource(R.string.order_has_been_confirmed),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(Gray),
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center
                    )
                }
                CustomButton(modifier = Modifier.fillMaxWidth(0.9f), label = stringResource(R.string.go_to_orders)) {
                    navigator.navigate(OrdersScreenDestination)
                }
                Spacer(modifier = Modifier.height(20.dp))

            }
        }
    }
}