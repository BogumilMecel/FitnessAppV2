package com.gmail.bogumilmecel2.fitnessappv2.common.domain.navigation

import androidx.navigation.NavOptions
import com.ramcosta.composedestinations.spec.Direction

data class NavigationAction(
    val direction: Direction,
    val navOptions: NavOptions
)
