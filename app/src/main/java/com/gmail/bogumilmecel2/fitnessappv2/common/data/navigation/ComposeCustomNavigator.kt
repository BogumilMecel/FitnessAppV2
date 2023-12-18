package com.gmail.bogumilmecel2.fitnessappv2.common.data.navigation

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.navigation.NavigationAction
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.navigation.Navigator
import kotlinx.coroutines.channels.Channel

class ComposeCustomNavigator: Navigator {

    private val _navActions = Channel<NavigationAction?>()

    override val navActions: Channel<NavigationAction?> = _navActions

    override suspend fun navigate(navAction: NavigationAction?) {
        _navActions.send(navAction)
    }
}