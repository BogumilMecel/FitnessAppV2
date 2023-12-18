package com.gmail.bogumilmecel2.fitnessappv2.common

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.MainActivity
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.navigation.NavHostGraph
import com.gmail.bogumilmecel2.fitnessappv2.common.util.TestTags
import com.gmail.bogumilmecel2.fitnessappv2.destinations.Destination
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule

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

    private lateinit var navController: TestNavHostController

    fun setContent(
        destination: Destination,
        screenTestTag: String? = null
    ) {
        composeRule.run {
            activity.setContent {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                FitnessAppTheme {
                    NavHostGraph(
                        startDestination = destination,
                        navController = navController
                    )
                }
            }

            screenTestTag?.let {
                onNodeWithTag(screenTestTag).assertIsDisplayed()
            }
        }
    }

    fun performTextInputAndAssertIsDisplayed(
        testTag: String,
        text: String,
        expectedTest: String? = null
    ) {
        composeRule.onNodeWithTag(testTag).run {
            assertIsDisplayed()
            performTextInput(text)
            assertHasText(expectedTest ?: text)
        }
    }

    fun performButtonClickAndAssertSnackbarDisplayed(
        buttonTestTag: String,
        snackbarText: String? = null
    ) {
        composeRule.run {
            onNodeWithTag(buttonTestTag).performClick()
            onNodeWithTag(TestTags.General.SNACKBAR).assertIsDisplayed()

            snackbarText?.let {
                onNodeWithText(it).assertIsDisplayed()
            }
        }
    }

    private fun SemanticsNodeInteraction.assertHasText(text: String) = assert(hasText(text = text))
}