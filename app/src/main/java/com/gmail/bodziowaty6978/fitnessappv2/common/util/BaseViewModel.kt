package com.gmail.bodziowaty6978.fitnessappv2.common.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import com.gmail.bodziowaty6978.fitnessappv2.common.data.utils.CustomSharedPreferencesUtils
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.navigation.NavigationAction
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.navigation.Navigator
import com.ramcosta.composedestinations.spec.Direction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

    fun navigateTo(destination: Direction, navOptions: NavOptions = NavOptions.Builder().build()) =
        viewModelScope.launch {
            navigator.navigate(
                NavigationAction(direction = destination, navOptions = navOptions)
            )
        }

    fun navigateUp() {
        navigateTo(
            destination = object : Direction {
                override val route: String = "navigate_up"
            }
        )
    }
}