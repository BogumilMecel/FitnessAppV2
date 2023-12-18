package com.gmail.bogumilmecel2.fitnessappv2.feature_auth.presentation.login

sealed interface LoginEvent {
    data class EnteredEmail(val email:String): LoginEvent
    data class EnteredPassword(val password:String): LoginEvent
    data object LoginButtonClicked: LoginEvent
    data object RegisterLoginButtonClicked: LoginEvent
    data object ForgotButtonClicked: LoginEvent
}