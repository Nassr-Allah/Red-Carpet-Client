package com.maduo.redcarpet.android.presentation.feature_profile.language_screen

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.maduo.redcarpet.android.R
import com.maduo.redcarpet.android.presentation.components.CustomButton
import com.maduo.redcarpet.android.presentation.components.PagePicker
import com.maduo.redcarpet.android.presentation.navigation.MainAppNavGraph
import com.maduo.redcarpet.android.util.LanguageHelper
import com.ramcosta.composedestinations.annotation.Destination

@MainAppNavGraph
@Destination
@Composable
fun LanguageScreen() {

    val context = LocalContext.current
    val activity = LocalContext.current as Activity

    val storedLanguage = LanguageHelper.getUserLanguage(context)

    var selectedLanguage by remember {
        mutableStateOf(storedLanguage)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.SpaceAround) {
            PagePicker(
                labelRight = "العربية",
                labelLeft = "English",
                onRightSelected = {
                    selectedLanguage = "ar"
                    Log.d("LanguageScreen", "right selected: $selectedLanguage")
                },
                onLeftSelected = {
                    selectedLanguage = "en"
                    Log.d("LanguageScreen", "left selected: $selectedLanguage")
                }
            )
            Spacer(modifier = Modifier.height(30.dp))
            CustomButton(modifier = Modifier.fillMaxWidth(), label = stringResource(R.string.save)) {
                LanguageHelper.storeLanguage(context, selectedLanguage)
                LanguageHelper.updateLanguage(context, selectedLanguage)
                LanguageHelper.recreateActivity(activity)
            }
        }
    }
}