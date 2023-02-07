package com.gmail.bodziowaty6978.fitnessappv2.common.domain.navigation

import kotlinx.coroutines.channels.Channel


interface Navigator {
    val navActions: Channel<NavigationAction?>
    suspend fun navigate(navAction: NavigationAction?)
}