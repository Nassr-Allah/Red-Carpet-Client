package com.maduo.redcarpet.android.presentation.feature_course.courses_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.maduo.redcarpet.android.R
import com.maduo.redcarpet.android.presentation.components.*
import com.maduo.redcarpet.android.presentation.destinations.CourseDetailsScreenDestination
import com.maduo.redcarpet.android.presentation.feature_store.design_list_screen.DesignListViewModel
import com.maduo.redcarpet.android.presentation.navigation.MainAppNavGraph
import com.maduo.redcarpet.domain.model.Course
import com.maduo.redcarpet.presentation.Black
import com.maduo.redcarpet.presentation.Gray
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.valentinilk.shimmer.shimmer

@MainAppNavGraph
@Destination
@Composable
fun CoursesScreen(
    viewModel: CoursesViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current.applicationContext

    val categories = listOf(
        Pair("All", stringResource(R.string.all)),
        Pair("Handmade", stringResource(R.string.handmade)),
        Pair("Digital", stringResource(R.string.digital)),
        Pair("Sewing", stringResource(R.string.sewing)),
        Pair("Modern", stringResource(R.string.modern)),
        Pair("Other", stringResource(R.string.other)),
    )

    LaunchedEffect(key1 = state) {
        if (!state.isLoading) {
            if (state.error.isNotEmpty()) {
                Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
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
            CoursesScreenHeader(viewModel = viewModel)
            Spacer(modifier = Modifier.height(20.dp))
            ChipsAsPairs(
                items = categories,
                label = stringResource(R.string.filter_by_category),
                onSelected = {
                    viewModel.filterByCategory(it)
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            if (state.isLoading) {
                LoadingShimmerList()
            } else {
                CoursesList(list = viewModel.filteredCourses) {
                    navigator.navigate(CourseDetailsScreenDestination(courseId = it))
                }
            }
        }
    }
}


@Composable
fun CoursesScreenHeader(viewModel: CoursesViewModel) {
    
    var query by remember {
        mutableStateOf("")
    }
    
    Column {
        ScreenTitleSubtitle(title = stringResource(R.string.courses), subtitle = stringResource(R.string.browse_through_courses))
        Spacer(modifier = Modifier.height(25.dp))
        SearchBar(
            query = query,
            onValueChange = {
                query = it
                viewModel.filterCourses(query)
            }
        )
    }
}


@Composable
fun CoursesList(list: List<Course>, onClick: (String) -> Unit) {
    Column {
        Text(
            text = stringResource(R.string.courses),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = Color(Black)
        )
        Spacer(modifier = Modifier.height(10.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(list) { course ->
                CourseItem(course = course) {
                    onClick(course.id)
                }
            }
        }
    }
}

@Composable
fun CourseItem(course: Course, onClick: () -> Unit) {

    var isImageLoading by remember {
        mutableStateOf(false)
    }

    val modifier = if (isImageLoading) {
        Modifier
            .clip(RoundedCornerShape(15))
            .shimmer()
            .background(Color(Gray)) } else { Modifier }

    Column(
        modifier = Modifier
            .fillMaxHeight(0.4f)
            .clickable { onClick() }) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(220.dp),
            shape = RoundedCornerShape(15)
        ) {
            AsyncImage(
                model = course.image,
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
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = course.title,
            color = Color(Black),
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 5.dp)
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = "${course.price} DZD",
            color = Color(Black),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 5.dp)
        )
    }
}
