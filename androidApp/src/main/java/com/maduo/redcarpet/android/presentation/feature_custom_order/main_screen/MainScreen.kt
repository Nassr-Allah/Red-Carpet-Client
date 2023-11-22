package com.maduo.redcarpet.android.presentation.feature_custom_order.main_screen

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.maduo.redcarpet.android.MyApplicationTheme
import com.maduo.redcarpet.android.R
import com.maduo.redcarpet.android.presentation.components.*
import com.maduo.redcarpet.android.presentation.destinations.CollectionScreenDestination
import com.maduo.redcarpet.android.presentation.destinations.ConfirmationScreenDestination
import com.maduo.redcarpet.android.presentation.destinations.OrdersScreenDestination
import com.maduo.redcarpet.android.presentation.navigation.MainAppNavGraph
import com.maduo.redcarpet.presentation.Black
import com.maduo.redcarpet.presentation.Red
import com.maduo.redcarpet.presentation.WhiteSoft
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@MainAppNavGraph(start = true)
@Destination
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    val state = viewModel.state.collectAsState()
    val context = LocalContext.current.applicationContext

    LaunchedEffect(key1 = state.value) {
        if (!state.value.isLoading) {
            if (state.value.isOrderSent && state.value.message.isNotEmpty()) {
                navigator.navigate(ConfirmationScreenDestination)
            }
            if (state.value.error.isNotEmpty()) {
                Toast.makeText(context, state.value.error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp), contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            MainScreenHeader(
                onCollectionClick = {
                    navigator.navigate(CollectionScreenDestination)
                },
                onCartClick = {
                    navigator.navigate(OrdersScreenDestination)
                }
            )
            Spacer(modifier = Modifier.height(25.dp))
            InputFields(viewModel = viewModel, isLoading = state.value.isLoading)
        }
    }
}

@Composable
fun MainScreenHeader(onCollectionClick: () -> Unit, onCartClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconRoundBackground(icon = R.drawable.folder) {
                onCollectionClick()
            }
            Spacer(modifier = Modifier.width(10.dp))
            IconRoundBackground(icon = R.drawable.bag) {
                onCartClick()
            }
        }
        Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.2f)) {
            Image(
                painter = painterResource(R.drawable.redcarpet_wordmark),
                contentDescription = null,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}

@Composable
fun InputFields(viewModel: MainScreenViewModel, isLoading: Boolean) {

    val context = LocalContext.current.applicationContext
    val activity = LocalContext.current as Activity
    val contentResolver = activity.contentResolver

    val scrollState = rememberScrollState()

    val wrongInputText = stringResource(R.string.wrong_input)

    val categories = listOf(
        Pair("VIP", stringResource(R.string.vip)),
        Pair("Regular", stringResource(R.string.regular)),
        Pair("Tall", stringResource(R.string.tall)),
        Pair("Short", stringResource(R.string.short_)),
        Pair("Oversize", stringResource(R.string.oversize)),
        Pair("Skinny", stringResource(R.string.skinny)),
        Pair("Special Needs", stringResource(R.string.special_needs)),
        Pair("Other", stringResource(R.string.other)),
    )

    val genders = listOf(
        Pair("Male", stringResource(R.string.male)),
        Pair("Female", stringResource(R.string.female)),
        Pair("Children", stringResource(R.string.children)),
    )

    val designType = listOf(
        Pair("T-Shirt", stringResource(R.string.tshirt)),
        Pair("Pants", stringResource(R.string.pants)),
        Pair("Dress", stringResource(R.string.dress)),
        Pair("Other", stringResource(R.string.other)),
    )

    val materials = listOf(
        Pair("Summer", stringResource(R.string.summer)),
        Pair("Winter", stringResource(R.string.winter)),
        Pair("Autumn", stringResource(R.string.autumn)),
        Pair("Spring", stringResource(R.string.spring)),
        Pair("Other", stringResource(R.string.other)),
    )

    val size = listOf(
        "S", "M", "L", "XL", "XXL", "XXXL", "4XL", "5XL", "Other"
    )

    val yesNo = listOf(
        Pair("Yes", stringResource(R.string.yes)),
        Pair("No", stringResource(R.string.no)),
    )

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        viewModel.uri.value = it
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ChipsAsPairs(items = categories, label = stringResource(R.string.choose_category), onSelected = {
            viewModel.category.value = it
        })
        Spacer(modifier = Modifier.height(20.dp))

        ChipsAsPairs(items = genders, label = stringResource(R.string.choose_gender), onSelected = {
            viewModel.gender.value = it
        })
        Spacer(modifier = Modifier.height(20.dp))

        CustomOutlinedTextField(
            value = viewModel.age.value,
            label = stringResource(R.string.select_age),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            onValueChange = {
                viewModel.age.value = it
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))

        ChipsAsPairs(items = designType, label = stringResource(R.string.choose_design_type), onSelected = {
            viewModel.designType.value = it
        })
        Spacer(modifier = Modifier.height(20.dp))

        ChipsAsPairs(items = materials, label = stringResource(R.string.choose_cloth_material), onSelected = {
            viewModel.clothMaterial.value = it
        })
        Spacer(modifier = Modifier.height(20.dp))

        Chips(items = size, label = stringResource(R.string.choose_size), onSelected = {
            viewModel.size.value = it
        })
        Spacer(modifier = Modifier.height(20.dp))

        CustomOutlinedTextField(
            value = viewModel.colors.value,
            label = stringResource(R.string.suggested_colors),
            onValueChange = {
                viewModel.colors.value = it
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))

        CustomOutlinedTextField(
            value = viewModel.budget.value,
            label = stringResource(R.string.your_budget),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            onValueChange = {
                viewModel.budget.value = it
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))

        CustomOutlinedTextField(
            value = viewModel.deliveryTime.value,
            label = stringResource(R.string.delivery_time),
            onValueChange = {
                viewModel.deliveryTime.value = it
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))

        ChipsAsPairs(
            items = yesNo,
            label = stringResource(R.string.do_you_want_the_design_to_be_sewn),
            onSelected = {
                viewModel.isSewn.value = it == "Yes"
            }
        )
        Spacer(modifier = Modifier.height(20.dp))

        ChipsAsPairs(items = yesNo, label = stringResource(R.string.purchase_original_pattern), onSelected = {
            viewModel.isPatternIncluded.value = it == "Yes"
        })
        Spacer(modifier = Modifier.height(20.dp))

        CustomOutlinedTextField(
            value = viewModel.addition.value,
            label = stringResource(R.string.anything_to_add),
            onValueChange = {
                viewModel.addition.value = it
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))

        CustomOutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.add_attachment),
            icon = R.drawable.ic_plus
        ) {
            imagePicker.launch("image/*")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
                .clip(RoundedCornerShape(12))
                .background(Color(WhiteSoft)),

        ) {
            AsyncImage(
                model = viewModel.uri.value,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(25.dp))

        if (isLoading) {
            LoadingAnimation(
                circleSize = 10.dp,
                circleColor = Color(Red)
            )
        } else {
            CustomButton(modifier = Modifier.fillMaxWidth(), label = stringResource(R.string.send_order)) {
                if (viewModel.validateInput()) {
                    viewModel.uploadAttachment(contentResolver)
                } else {
                    Toast.makeText(context, "Wrong Input!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
