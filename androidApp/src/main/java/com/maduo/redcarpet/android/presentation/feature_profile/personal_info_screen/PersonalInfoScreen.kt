package com.maduo.redcarpet.android.presentation.feature_profile.personal_info_screen

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.maduo.redcarpet.android.MyApplicationTheme
import com.maduo.redcarpet.android.R
import com.maduo.redcarpet.android.presentation.components.CustomButton
import com.maduo.redcarpet.android.presentation.components.ScreenHeader
import com.maduo.redcarpet.android.presentation.navigation.MainAppNavGraph
import com.maduo.redcarpet.presentation.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.delay
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@MainAppNavGraph
@Destination
@Composable
fun PersonalInfoScreen(
    viewModel: PersonalInfoViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current.applicationContext

    var isVisible by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = state) {
        if (!state.isLoading) {
            if (state.error.isNotEmpty()) {
                Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
            }
            if (state.isClientUpdated && state.response.isNotEmpty()) {
                Toast.makeText(context, state.response, Toast.LENGTH_SHORT).show()
            }
            if (state.clientDto != null) {
                isVisible = true
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
             ScreenHeader(label = stringResource(R.string.personal_information)) {
                 navigator.navigateUp()
             }
            Spacer(modifier = Modifier.height(35.dp))
            PersonalInfoSection(isVisible = isVisible, viewModel = viewModel) {
                viewModel.updateClient()
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PersonalInfoSection(
    isVisible: Boolean,
    viewModel: PersonalInfoViewModel,
    onClick: () -> Unit
) {

    val dateDialogState = rememberMaterialDialogState()
    val scrollState = rememberScrollState()

    var date by remember {
        mutableStateOf(viewModel.dateOfBirth.value)
    }

    val genders = listOf(
        Pair(stringResource(R.string.male), "Male"),
        Pair(stringResource(R.string.female), "Female"),
    )

    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton("OK", textStyle = TextStyle(color = Color(Red)))
            negativeButton("CANCEL", textStyle = TextStyle(color = Color(Red)))
        },
    ) {
        datepicker(
            colors = DatePickerDefaults.colors(
                dateActiveBackgroundColor = Color(Red),
                headerBackgroundColor = Color(Red)
            )
        ) {
            date = DateTimeFormatter.ofPattern("dd MMMM yyyy").format(it)
            viewModel.dateOfBirth.value = date
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CustomProfileTextField(
            value = viewModel.firstName.value,
            label = stringResource(R.string.first_name),
            onValueChange = {
                viewModel.firstName.value = it
            },
            isVisible = isVisible,
            delay = 200
        )
        CustomProfileTextField(
            value = viewModel.lastName.value,
            label = stringResource(R.string.last_name),
            onValueChange = {
                viewModel.lastName.value = it
            },
            isVisible = isVisible,
            delay = 500
        )
        CustomProfileTextField(
            value = viewModel.email.value,
            label = stringResource(R.string.email),
            onValueChange = {
                viewModel.email.value = it
            },
            isVisible = isVisible,
            delay = 800
        )
        CustomProfileTextField(
            value = viewModel.address.value,
            label = stringResource(R.string.address),
            onValueChange = {
                viewModel.address.value = it
            },
            isVisible = isVisible,
            delay = 1100
        )
        CustomProfileDropDownMenu(
            list = listOf(genders[0].first, genders[1].first),
            onItemSelected = {
                viewModel.gender.value = if (it == genders[0].first) genders[0].second else genders[1].second
            },
            isVisible = isVisible,
            delay = 1400,
            value = viewModel.gender.value
        )
        CustomProfileTextField(
            value = viewModel.dateOfBirth.value,
            label = stringResource(R.string.date_of_birth),
            onValueChange = { },
            trailingIcon = {
                Text(
                    text = stringResource(R.string.pick),
                    color = Color(Black),
                    modifier = Modifier.clickable { dateDialogState.show() }
                )
            },
            enabled = false,
            isVisible = isVisible,
            delay = 1700
        )
        Spacer(modifier = Modifier.height(15.dp))
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(
                tween(durationMillis = 500, delayMillis = 1700)
            ) + slideInHorizontally(
                animationSpec = tween(durationMillis = 500, delayMillis = 1700)
            )
        ) {
            CustomButton(modifier = Modifier.fillMaxWidth(0.9f), label = stringResource(R.string.save)) {
                onClick()
            }
        }
    }
}

@Composable
fun CustomProfileTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    maxLines: Int = 1,
    enabled: Boolean = true,
    delay: Int = 0,
    isVisible: Boolean
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(
            tween(durationMillis = 500, delayMillis = delay)
        ) + slideInHorizontally(
            animationSpec = tween(durationMillis = 500, delayMillis = delay)
        )
    ) {
        TextField(
            modifier = modifier,
            value = value,
            onValueChange = { onValueChange(it) },
            label = { Text(text = label) },
            trailingIcon = trailingIcon,
            isError = isError,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            maxLines = maxLines,
            colors = TextFieldDefaults.colors(
                selectionColors = TextSelectionColors(
                    handleColor = Color.Transparent,
                    backgroundColor = Color.Transparent
                ),
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                focusedLabelColor = Color(Gray),
                unfocusedLabelColor = Color(Gray),
                cursorColor = Color(Black),
                focusedTextColor = Color(Black),
                errorIndicatorColor = Color(Red),
                errorLabelColor = Color(Red),
                focusedIndicatorColor = Color(Black),
                unfocusedTextColor = Color(Black),
                unfocusedIndicatorColor = Color(GraySolid),
                disabledContainerColor = Color.Transparent,
                disabledLabelColor = Color(Gray),
                disabledTextColor = Color(Black),
                disabledIndicatorColor = Color(GraySolid)
            ),
            enabled = enabled
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomProfileDropDownMenu(
    list: List<String>,
    delay: Int = 0,
    isVisible: Boolean,
    value: String,
    onItemSelected: (String) -> Unit
) {
    var selectedIndex by remember {
        mutableStateOf(0)
    }

    var selectedText by remember {
        mutableStateOf(value)
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(
            tween(durationMillis = 500, delayMillis = delay)
        ) + slideInHorizontally(
            animationSpec = tween(durationMillis = 500, delayMillis = delay)
        )
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = value,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor(),
                colors = TextFieldDefaults.colors(
                    selectionColors = TextSelectionColors(
                        handleColor = Color.Transparent,
                        backgroundColor = Color.Transparent
                    ),
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedLabelColor = Color(Gray),
                    unfocusedLabelColor = Color(Gray),
                    cursorColor = Color(Black),
                    focusedTextColor = Color(Black),
                    errorIndicatorColor = Color(Red),
                    errorLabelColor = Color(Red),
                    focusedIndicatorColor = Color(Black),
                    unfocusedTextColor = Color(Black),
                    unfocusedIndicatorColor = Color(Gray),
                    disabledContainerColor = Color.Transparent,
                    disabledLabelColor = Color(Gray),
                    disabledTextColor = Color(Black),
                    disabledIndicatorColor = Color(Gray)
                ),
            )
            ExposedDropdownMenu(
                modifier = Modifier.background(Color(WhiteSoft)),
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                list.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedText = item
                            onItemSelected(item)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}