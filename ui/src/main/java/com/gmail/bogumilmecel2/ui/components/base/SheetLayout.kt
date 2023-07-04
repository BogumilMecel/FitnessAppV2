package com.gmail.bogumilmecel2.ui.components.base

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SheetLayout(
    sheetState: ModalBottomSheetState,
    bottomSheetContent: @Composable () -> Unit,
    onBottomSheetDismissed: () -> Unit,
    content: @Composable () -> Unit
) {
    if (sheetState.currentValue != ModalBottomSheetValue.Hidden) {
        DisposableEffect(Unit) {
            onDispose {
                onBottomSheetDismissed()
            }
        }
    }

    ModalBottomSheetLayout(
        sheetContent = {
            bottomSheetContent()
        },
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetBackgroundColor = FitnessAppTheme.colors.BackgroundSecondary
    ) {
        content()
    }
}