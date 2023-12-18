package com.gmail.bodziowaty6978.fitnessappv2.common.util

import androidx.lifecycle.ViewModel
import com.gmail.bodziowaty6978.fitnessappv2.common.data.navigation.NavigationActions
import com.gmail.bodziowaty6978.fitnessappv2.common.data.utils.CustomSharedPreferencesUtils
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.navigation.NavigationAction
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(): ViewModel() {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var sharedPreferencesUtils: CustomSharedPreferencesUtils

    @Inject
    lateinit var resourceProvider: ResourceProvider

    suspend fun showSnackbarError(message: String) {
        ErrorUtils.showSnackbarWithMessage(message = message)
    }

    fun navigate(navigationAction: NavigationAction) = navigator.navigate(navigationAction)
    fun navigateBack() = navigate(NavigationActions.General.navigateUp())
}