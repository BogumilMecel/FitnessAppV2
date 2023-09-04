package com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.presentation

import androidx.lifecycle.viewModelScope
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseViewModel
import com.gmail.bogumilmecel2.fitnessappv2.destinations.SummaryScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.ActivityLevel
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.DesiredWeight
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.Gender
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.QuestionName
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.TrainingFrequency
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.TypeOfWork
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.use_cases.SaveIntroductionInformationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroductionViewModel @Inject constructor(
    private val saveIntroductionInformationUseCase: SaveIntroductionInformationUseCase,
) : BaseViewModel<IntroductionState, IntroductionEvent, Unit>(
    state = IntroductionState(),
    navArguments = Unit
) {

    private val _introductionUiEvent = MutableSharedFlow<IntroductionUiEvent>()
    val introductionUiEvent: SharedFlow<IntroductionUiEvent> = _introductionUiEvent

    override fun onEvent(event: IntroductionEvent) {
        when (event) {
            is IntroductionEvent.ClickedArrowForward -> {
                viewModelScope.launch {
                    _introductionUiEvent.emit(IntroductionUiEvent.MoveForward)
                }
            }

            is IntroductionEvent.ClickedArrowBackwards -> {
                viewModelScope.launch {
                    _introductionUiEvent.emit(IntroductionUiEvent.MoveBackward)
                }
            }

            is IntroductionEvent.ClickedTile -> {
                when (event.tile) {
                    is Gender -> _state.update {
                        it.copy(selectedGender = event.tile)
                    }

                    is ActivityLevel -> _state.update {
                        it.copy(activityLevel = event.tile)
                    }

                    is TrainingFrequency -> _state.update {
                        it.copy(trainingFrequency = event.tile)
                    }

                    is TypeOfWork -> _state.update {
                        it.copy(typeOfWork = event.tile)
                    }

                    is DesiredWeight -> _state.update {
                        it.copy(desiredWeight = event.tile)
                    }
                }
            }

            is IntroductionEvent.EnteredAnswer -> {
                when (event.questionName) {
                    QuestionName.AGE -> _state.update {
                        it.copy(age = event.newValue)
                    }

                    QuestionName.HEIGHT -> _state.update {
                        it.copy(height = event.newValue)
                    }

                    QuestionName.CURRENT_WEIGHT -> _state.update {
                        it.copy(currentWeight = event.newValue)
                    }

                    else -> {}
                }
            }

            is IntroductionEvent.FinishIntroduction -> {
                viewModelScope.launch(Dispatchers.IO) {
                    with(_state.value) {
                        saveIntroductionInformationUseCase(
                            selectedGender = selectedGender,
                            age = age,
                            height = height,
                            weight = currentWeight,
                            typeOfWork = typeOfWork,
                            activityLevel = activityLevel,
                            trainingFrequency = trainingFrequency,
                            desiredWeight = desiredWeight,
                            cachedValuesProvider = cachedValuesProvider
                        ).handle {
                            navigateWithPopUp(
                                destination = SummaryScreenDestination
                            )
                        }
                    }
                }
            }
        }
    }
}