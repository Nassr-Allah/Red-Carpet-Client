package com.maduo.redcarpet.android.presentation.feature_profile.profile_screen

import android.app.Activity
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.maduo.redcarpet.android.MyApplicationTheme
import com.maduo.redcarpet.presentation.Black
import com.maduo.redcarpet.presentation.WhiteSoft
import com.ramcosta.composedestinations.annotation.Destination
import com.maduo.redcarpet.android.R
import com.maduo.redcarpet.android.presentation.components.CustomOutlinedButton
import com.maduo.redcarpet.android.presentation.components.ScreenTitleSubtitle
import com.maduo.redcarpet.android.presentation.destinations.*
import com.maduo.redcarpet.android.presentation.navigation.MainAppNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay

@MainAppNavGraph
@Destination
@Composable
fun ProfileScreen(
    viewModel: ProfileScreenViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    val state = viewModel.state.collectAsState().value
    val activity = LocalContext.current as Activity
    val context = LocalContext.current.applicationContext

    val errorText = stringResource(R.string.error)

    var isVisible by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        delay(500)
        isVisible = true
    }

    LaunchedEffect(key1 = state) {
        if (!state.isLoading) {
            if (state.isLoggedOut) {
                activity.finish()
            }
            if (state.error.isNotEmpty()) {
                Toast.makeText(context, errorText, Toast.LENGTH_SHORT).show()
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
            Row(modifier = Modifier.fillMaxWidth()) {
                ScreenTitleSubtitle(title = stringResource(R.string.my_profile), subtitle = stringResource(R.string.manage_your_profile))
            }
            Spacer(modifier = Modifier.height(36.dp))
            ProfileItemsList(isVisible = isVisible, navigator = navigator) {
                viewModel.logOut()
            }
        }
    }
}

@Composable
fun ProfileItemsList(isVisible: Boolean, navigator: DestinationsNavigator, onClick: () -> Unit) {

    val logoutText = stringResource(R.string.logout)
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ProfileItem(label = stringResource(R.string.personal_information), isVisible = isVisible, delay = 200) {
            navigator.navigate(PersonalInfoScreenDestination)
        }
        ProfileItem(label = stringResource(R.string.my_orders), isVisible = isVisible, delay = 500) {
            navigator.navigate(OrdersScreenDestination)
        }
        ProfileItem(label = stringResource(R.string.my_collection), isVisible = isVisible, delay = 800) {
            navigator.navigate(CollectionScreenDestination)
        }
        ProfileItem(label = stringResource(R.string.language), isVisible = isVisible, delay = 1100) {
            navigator.navigate(LanguageScreenDestination)
        }
        ProfileItem(label = stringResource(R.string.privacy_policy), isVisible = isVisible, delay = 1400) {
            uriHandler.openUri("https://s3.privyr.com/privacy/privacy-policy.html?d=eyJlbWFpbCI6ImJlYmJhLnJlZGNhcnBldEBnbWFpbC5jb20iLCJjb21wYW55IjoiUmVkIENhcnBldCIsImdlbl9hdCI6IjIwMjMtMTEtMTlUMjM6MTg6NDcuOTgxWiJ9")
        }
        ProfileItem(label = stringResource(R.string.terms_of_use), isVisible = isVisible, delay = 1700) {
            uriHandler.openUri("https://www.privacypolicyonline.com/live.php?token=dfxjA7FYWw70CVn3w1oXSytsuEuauyxw")
        }
        ProfileItem(label = stringResource(R.string.contact_us), isVisible = isVisible, delay = 2000) {
            navigator.navigate(ContactUsScreenDestination)
        }
        Spacer(modifier = Modifier.height(0.dp))
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(
                tween(durationMillis = 500, delayMillis = 2300)
            ) + slideInHorizontally(
                animationSpec = tween(durationMillis = 500, delayMillis = 2000)
            )
        ) {
            CustomOutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                label = logoutText,
                icon = R.drawable.logout
            ) {
                onClick()
            }
        }
    }
}

@Composable
fun ProfileItem(
    label: String,
    delay: Int = 0,
    isVisible: Boolean,
    onClick: () -> Unit
) {

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(
            tween(durationMillis = 500, delayMillis = delay)
        ) + slideInHorizontally(
            animationSpec = tween(durationMillis = 500, delayMillis = delay)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12))
                .background(Color(WhiteSoft))
                .clickable { onClick() }
                .padding(horizontal = 20.dp, vertical = 15.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = Color(Black)
                )
                Icon(
                    painter = painterResource(R.drawable.next_round),
                    contentDescription = null,
                    modifier = Modifier.size(15.dp)
                )
            }
        }
    }
}
