package com.gmail.bogumilmecel2.fitnessappv2

import com.gmail.bogumilmecel2.util.Constants

object AndroidMockConstants {
    const val INCORRECT_EMAIL = "email"
    const val CORRECT_EMAIL = "email@email.com"
    const val CORRECT_USERNAME = "username"
    const val CORRECT_PASSWORD = "123456"
    const val CORRECT_PASSWORD_2 = "654321"
    const val TOO_SHORT_PASSWORD = "12345"
    const val CORRECT_PASSWORD_DOTTED = "••••••"

    fun getInvalidLengthPassword() = StringBuilder().run {
        (0..(Constants.Authentication.PASSWORD_MAX_LENGTH + 1)).map {
            append("1")
        }
    }.toString()
}