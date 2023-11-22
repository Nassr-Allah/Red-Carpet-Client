package com.maduo.redcarpet.android.presentation.feature_store.design_list_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.maduo.redcarpet.android.MyApplicationTheme
import com.maduo.redcarpet.android.presentation.components.*
import com.maduo.redcarpet.android.presentation.destinations.DesignDetailsScreenDestination
import com.maduo.redcarpet.android.presentation.navigation.MainAppNavGraph
import com.maduo.redcarpet.domain.model.Design
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.maduo.redcarpet.android.R

@MainAppNavGraph
@Destination
@Composable
fun DesignListScreen(
    viewModel: DesignListViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    val state = viewModel.state.collectAsState().value

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
            DesignListScreenHeader(viewModel)
            Spacer(modifier = Modifier.height(30.dp))
            if (state.isLoading) {
                LoadingShimmerList()
            } else {
                DesignListSection(viewModel, state.designs) {
                    navigator.navigate(DesignDetailsScreenDestination(designId = it.id))
                }
            }
        }
    }
}

@Composable
fun DesignListScreenHeader(viewModel: DesignListViewModel) {
    Column {
        ScreenTitleSubtitle(title = stringResource(R.string.design), subtitle = stringResource(R.string.browse_through_designs))
        Spacer(modifier = Modifier.height(25.dp))
        SearchBar(
            query = viewModel.searchQuery.value,
            onValueChange = {
                viewModel.searchQuery.value = it
                viewModel.filterDesignsByQuery(it)
            }
        )
    }
}

@Composable
fun DesignListSection(viewModel: DesignListViewModel, designs: List<Design>, onClick: (Design) -> Unit) {
    val categories = listOf(
        Pair("All", stringResource(R.string.all)),
        Pair("Male", stringResource(R.string.male)),
        Pair("Female", stringResource(R.string.female)),
        Pair("Children", stringResource(R.string.children)),
        Pair("Regular", stringResource(R.string.regular)),
        Pair("VIP", stringResource(R.string.vip)),
        Pair("Other", stringResource(R.string.other)),
    )

    Column {
        ChipsAsPairs(
            items = categories,
            label = stringResource(R.string.choose_category),
            onSelected = {
                viewModel.filterListByCategory(it)
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        DesignList(designs = designs, onClick = { onClick(it) })
    }
}

@Composable
fun DesignList(designs: List<Design>, onClick: (Design) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(designs) { design ->  
            CustomListItem(design = design) {
                onClick(design)
            }
        }
    }
}
