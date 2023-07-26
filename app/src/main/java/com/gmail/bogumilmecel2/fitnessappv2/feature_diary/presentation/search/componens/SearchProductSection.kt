package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search.componens

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.util.ErrorUtils
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search.SearchEvent
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search.SearchState
import com.gmail.bogumilmecel2.ui.components.base.ButtonParams
import com.gmail.bogumilmecel2.ui.components.base.HeightSpacer
import com.gmail.bogumilmecel2.ui.components.base.IconVector
import com.gmail.bogumilmecel2.ui.components.complex.SearchButtonParams
import com.gmail.bogumilmecel2.ui.components.complex.SearchButtonRow
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme
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
        HeightSpacer()

        SearchButtonRow(
            buttons = listOf(
                SearchButtonParams(
                    buttonParams = ButtonParams(
                        text = stringResource(id = R.string.scan_barcode),
                        onClick = {
                            if (!cameraPermissionState.status.isGranted) {
                                onEvent(SearchEvent.ShowedPermissionDialog)
                                cameraPermissionState.launchPermissionRequest()
                            } else {
                                onEvent(SearchEvent.ClickedScanButton)
                            }
                        }
                    ),
                    icon = IconVector.barcode(),
                ),
                SearchButtonParams(
                    buttonParams = ButtonParams(
                        text = stringResource(id = R.string.create_product),
                        onClick = {
                            onEvent(SearchEvent.ClickedNewProduct)
                        }
                    ),
                    icon = IconVector.Add
                )
            ),
            buttonsColor = FitnessAppTheme.colors.Tertiary
        )

        HeightSpacer()

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
            }
//            else if (state.productItems.isNotEmpty()) {
//
//                val items = state.productItems
//
//                LazyColumn(modifier = Modifier.fillMaxWidth()) {
//                    items(items.size) { itemPosition ->
//                        val product = items[itemPosition]
//                        SearchItem(
//                            name = product.name,
//                            unit = stringResource(id = product.measurementUnit.getStringRes()),
//                            weight = product.containerWeight ?: 100,
//                            calories = product.nutritionValues.calories,
//                            onItemClick = {
//                                onEvent(
//                                    SearchEvent.ClickedProduct(
//                                        product = items[itemPosition]
//                                    )
//                                )
//                            }
//                        )
//                    }
//                }
//            }
        }
    }
}