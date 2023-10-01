package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.components.BackHandler
import com.gmail.bogumilmecel2.fitnessappv2.common.util.ViewModelLayout
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search.componens.SearchButtonParams
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search.componens.SearchSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search.componens.SearchTopSection
import com.gmail.bogumilmecel2.ui.components.base.ButtonParams
import com.gmail.bogumilmecel2.ui.components.base.CustomDialog
import com.gmail.bogumilmecel2.ui.components.base.CustomIcon
import com.gmail.bogumilmecel2.ui.components.base.IconVector
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalFoundationApi::class)
@Destination(navArgsDelegate = SearchNavArguments::class)
@Composable
fun SearchScreen(navigator: DestinationsNavigator) {
    hiltViewModel<SearchViewModel>().ViewModelLayout(navigator = navigator) { viewModel ->
        val state = viewModel.state.collectAsStateWithLifecycle().value
        val pagerState = rememberPagerState(initialPage = SearchTab.EVERYTHING.ordinal) {
            SearchTab.values().size
        }

        LaunchedEffect(
            key1 = state.selectedTabIndex,
            block = {
                pagerState.animateScrollToPage(page = state.selectedTabIndex)
            }
        )

        BackHandler(
            enabled = state.selectedTabIndex != SearchTab.EVERYTHING.ordinal
        ) {
            if (state.selectedTabIndex != SearchTab.EVERYTHING.ordinal) {
                viewModel.onEvent(SearchEvent.SelectedTab(SearchTab.EVERYTHING.ordinal))
            }
        }

        if (state.noProductFoundVisible) {
            CustomDialog(
                title = stringResource(id = R.string.there_is_no_product_with_provided_barcode_do_you_want_to_add_it),
                secondaryText = stringResource(id = R.string.search_add_it_now),
                primaryButtonParams = ButtonParams(
                    text = stringResource(id = R.string.add),
                    onClick = {
                        viewModel.onEvent(SearchEvent.ClickedNewProduct)
                    }
                ),
                secondaryButtonParams = ButtonParams(
                    text = stringResource(id = R.string.cancel),
                    onClick = {
                        viewModel.onEvent(SearchEvent.DismissedNoProductFoundDialog)
                    }
                ),
                onDismissRequest = {
                    viewModel.onEvent(SearchEvent.DismissedNoProductFoundDialog)
                }
            )
        }

        Scaffold(
            floatingActionButton = {
                AnimatedVisibility(
                    visible = state.searchButtonVisible,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    ExtendedFloatingActionButton(
                        onClick = { viewModel.onEvent(SearchEvent.ClickedSearch) },
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
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = paddingValues.calculateBottomPadding())
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
                    val currentTab = SearchTab.values()[pagerScope]

                    SearchSection(
                        modifier = Modifier,
                        contentColor = currentTab.getTabColor(),
                        buttonParams = currentTab.getButtonParams(onEvent = { viewModel.onEvent(it) }),
                        searchItems = currentTab.getItems(state = state),
                        onListEndReached = { viewModel.onEvent(SearchEvent.ReachedListEnd) }
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchTab.getTabColor() = when (this) {
    SearchTab.EVERYTHING -> FitnessAppTheme.colors.Secondary
    SearchTab.RECIPES -> FitnessAppTheme.colors.Tertiary
    SearchTab.PRODUCTS -> FitnessAppTheme.colors.Quaternary
}

@Composable
private fun SearchTab.getItems(state: SearchState) = when (this) {
    SearchTab.EVERYTHING -> state.everythingSearchItems
    SearchTab.RECIPES -> state.myRecipesSearchItems
    SearchTab.PRODUCTS -> state.myProductsSearchItems
}

@Composable
private fun SearchTab.getButtonParams(onEvent: (SearchEvent) -> Unit) = when (this) {
    SearchTab.EVERYTHING -> listOf(
        SearchButtonParams(
            buttonParams = ButtonParams(
                text = stringResource(id = R.string.search_quick_add),
                onClick = {}
            ),
            icon = IconVector.Add
        ),
        SearchButtonParams(
            buttonParams = ButtonParams(
                text = stringResource(id = R.string.scan_barcode),
                onClick = { onEvent(SearchEvent.ClickedScanButton) }
            ),
            icon = IconVector.barcode(),
        )
    )

    SearchTab.RECIPES -> listOf(
        SearchButtonParams(
            buttonParams = ButtonParams(
                text = stringResource(id = R.string.search_browse_recipes),
                onClick = {}
            ),
            icon = IconVector.Search,
        ),
        SearchButtonParams(
            buttonParams = ButtonParams(
                text = stringResource(id = R.string.add_recipe),
                onClick = { onEvent(SearchEvent.ClickedCreateNewRecipe) }
            ),
            icon = IconVector.Add
        )
    )

    SearchTab.PRODUCTS -> listOf(
        SearchButtonParams(
            buttonParams = ButtonParams(
                text = stringResource(id = R.string.scan_barcode),
                onClick = { onEvent(SearchEvent.ClickedScanButton) }
            ),
            icon = IconVector.barcode(),
        ),
        SearchButtonParams(
            buttonParams = ButtonParams(
                text = stringResource(id = R.string.create_product),
                onClick = { onEvent(SearchEvent.ClickedNewProduct) }
            ),
            icon = IconVector.Add
        )
    )
}