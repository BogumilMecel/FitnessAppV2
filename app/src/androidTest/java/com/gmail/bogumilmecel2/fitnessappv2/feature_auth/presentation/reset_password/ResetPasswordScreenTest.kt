package com.gmail.bogumilmecel2.fitnessappv2.feature_auth.presentation.reset_password

import androidx.compose.ui.test.*
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.BaseAndroidTest
import com.gmail.bogumilmecel2.fitnessappv2.common.di.AppModule
import com.gmail.bogumilmecel2.fitnessappv2.common.util.TestTags
import com.gmail.bogumilmecel2.fitnessappv2.destinations.ResetPasswordScreenDestination
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
internal class ResetPasswordScreenTest: BaseAndroidTest() {
    @Before
    fun setUp() {
        setContent(destination = ResetPasswordScreenDestination)
    }

    @Test
    fun enteredEmail_TextIsDisplayed(){
        val text = "abc"
        val loginEmail = composeRule.onNodeWithTag(TestTags.General.EMAIL)
        loginEmail.assertIsDisplayed().performTextInput(text)
        loginEmail.assert(
            hasText(text)
        )
    }

    @Test
    fun emptyFields_SnackbarVisible(){
        composeRule.onNodeWithTag(TestTags.General.EMAIL).performTextClearance()
        composeRule.onNodeWithTag(TestTags.General.PRIMARY_BUTTON).performClick()
        composeRule.onNodeWithText(composeRule.activity.getString(R.string.please_enter_your_email_in_order_to_reset_your_password)).assertIsDisplayed()
    }
}