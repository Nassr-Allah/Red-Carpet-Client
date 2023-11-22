package com.maduo.redcarpet.android.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.material3.R
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.maduo.redcarpet.android.presentation.NavGraphs
import com.maduo.redcarpet.android.presentation.appCurrentDestinationAsState
import com.maduo.redcarpet.android.presentation.destinations.Destination
import com.maduo.redcarpet.android.presentation.destinations.MainScreenDestination
import com.maduo.redcarpet.android.presentation.startAppDestination
import com.maduo.redcarpet.presentation.Gray
import com.maduo.redcarpet.presentation.Red
import com.maduo.redcarpet.presentation.White
import com.maduo.redcarpet.presentation.WhiteSoft
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

@Composable
fun BottomBarNavigation() {
    val navController = rememberNavController()

    var selectedIndex by remember {
        mutableStateOf(0)
    }

    Scaffold(
        bottomBar = {
            NavigationBar (
                containerColor = Color(WhiteSoft),
                contentColor = Color(Gray),
            )
            {
                val currentDestination: Destination = navController.appCurrentDestinationAsState().value
                    ?: NavGraphs.mainApp.startAppDestination
                BottomBarDestination.values().forEach { item ->
                    NavigationBarItem(
                        onClick = {
                            selectedIndex = item.index
                            navController.navigate(item.direction) {
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(item.icon),
                                contentDescription = null,
                                modifier = Modifier.size(25.dp)
                            )
                        },
                        label = {
                            Text(
                                text = item.label,
                                style = MaterialTheme.typography.labelSmall
                            )
                        },
                        selected = selectedIndex == item.index,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(Red),
                            selectedTextColor = Color(Red),
                            unselectedIconColor = Color(Gray),
                            unselectedTextColor = Color.Transparent,
                            indicatorColor = Color(0xFFFEFEFE)
                        )
                    )
                }
            }
        }
    ) {
        Surface(color = Color(0xFFFEFEFE)) {
            DestinationsNavHost(
                navGraph = NavGraphs.mainApp,
                modifier = Modifier.padding(it),
                navController = navController
            )
        }
    }
}