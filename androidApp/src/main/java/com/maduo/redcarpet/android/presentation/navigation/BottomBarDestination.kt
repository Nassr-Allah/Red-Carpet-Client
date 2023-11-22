package com.maduo.redcarpet.android.presentation.navigation

import com.maduo.redcarpet.android.R
import com.maduo.redcarpet.android.presentation.destinations.*
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val icon: Int,
    val label: String,
    val index: Int
) {

    Home(MainScreenDestination, R.drawable.ic_home, "Home", 0),
    Cart(DesignListScreenDestination, R.drawable.tshirt, "Designs", 1),
    Course(CoursesScreenDestination, R.drawable.ic_bookmark, "Courses", 2),
    Profile(ProfileScreenDestination, R.drawable.ic_profile, "Profile", 3)

}