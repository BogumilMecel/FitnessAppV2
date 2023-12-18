package com.gmail.bodziowaty6978.fitnessappv2.common.util

import androidx.lifecycle.ViewModel

open class BaseViewModel: ViewModel() {

    suspend fun showSnackbarError(message: String) {
        ErrorUtils.showSnackbarWithMessage(message = message)
    }
}