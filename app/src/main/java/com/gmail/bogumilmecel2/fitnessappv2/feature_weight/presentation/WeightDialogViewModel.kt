package com.gmail.bogumilmecel2.fitnessappv2.feature_weight.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseViewModel
import com.gmail.bogumilmecel2.fitnessappv2.destinations.WeightDialogScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.use_case.AddWeightEntryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeightDialogViewModel @Inject constructor(
    private val addWeightEntryUseCase: AddWeightEntryUseCase,
    savedStateHandle: SavedStateHandle
): BaseViewModel<WeightDialogState, WeightDialogEvent, WeightDialogNavArguments>(
    navArguments = WeightDialogScreenDestination.argsFrom(savedStateHandle),
    state = WeightDialogState(
        currentWeight = WeightDialogScreenDestination.argsFrom(savedStateHandle).startingValue
    )
) {

    private var currentValue: Double = navArguments.startingValue

    override fun onEvent(event: WeightDialogEvent) {
        when(event) {
            is WeightDialogEvent.EnteredWeight -> {
                currentValue = event.value
                _state.update {
                    it.copy(currentWeight = currentValue)
                }
            }

            is
            WeightDialogEvent.ClickedBack,
            WeightDialogEvent.ClickedCancel
            -> navigateUp()

            is WeightDialogEvent.ClickedSave -> {
                saveNewWeightEntry()
            }
        }
    }

    private fun saveNewWeightEntry() {
        viewModelScope.launch {
            loaderVisible = true
            addWeightEntryUseCase(value = currentValue).handle(
                finally = { loaderVisible = false }
            ) {
                navigateUp()
            }
        }
    }
}