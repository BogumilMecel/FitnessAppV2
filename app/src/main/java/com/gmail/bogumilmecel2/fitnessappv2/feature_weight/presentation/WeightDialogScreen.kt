package com.gmail.bogumilmecel2.fitnessappv2.feature_weight.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.BaseResult
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.components.BackHandler
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.util.NonDismissibleDialog
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Constants
import com.gmail.bogumilmecel2.fitnessappv2.common.util.ViewModelLayout
import com.gmail.bogumilmecel2.ui.components.base.ButtonParams
import com.gmail.bogumilmecel2.ui.components.base.CustomDialog
import com.gmail.bogumilmecel2.ui.components.complex.DoubleNumberPicker
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator

@Destination(
    style = NonDismissibleDialog::class,
    navArgsDelegate = WeightDialogNavArguments::class
)
@Composable
fun WeightDialogScreen(
    navigator: DestinationsNavigator,
    resultBackNavigator: ResultBackNavigator<BaseResult>,
) {
    hiltViewModel<WeightDialogViewModel>().ViewModelLayout(
        navigator = navigator,
        resultBackNavigator = resultBackNavigator
    ) {
        val state by __state.collectAsStateWithLifecycle()
        BackHandler {
            onEvent(WeightDialogEvent.ClickedBack)
        }

        CustomDialog(
            title = stringResource(id = R.string.summary_weight_dialog_title),
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    DoubleNumberPicker(
                        modifier = Modifier,
                        value = state.currentWeight,
                        minValue = Constants.WEIGHT_PICKER_MIN_VALUE,
                        maxValue = Constants.WEIGHT_PICKER_MAX_VALUE,
                        onValueChange = { onEvent(WeightDialogEvent.EnteredWeight(it)) }
                    )
                }
            },
            primaryButtonParams = ButtonParams(
                text = stringResource(id = R.string.save),
                onClick = { onEvent(WeightDialogEvent.ClickedSave) }
            ),
            secondaryButtonParams = ButtonParams(
                text = stringResource(id = R.string.cancel),
                onClick = { onEvent(WeightDialogEvent.ClickedBack) }
            ),
            onDismissRequest = { onEvent(WeightDialogEvent.ClickedBack) }
        )
    }
}