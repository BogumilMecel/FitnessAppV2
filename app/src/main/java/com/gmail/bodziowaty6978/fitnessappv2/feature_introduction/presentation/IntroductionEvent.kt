package com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.presentation

import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.presentation.util.IntroductionExpectedQuestionAnswer

sealed interface IntroductionEvent {
    object ClickedArrowForward: IntroductionEvent
    object ClickedArrowBackwards: IntroductionEvent
    data class EnteredAnswer(val expectedQuestionAnswer: IntroductionExpectedQuestionAnswer, val answer:String): IntroductionEvent
    object FinishIntroduction: IntroductionEvent
}