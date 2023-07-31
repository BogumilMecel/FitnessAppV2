package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.components.BackHandler
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search.componens.SearchEverythingSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search.componens.SearchList
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search.componens.SearchProductSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search.componens.SearchTopSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.shared.ScannerSection
import com.gmail.bogumilmecel2.ui.components.base.ButtonParams
import com.gmail.bogumilmecel2.ui.components.base.CustomIcon
import com.gmail.bogumilmecel2.ui.components.base.HeightSpacer
import com.gmail.bogumilmecel2.ui.components.base.IconVector
import com.gmail.bogumilmecel2.ui.components.complex.SearchButtonParams
import com.gmail.bogumilmecel2.ui.components.complex.SearchButtonRow
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme
import com.ramcosta.composedestinations.annotation.Destination

@OptIn(ExperimentalFoundationApi::class)
@Destination(
    navArgsDelegate = SearchNavArguments::class
)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val pagerState = rememberPagerState(initialPage = SearchTab.EVERYTHING.ordinal) {
        SearchTab.values().size
    }

    LaunchedEffect(key1 = true) {
        viewModel.initializeHistory()
        viewModel.initializeData()
    }

    LaunchedEffect(
        key1 = state.selectedTabIndex,
        block = {
            pagerState.animateScrollToPage(page = state.selectedTabIndex)
        }
    )

    BackHandler(
        enabled = state.isScannerVisible || state.selectedTabIndex != SearchTab.EVERYTHING.ordinal
    ) {
        if (state.isScannerVisible) {
            viewModel.onEvent(SearchEvent.ClosedScanner)
        } else if (state.selectedTabIndex != SearchTab.EVERYTHING.ordinal) {
            viewModel.onEvent(SearchEvent.SelectedTab(SearchTab.EVERYTHING.ordinal))
        }
    }

    if (!state.isScannerVisible) {
        Scaffold(
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = {
                        viewModel.onEvent(SearchEvent.ClickedSearch)
                    },
                    backgroundColor = FitnessAppTheme.colors.Primary,
                    modifier = Modifier.testTag(stringResource(id = R.string.SEARCH_BUTTON)),
                    text = {
                        Text(
                            text = stringResource(id = R.string.search).uppercase(),
                            color = FitnessAppTheme.colors.Black
                        )
                    },
                    icon = {
                        CustomIcon(
                            icon = IconVector.Search,
                            iconColor = FitnessAppTheme.colors.Black
                        )
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                SearchTopSection(
                    searchBarText = state.searchBarText,
                    mealName = stringResource(id = state.mealName.getDisplayValue()),
                    date = state.date,
                    onEvent = { searchEvent ->
                        viewModel.onEvent(searchEvent)
                    },
                    selectedTabIndex = state.selectedTabIndex
                )

                HorizontalPager(
                    state = pagerState,
                    userScrollEnabled = false
                ) { pagerScope ->
                    when (pagerScope) {
                        SearchTab.EVERYTHING.ordinal -> {
                            SearchEverythingSection(
                                searchItems = state.everythingSearchItems,
                                isBarcodeLayoutVisible = state.barcode != null,
                                onScanBarcodeClicked = {
                                    viewModel.onEvent(SearchEvent.ClickedScanButton)
                                },
                                onBarcodeAddProductClicked = {
                                    viewModel.onEvent(SearchEvent.ClickedNewProduct)
                                }
                            )
                        }

                        SearchTab.PRODUCTS.ordinal -> SearchProductSection(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues),
                            onEvent = { event ->
                                viewModel.onEvent(event)
                            },
                            state = state
                        )

                        SearchTab.RECIPES.ordinal -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(
                                        top = 16.dp,
                                        bottom = paddingValues.calculateBottomPadding()
                                    )
                            ) {
                                SearchButtonRow(
                                    buttons = listOf(
                                        SearchButtonParams(
                                            buttonParams = ButtonParams(
                                                text = stringResource(id = R.string.search_browse_recipes),
                                                onClick = {

                                                }
                                            ),
                                            icon = IconVector.Search,
                                        ),
                                        SearchButtonParams(
                                            buttonParams = ButtonParams(
                                                text = stringResource(id = R.string.add_recipe),
                                                onClick = {
                                                    viewModel.onEvent(SearchEvent.ClickedCreateNewRecipe)
                                                }
                                            ),
                                            icon = IconVector.Add
                                        )
                                    ),
                                    buttonsColor = FitnessAppTheme.colors.Quaternary
                                )

                                HeightSpacer()

                                if (state.myRecipesSearchItems.isNotEmpty()) {
                                    SearchList(state.myRecipesSearchItems)
                                }
                            }
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