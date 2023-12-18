package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.search

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.data.singleton.CurrentDate
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.BackHandler
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.Beige1
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.BlueViolet3
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.search.componens.BottomSearchAddItem
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.search.componens.SearchButton
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.search.componens.SearchProductItem
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.search.componens.SearchTopSection
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.shared.ScannerSection
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SearchScreen(
    mealName: String,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state = viewModel.searchState.collectAsState().value

    val cameraPermissionState = rememberPermissionState(
        permission = Manifest.permission.CAMERA
    )

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.initializeHistory()
    }

    val cameraPermissionErrorMessage =
        stringResource(id = R.string.camera_permission_is_required_to_scan_a_product_barcode)

    LaunchedEffect(key1 = cameraPermissionState) {
        if (cameraPermissionState.status is PermissionStatus.Denied && state.hasPermissionDialogBeenShowed) {
            scaffoldState.snackbarHostState.showSnackbar(cameraPermissionErrorMessage)
        }
    }

    LaunchedEffect(key1 = state) {
        viewModel.searchState.collect {
            it.errorMessage?.let { error ->
                if (error != state.lastErrorMessage) {
                    scaffoldState.snackbarHostState.showSnackbar(error)
                }
            }
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
            },
            bottomBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(elevation = 24.dp)
                ) {
                    BottomSearchAddItem(
                        modifier = Modifier.weight(1F),
                        onClick = {
                            viewModel.onEvent(SearchEvent.ClickedNewProduct)
                        },
                        name = stringResource(id = R.string.product),
                        color = BlueViolet3
                    )

                    BottomSearchAddItem(
                        modifier = Modifier.weight(1F),
                        onClick = {

                        },
                        name = stringResource(id = R.string.recipe),
                        color = Color.DarkGray,
                        textColor = Color.DarkGray
                    )
                }
            }

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                SearchTopSection(
                    modifier = Modifier,
                    searchBarText = state.searchBarText,
                    mealName = mealName,
                    date = CurrentDate.dateModel(LocalContext.current).valueToDisplay
                        ?: CurrentDate.dateModel(LocalContext.current).date,
                    onEvent = { searchEvent ->
                        viewModel.onEvent(searchEvent)
                    }
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 8.dp)
                ) {

                    SearchButton(
                        text = stringResource(id = R.string.scan),
                        color = Beige1,
                        onClick = {
                            if (!cameraPermissionState.status.isGranted) {
                                viewModel.onEvent(SearchEvent.ShowedPermissionDialog)
                                cameraPermissionState.launchPermissionRequest()
                            } else {
                                viewModel.onEvent(SearchEvent.ClickedScanButton)
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.barcode_scan),
                                contentDescription = stringResource(id = R.string.scan),
                                tint = Color.Black
                            )
                        },
                        modifier = Modifier
                            .weight(1F)
                    )

                    SearchButton(
                        text = stringResource(id = R.string.filter),
                        modifier = Modifier.weight(1F),
                        color = MaterialTheme.colors.secondary,
                        onClick = {

                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.filter),
                                contentDescription = stringResource(id = R.string.filter),
                                tint = Color.Black
                            )
                        },
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    if (state.isLoading) {
                        Row(
                            modifier = Modifier
                                .align(Alignment.Center)
                        ) {
                            CircularProgressIndicator()
                        }
                    } else if (state.barcode != null) {
                        Column(
                            modifier = Modifier
                                .align(Alignment.Center)
                        ) {

                            Text(
                                text = stringResource(id = R.string.there_is_no_product_with_provided_barcode_do_you_want_to_add_it),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 30.dp),
                                style = MaterialTheme.typography.h1.copy(
                                    textAlign = TextAlign.Center
                                )
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = {
                                    viewModel.onEvent(SearchEvent.ClickedNewProduct)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 60.dp)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.add),
                                    style = MaterialTheme.typography.button
                                )

                            }

                            Spacer(modifier = Modifier.height(32.dp))

                        }
                    } else if (state.items.isNotEmpty()) {

                        val items = state.items

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            items(items.size) { itemPosition ->
                                SearchProductItem(product = items[itemPosition].product) {
                                    viewModel.onEvent(
                                        SearchEvent.ClickedSearchItem(
                                            item = items[itemPosition],
                                            mealName = mealName
                                        )
                                    )
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