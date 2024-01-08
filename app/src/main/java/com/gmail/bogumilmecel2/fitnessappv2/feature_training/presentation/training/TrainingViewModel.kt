package com.gmail.bogumilmecel2.fitnessappv2.feature_training.presentation.training

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case.FormatLocalDateUseCase
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseViewModel
import com.gmail.bogumilmecel2.fitnessappv2.common.util.CustomDateUtils
import com.gmail.bogumilmecel2.fitnessappv2.destinations.SelectExerciseScreenDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import javax.inject.Inject

@HiltViewModel
class TrainingViewModel @Inject constructor(
    private val formatLocalDateUseCase: FormatLocalDateUseCase
): BaseViewModel<TrainingState, TrainingEvent, Unit>(
    state = TrainingState(),
    navArguments = Unit
) {

    private var currentDate = CustomDateUtils.getDate()

    override fun configureOnStart() {
        assignCurrentDate()
    }

    override fun onEvent(event: TrainingEvent) {
        when(event) {
            is TrainingEvent.ClickedCalendarArrowForward -> {
                currentDate = currentDate.plus(1, DateTimeUnit.DAY)
                assignCurrentDate()
            }

            is TrainingEvent.ClickedCalendarArrowBackwards -> {
                currentDate = currentDate.minus(1, DateTimeUnit.DAY)
                assignCurrentDate()
            }

            is TrainingEvent.ClickedAddButton -> {
                navigateTo(destination = SelectExerciseScreenDestination)
            }

            is TrainingEvent.ClickedCopyButton -> {
            }
        }
    }

    private fun assignCurrentDate() {
        _state.update {
            it.copy(currentDate = formatLocalDateUseCase(localDate = currentDate))
        }
    }
}