package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.search.componens

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.BlueViolet1
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.OrangeYellow1
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ErrorUtils
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.search.SearchEvent
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.search.SearchState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SearchProductSection(
    modifier: Modifier,
    onEvent: (SearchEvent) -> Unit,
    state: SearchState
) {
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
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
        ) {
            SearchButton(
                text = stringResource(id = R.string.scan),
                color = BlueViolet1,
                onClick = {
                    if (!cameraPermissionState.status.isGranted) {
                        onEvent(SearchEvent.ShowedPermissionDialog)
                        cameraPermissionState.launchPermissionRequest()
                    } else {
                        onEvent(SearchEvent.ClickedScanButton)
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
                    .testTag(stringResource(id = R.string.scan))
            )

            SearchButton(
                text = stringResource(id = R.string.create_product),
                modifier = Modifier.weight(1F),
                color = OrangeYellow1,
                onClick = {
                    onEvent(SearchEvent.ClickedNewProduct)
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.add),
                        tint = Color.Black
                    )
                },
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

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
                            onEvent(SearchEvent.ClickedNewProduct)
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
                        val product = items[itemPosition]
                        SearchProductItem(
                            name = product.name,
                            unit = product.unit,
                            weight = product.containerWeight,
                            calories = product.nutritionValues.calories
                        ) {
                            onEvent(
                                SearchEvent.ClickedProduct(
                                    product = items[itemPosition]
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}