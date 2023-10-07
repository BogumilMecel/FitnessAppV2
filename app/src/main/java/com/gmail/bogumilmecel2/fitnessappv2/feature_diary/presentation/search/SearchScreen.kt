package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.gmail.bogumilmecel2.fitnessappv2.common.util.ViewModelLayout
import com.gmail.bogumilmecel2.fitnessappv2.destinations.ProductScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.ProductResult
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search.componens.SearchButtonParams
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search.componens.SearchSection
import com.gmail.bogumilmecel2.ui.components.base.ButtonParams
import com.gmail.bogumilmecel2.ui.components.base.CustomBasicTextField
import com.gmail.bogumilmecel2.ui.components.base.CustomDialog
import com.gmail.bogumilmecel2.ui.components.base.CustomIcon
import com.gmail.bogumilmecel2.ui.components.base.CustomTabRow
import com.gmail.bogumilmecel2.ui.components.base.HeightSpacer
import com.gmail.bogumilmecel2.ui.components.base.IconVector
import com.gmail.bogumilmecel2.ui.components.complex.HeaderRow
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.result.ResultRecipient

@OptIn(ExperimentalFoundationApi::class)
@Destination(navArgsDelegate = SearchNavArguments::class)
@Composable
fun SearchScreen(
    navigator: DestinationsNavigator,
    resultBackNavigator: ResultBackNavigator<ProductResult>,
    resultRecipient: ResultRecipient<ProductScreenDestination, ProductResult>
) {
    hiltViewModel<SearchViewModel>().ViewModelLayout(
        navigator = navigator,
        resultBackNavigator = resultBackNavigator
    ) { viewModel ->
        val state = viewModel.state.collectAsStateWithLifecycle().value

        val pagerState = rememberPagerState(initialPage = SearchTab.EVERYTHING.ordinal) {
            SearchTab.entries.size
        }

        val noRecipeTabPagerState = rememberPagerState(initialPage = SearchTab.EVERYTHING.ordinal) {
            listOf(
                SearchTab.EVERYTHING,
                SearchTab.PRODUCTS
            ).size
        }

        resultRecipient.onNavResult { result ->
            if (result is NavResult.Value) {
                viewModel.onEvent(SearchEvent.ReceivedProductResult(result.value))
            }
        }

        LaunchedEffect(
            key1 = state.selectedTabIndex,
            block = {
                if (state.recipeTabVisible) {
                    pagerState.animateScrollToPage(page = state.selectedTabIndex)
                } else {
                    noRecipeTabPagerState.animateScrollToPage(page = state.selectedTabIndex)
                }
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
                val backgroundColor = FitnessAppTheme.colors.BackgroundSecondary

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = backgroundColor)
                        .padding(top = 8.dp)
                ) {
                    HeaderRow(
                        middlePrimaryText = state.headerPrimaryText,
                        middleSecondaryText = state.headerSecondaryText,
                        onBackPressed = {
                            viewModel.onEvent(SearchEvent.ClickedBackArrow)
                        }
                    )

                    HeightSpacer(8.dp)

                    CustomBasicTextField(
                        value = state.searchBarText,
                        placeholder = when (state.selectedTabIndex) {
                            SearchTab.RECIPES.ordinal -> stringResource(id = R.string.search_for_recipes)
                            else -> stringResource(id = R.string.search_for_products)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag(stringResource(id = R.string.SEARCH_TEXT_FIELD))
                            .padding(horizontal = 16.dp),
                        singleLine = true,
                        onValueChange = {
                            viewModel.onEvent(SearchEvent.EnteredSearchText(it))
                        }
                    )

                    HeightSpacer(8.dp)

                    CustomTabRow(
                        selectedTabIndex = state.selectedTabIndex,
                        tabs = if (state.recipeTabVisible) {
                            SearchTab.entries.map {
                                stringResource(id = it.textResId)
                            }
                        } else buildList {
                            add(stringResource(id = SearchTab.EVERYTHING.textResId))
                            add(stringResource(id = SearchTab.PRODUCTS.textResId))
                        },
                        backgroundColor = backgroundColor,
                        onTabSelected = {
                            viewModel.onEvent(SearchEvent.SelectedTab(it))
                        }
                    )
                }

                HorizontalPager(
                    state = if (state.recipeTabVisible) pagerState else noRecipeTabPagerState,
                    userScrollEnabled = false
                ) { pagerScope ->
                    val currentTab = SearchTab.entries[pagerScope]

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