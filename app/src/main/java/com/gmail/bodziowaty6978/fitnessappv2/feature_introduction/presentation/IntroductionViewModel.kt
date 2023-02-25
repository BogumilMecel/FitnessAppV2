package com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.gmail.bodziowaty6978.fitnessappv2.common.util.BaseViewModel
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.use_cases.SaveIntroductionInformation
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.presentation.util.IntroductionExpectedQuestionAnswer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroductionViewModel @Inject constructor(
    resourceProvider: ResourceProvider,
    private val saveIntroductionInformation: SaveIntroductionInformation,
) : BaseViewModel() {

    private val _state = MutableStateFlow(IntroductionState())
    val state: StateFlow<IntroductionState> = _state

    private val _introductionUiEvent = MutableSharedFlow<IntroductionUiEvent>()
    val introductionUiEvent: SharedFlow<IntroductionUiEvent> = _introductionUiEvent

    private val _introductionAnswerState = mutableStateOf(
        mapOf(
            IntroductionExpectedQuestionAnswer.Gender to "0",
            IntroductionExpectedQuestionAnswer.Age to "18",
            IntroductionExpectedQuestionAnswer.Height to "180.0",
            IntroductionExpectedQuestionAnswer.CurrentWeight to "80.0",
            IntroductionExpectedQuestionAnswer.TypeOfWork to "0",
            IntroductionExpectedQuestionAnswer.HowOftenDoYouTrain to "0",
            IntroductionExpectedQuestionAnswer.TypeOfWork to "0",
            IntroductionExpectedQuestionAnswer.DesiredWeight to "80.0",
            IntroductionExpectedQuestionAnswer.ActivityDuringADay to "0"
        )
    )
    val introductionAnswerState: State<Map<IntroductionExpectedQuestionAnswer, String>> =
        _introductionAnswerState


    fun onEvent(event: IntroductionEvent) {
        when (event) {
            is IntroductionEvent.ClickedArrow -> {
                viewModelScope.launch {
                    if (event.isForward) {
                        _introductionUiEvent.emit(
                            IntroductionUiEvent.MoveForward
                        )
                    } else {
                        _introductionUiEvent.emit(
                            IntroductionUiEvent.MoveBackward
                        )
                    }
                }
            }

            is IntroductionEvent.EnteredAnswer -> {
                val currentAnswerState = _introductionAnswerState.value.toMutableMap()
                currentAnswerState[event.expectedQuestionAnswer] = event.answer
                _introductionAnswerState.value = currentAnswerState
            }

            is IntroductionEvent.FinishIntroduction -> {
//                viewModelScope.launch(Dispatchers.IO) {
//                    saveIntroductionInformation(answers = _introductionAnswerState.value).handle {
//                        navigateWithPopUp(
//                            destination = SummaryScreenDestination
//                        )
//                    }
//                }
            }
        }
    }
}