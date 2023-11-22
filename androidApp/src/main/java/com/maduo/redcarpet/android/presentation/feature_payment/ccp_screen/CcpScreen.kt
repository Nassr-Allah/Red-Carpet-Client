package com.maduo.redcarpet.android.presentation.feature_payment.ccp_screen

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.maduo.redcarpet.android.MyApplicationTheme
import com.maduo.redcarpet.presentation.Black
import com.maduo.redcarpet.android.R
import com.maduo.redcarpet.android.presentation.components.*
import com.maduo.redcarpet.android.presentation.destinations.PaymentConfirmationScreenDestination
import com.maduo.redcarpet.android.presentation.navigation.MainAppNavGraph
import com.maduo.redcarpet.presentation.Green
import com.maduo.redcarpet.presentation.Red
import com.maduo.redcarpet.presentation.WhiteSoft
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@MainAppNavGraph
@Destination
@Composable
fun CcpScreen(
    viewModel: CcpScreenViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    orderId: String
) {

    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current.applicationContext
    val contentResolver = context.contentResolver

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        Log.d("CcpScreen", "Uri: $it")
        viewModel.uri.value = it
    }

    LaunchedEffect(key1 = state) {
        if (!state.isLoading) {
            if (state.error.isNotEmpty()) {
                Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
            }
            if (state.isPaymentCreated && state.paymentResponse.isNotEmpty()) {
                navigator.navigate(PaymentConfirmationScreenDestination)
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
            ScreenHeader(label = stringResource(R.string.payment)) {
                navigator.navigateUp()
            }
            Spacer(modifier = Modifier.height(40.dp))
            PagePicker(
                labelRight = "PayPal",
                labelLeft = "CCP/BaridiMob",
                onRightSelected = {},
                onLeftSelected = {}
            )
            Spacer(modifier = Modifier.height(50.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.receipt),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(Black)
                )
                Spacer(modifier = Modifier.height(10.dp))
                CustomOutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    label = stringResource(R.string.add_receipt),
                    icon = R.drawable.ic_plus
                ) {
                    imagePicker.launch("image/*")
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.45f)
                    .clip(RoundedCornerShape(10))
                    .background(Color(WhiteSoft))
            ) {
                AsyncImage(
                    model = viewModel.uri.value,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.height(50.dp))
            if (state.isLoading) {
                LoadingAnimation(
                    circleColor = Color(Red),
                    circleSize = 10.dp
                )
            } else {
                CustomButton(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    label = stringResource(R.string.confirm),
                    color = Green
                ) {
                    viewModel.uploadReceipt(contentResolver, orderId)
                }
            }
        }
    }
}
