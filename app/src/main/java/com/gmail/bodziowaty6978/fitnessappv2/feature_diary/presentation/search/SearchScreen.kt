package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.data.singleton.CurrentDate
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.BackHandler
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.Grey
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.search.componens.SearchProductSection
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.search.componens.SearchRecipeSection
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.search.componens.SearchTopSection
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.shared.ScannerSection
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class, ExperimentalLifecycleComposeApi::class, ExperimentalPagerApi::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state = viewModel.searchState.collectAsStateWithLifecycle().value

    val scope = rememberCoroutineScope()

    val scaffoldState = rememberScaffoldState()
    val pagerState = rememberPagerState(initialPage = 0)

    LaunchedEffect(key1 = true) {
        viewModel.initializeHistory()
    }

    LaunchedEffect(key1 = true) {
        viewModel.errorState.collect {error ->
            scaffoldState.snackbarHostState.showSnackbar(error)
        }
    }

    BackHandler(
        enabled = state.isScannerVisible
    ) {
        if (state.isScannerVisible) {
            viewModel.onEvent(SearchEvent.ClosedScanner)
        }
    }


    if (!state.isScannerVisible) {
        Scaffold(
            scaffoldState = scaffoldState,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        viewModel.onEvent(SearchEvent.ClickedSearch)
                    },
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier
                        .testTag(stringResource(id = R.string.SEARCH_BUTTON))
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "search"
                    )
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                SearchTopSection(
                    modifier = Modifier
                        .background(Grey),
                    searchBarText = state.searchBarText,
                    mealName = state.mealName,
                    date = CurrentDate.dateModel(LocalContext.current).valueToDisplay
                        ?: CurrentDate.dateModel(LocalContext.current).date,
                    onEvent = { searchEvent ->
                        viewModel.onEvent(searchEvent)
                    },
                    pagerState = pagerState,
                    scope = scope
                )

                HorizontalPager(
                    count = 2,
                    state = pagerState
                ) {pagerScope ->
                    when(pagerScope){
                        0 -> {
                            SearchProductSection(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(paddingValues),
                                onEvent = { event ->
                                    viewModel.onEvent(event)
                                },
                                showSnackbar = {
                                    scope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar(it)
                                    }
                                },
                                state = state
                            )
                        }
                        1 -> {
                            SearchRecipeSection(
                                calculatedRecipes = state.calculatedRecipes,
                                onEvent = {
                                    viewModel.onEvent(it)
                                }
                            )
                        }
                    }
                }

            }
        }
    } else {
        ScannerSection(
            onBarcodeScanned = { scannedBarcode ->
                scannedBarcode?.let { barcode ->
                    viewModel.onEvent(SearchEvent.ScannedBarcode(barcode))
                } ?: viewModel.onEvent(SearchEvent.ClosedScanner)
            }
        )
    }
}