package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search

import android.Manifest
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.components.BackHandler
import com.gmail.bogumilmecel2.fitnessappv2.common.util.ConfigureViewModel
import com.gmail.bogumilmecel2.fitnessappv2.common.util.ErrorUtils
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search.componens.SearchTopSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.shared.ScannerSection
import com.gmail.bogumilmecel2.ui.components.base.ButtonParams
import com.gmail.bogumilmecel2.ui.components.base.CustomButton
import com.gmail.bogumilmecel2.ui.components.base.CustomIcon
import com.gmail.bogumilmecel2.ui.components.base.HeightSpacer
import com.gmail.bogumilmecel2.ui.components.base.IconVector
import com.gmail.bogumilmecel2.ui.components.base.LoaderColumn
import com.gmail.bogumilmecel2.ui.components.complex.SearchButtonParams
import com.gmail.bogumilmecel2.ui.components.complex.SearchButtonRow
import com.gmail.bogumilmecel2.ui.components.complex.SearchList
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.ramcosta.composedestinations.annotation.Destination

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalPermissionsApi::class
)
@Destination(navArgsDelegate = SearchNavArguments::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val pagerState = rememberPagerState(initialPage = SearchTab.EVERYTHING.ordinal) {
        SearchTab.values().size
    }

    ConfigureViewModel(viewModel = viewModel)

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
                    containerColor = FitnessAppTheme.colors.Primary,
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
            Column(modifier = Modifier.fillMaxSize()) {
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
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 16.dp),
                            ) {
                                SearchButtonRow(
                                    buttons = listOf(
                                        SearchButtonParams(
                                            buttonParams = ButtonParams(
                                                text = stringResource(id = R.string.search_quick_add),
                                                onClick = {

                                                }
                                            ),
                                            icon = IconVector.Add
                                        ),
                                        SearchButtonParams(
                                            buttonParams = ButtonParams(
                                                text = stringResource(id = R.string.scan_barcode),
                                                onClick = { viewModel.onEvent(SearchEvent.ClickedScanButton) }
                                            ),
                                            icon = IconVector.barcode(),
                                        )
                                    ),
                                    buttonsColor = FitnessAppTheme.colors.Secondary
                                )

                                HeightSpacer()

                                Box {
                                    if (state.barcode != null) {
                                        Column(
                                            modifier = Modifier
                                                .align(Alignment.Center)
                                                .fillMaxWidth(),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = stringResource(id = R.string.there_is_no_product_with_provided_barcode_do_you_want_to_add_it),
                                                modifier = Modifier.padding(horizontal = 16.dp),
                                                style = FitnessAppTheme.typography.HeaderLarge,
                                                textAlign = TextAlign.Center
                                            )

                                            HeightSpacer()

                                            CustomButton(
                                                onClick = { viewModel.onEvent(SearchEvent.ClickedNewProduct) },
                                                text = stringResource(id = R.string.add)
                                            )
                                        }
                                    } else if (state.everythingSearchItems.isNotEmpty()) {
                                        LoaderColumn(isLoading = state.isLoading) {
                                            SearchList(
                                                items = state.everythingSearchItems,
                                                onScrollToEnd = {
                                                    viewModel.onEvent(SearchEvent.ReachedListEnd)
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        SearchTab.PRODUCTS.ordinal -> {
                            val cameraPermissionState = rememberPermissionState(
                                permission = Manifest.permission.CAMERA
                            )

                            val cameraPermissionErrorMessage =
                                stringResource(id = R.string.camera_permission_is_required_to_scan_a_product_barcode)

                            LaunchedEffect(key1 = cameraPermissionState) {
                                if (cameraPermissionState.status is PermissionStatus.Denied && state.hasPermissionDialogBeenShowed) {
                                    ErrorUtils.showSnackbarWithMessage(cameraPermissionErrorMessage)
                                }
                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(
                                        bottom = paddingValues.calculateBottomPadding(),
                                        top = 16.dp
                                    )
                            ) {
                                SearchButtonRow(
                                    buttons = listOf(
                                        SearchButtonParams(
                                            buttonParams = ButtonParams(
                                                text = stringResource(id = R.string.scan_barcode),
                                                onClick = {
                                                    if (!cameraPermissionState.status.isGranted) {
                                                        viewModel.onEvent(SearchEvent.ShowedPermissionDialog)
                                                        cameraPermissionState.launchPermissionRequest()
                                                    } else {
                                                        viewModel.onEvent(SearchEvent.ClickedScanButton)
                                                    }
                                                }
                                            ),
                                            icon = IconVector.barcode(),
                                        ),
                                        SearchButtonParams(
                                            buttonParams = ButtonParams(
                                                text = stringResource(id = R.string.create_product),
                                                onClick = {
                                                    viewModel.onEvent(SearchEvent.ClickedNewProduct)
                                                }
                                            ),
                                            icon = IconVector.Add
                                        )
                                    ),
                                    buttonsColor = FitnessAppTheme.colors.Tertiary
                                )

                                HeightSpacer()

                                Box(modifier = Modifier.fillMaxSize()) {
                                    if (state.isLoading) {
                                        Row(modifier = Modifier.align(Alignment.Center)) {
                                            CircularProgressIndicator()
                                        }
                                    } else if (state.myProductsSearchItems.isNotEmpty()) {
                                        SearchList(items = state.myProductsSearchItems)
                                    }
                                }
                            }
                        }

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