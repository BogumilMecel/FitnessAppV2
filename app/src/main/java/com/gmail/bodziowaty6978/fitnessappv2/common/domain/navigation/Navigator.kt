package com.gmail.bodziowaty6978.fitnessappv2.common.domain.navigation

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.navigation.NavigationAction
import kotlinx.coroutines.flow.StateFlow

interface Navigator {
    val navActions: StateFlow<NavigationAction?>
    fun navigate(navAction: NavigationAction?)
}