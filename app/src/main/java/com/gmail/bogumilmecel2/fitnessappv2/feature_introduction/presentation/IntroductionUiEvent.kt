package com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.presentation

sealed class IntroductionUiEvent{
    object MoveForward: IntroductionUiEvent()
    object MoveBackward: IntroductionUiEvent()
}
