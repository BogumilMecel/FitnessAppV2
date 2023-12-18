package com.gmail.bogumilmecel2.fitnessappv2.common

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.navigation.Navigator
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.MainActivity
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.navigation.NavHostGraph
import com.gmail.bogumilmecel2.fitnessappv2.destinations.Destination
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

@HiltAndroidTest
open class BaseAndroidTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setupDaggerHilt() {
        hiltRule.inject()
    }

    @Inject
    lateinit var composeNavigator: Navigator

    private lateinit var navController: TestNavHostController

    fun setContent(destination: Destination) {
        composeRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            FitnessAppTheme {
                NavHostGraph(
                    navigator = composeNavigator,
                    startDestination = destination,
                    navController = navController
                )
            }
        }
    }

    fun SemanticsNodeInteraction.assertHasText(text: String) = assert(hasText(text = text))
}