package com.gmail.bogumilmecel2.fitnessappv2.common.domain.navigation

import kotlinx.coroutines.channels.Channel


interface Navigator {
    val navActions: Channel<NavigationAction?>
    suspend fun navigate(navAction: NavigationAction?)
}