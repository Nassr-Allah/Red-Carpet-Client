package com.maduo.redcarpet.android.presentation.feature_course.course_details_srceen

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.maduo.redcarpet.android.R
import com.maduo.redcarpet.android.presentation.components.*
import com.maduo.redcarpet.android.presentation.destinations.RegistrationConfirmationScreenDestination
import com.maduo.redcarpet.android.presentation.navigation.MainAppNavGraph
import com.maduo.redcarpet.domain.model.Course
import com.maduo.redcarpet.presentation.Gray
import com.maduo.redcarpet.presentation.Red
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.valentinilk.shimmer.shimmer

@MainAppNavGraph
@Destination
@Composable
fun CourseDetailsScreen(
    courseId: String,
    viewModel: CourseDetailsViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current.applicationContext

    var isVisible by remember {
        mutableStateOf(false)
    }

    var isImageLoading by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(key1 = true) {
        viewModel.getCourseDetails(courseId)
    }

    LaunchedEffect(key1 = state) {
        if (!state.isLoading) {
            if (state.error.isNotEmpty()) {
                Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
            }
            if (state.isRegistrationCreated && state.response.isNotEmpty()) {
                Toast.makeText(context, state.response, Toast.LENGTH_SHORT).show()
                navigator.navigate(RegistrationConfirmationScreenDestination)
            }
            if (state.course != null) {
                isVisible = true
            }
        }
    }

    val modifier = if (isImageLoading) Modifier.shimmer() else Modifier

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
                    .background(Color(Gray))
            ) {
                AsyncImage(
                    model = state.course?.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    onLoading = {
                        isImageLoading = true
                    },
                    onSuccess = {
                        isImageLoading = false
                    }
                )
                Row(modifier = Modifier.padding(20.dp)) {
                    IconRoundBackground(icon = R.drawable.arrow_left) {
                        navigator.navigateUp()
                    }
                }
            }
            Spacer(modifier = Modifier.height(25.dp))
            CourseDetails(
                course = state.course ?: Course(),
                isLoading = state.isLoading,
                isVisible = isVisible,
                onClick = {
                    viewModel.createRegistration(courseId)
                },
            )
        }
    }
}


@Composable
fun CourseDetails(
    course: Course,
    isLoading: Boolean,
    isVisible: Boolean,
    onClick: () -> Unit
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ItemDetailTitle(
            title = course.title,
            price = "${course.price} DA",
            delay = 200,
            isVisible = isVisible
        )
        Spacer(modifier = Modifier.height(25.dp))
        ItemDetail(
            title = stringResource(R.string.description),
            description = course.description,
            delay = 1100,
            isVisible = isVisible
        )
        Spacer(modifier = Modifier.height(25.dp))
        ItemDetail(
            title = stringResource(R.string.type_of_course),
            description = course.type,
            delay = 1400,
            isVisible = isVisible
        )
        Spacer(modifier = Modifier.height(25.dp))
        ItemDetail(
            title = stringResource(R.string.course_duration),
            description = course.duration,
            delay = 1700,
            isVisible = isVisible
        )
        Spacer(modifier = Modifier.height(35.dp))
        if (isLoading) {
            LoadingAnimation(
                circleColor = Color(Red),
                circleSize = 10.dp
            )
        } else {
            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn(
                    tween(durationMillis = 500, delayMillis = 1900)
                ) + slideInHorizontally(
                    animationSpec = tween(durationMillis = 500, delayMillis = 1900)
                )
            ) {
                CustomButton(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    label = stringResource(R.string.register),
                ) {
                    onClick()
                }
            }
        }
        Spacer(modifier = Modifier.height(25.dp))
    }
}
