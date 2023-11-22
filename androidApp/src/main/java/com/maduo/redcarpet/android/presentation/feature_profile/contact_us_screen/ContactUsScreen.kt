package com.maduo.redcarpet.android.presentation.feature_profile.contact_us_screen

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.maduo.redcarpet.android.presentation.feature_profile.profile_screen.ProfileItem
import com.maduo.redcarpet.android.R
import com.maduo.redcarpet.android.presentation.components.ItemDetail
import com.maduo.redcarpet.android.presentation.components.ScreenHeader
import com.maduo.redcarpet.android.presentation.navigation.MainAppNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@MainAppNavGraph
@Destination
@Composable
fun ContactUsScreen(
    navigator: DestinationsNavigator
) {

    val context = LocalContext.current as Activity

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
            ScreenHeader(label = stringResource(R.string.contact_us)) {
                navigator.popBackStack()
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            ProfileItem(label = "+213 6 55 88 50 21", isVisible = true, delay = 300) {
                val openDialerIntent = Intent(Intent.ACTION_DIAL)
                openDialerIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                openDialerIntent.data = Uri.parse("tel:+213655885021")
                context.startActivity(openDialerIntent)
            }
            ProfileItem(label = "bebba.redcarpet@gmail.com", isVisible = true, delay = 600) {
                val openEmailIntent = Intent(Intent.ACTION_SEND)
                openEmailIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                openEmailIntent.type = "vnd.android.cursor.item/email"
                openEmailIntent.putExtra(Intent.EXTRA_EMAIL, "bebba.redcarpet@gmail.com")
                context.startActivity(openEmailIntent)
            }
        }
    }
}