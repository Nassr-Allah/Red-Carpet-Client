package com.maduo.redcarpet.android.presentation.feature_auth.login_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maduo.redcarpet.android.MyApplicationTheme
import com.maduo.redcarpet.android.R
import com.maduo.redcarpet.android.presentation.components.CustomDropDownMenu
import com.maduo.redcarpet.android.presentation.components.CustomTextField
import com.maduo.redcarpet.android.presentation.navigation.SignupNavGraph
import com.maduo.redcarpet.presentation.Black
import com.maduo.redcarpet.presentation.Red
import com.maduo.redcarpet.presentation.White
import com.ramcosta.composedestinations.annotation.Destination
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@SignupNavGraph
@Destination
@Composable
fun DetailsSection(
    onBirthDateChange: (String) -> Unit,
    onAddressChange: (String) -> Unit,
    onGenderChange: (String) -> Unit
) {

    val dateDialogState = rememberMaterialDialogState()
    val genders = listOf(stringResource(R.string.male), stringResource(R.string.female))

    var selectedGender by remember {
        mutableStateOf(genders[0])
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    var date by remember {
        mutableStateOf("")
    }

    var address by remember {
        mutableStateOf("")
    }

    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton("OK")
            negativeButton("ANNULER")
        },
    ) {
        datepicker(
            colors = DatePickerDefaults.colors(
                dateActiveBackgroundColor = Color(Red),
                headerBackgroundColor = Color(Red)
            )
        ) {
            date = DateTimeFormatter.ofPattern("dd MMMM yyyy").format(it)
            onBirthDateChange(date)
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTextField(
            value = date,
            label = stringResource(R.string.date_of_birth),
            onValueChange = { },
            trailingIcon = {
                Text(
                    text = "Pick",
                    color = Color(White),
                    modifier = Modifier.clickable { dateDialogState.show() }
                )
            },
            enabled = false
        )
        Spacer(modifier = Modifier.height(15.dp))
        CustomTextField(
            value = address,
            label = stringResource(R.string.address),
            onValueChange = {
                address = it
                onAddressChange(it)
            }
        )
        Spacer(modifier = Modifier.height(15.dp))
        CustomDropDownMenu(
            list = genders,
            onItemSelected = {
                selectedGender = it
                onGenderChange(it)
            }
        )
    }
}
