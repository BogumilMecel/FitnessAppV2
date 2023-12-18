package com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.presentation

import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.QuestionName
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.Tile

sealed interface IntroductionEvent {
    object ClickedArrowForward : IntroductionEvent
    object ClickedArrowBackwards : IntroductionEvent
    data class EnteredAnswer(val questionName: QuestionName, val newValue: String): IntroductionEvent
    data class ClickedTile(val tile: Tile): IntroductionEvent
    object FinishIntroduction : IntroductionEvent
}