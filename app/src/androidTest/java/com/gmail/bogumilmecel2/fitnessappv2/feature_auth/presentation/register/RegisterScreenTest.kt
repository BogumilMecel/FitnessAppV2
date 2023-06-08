package com.gmail.bogumilmecel2.fitnessappv2.feature_auth.presentation.register

import androidx.compose.ui.test.*
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.BaseAndroidTest
import com.gmail.bogumilmecel2.fitnessappv2.common.di.AppModule
import com.gmail.bogumilmecel2.fitnessappv2.common.util.TestTags
import com.gmail.bogumilmecel2.fitnessappv2.destinations.RegisterScreenDestination
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
internal class RegisterScreenTest: BaseAndroidTest() {
    @Before
    fun setUp() {
        setContent(destination = RegisterScreenDestination)
    }

    @Test
    fun enteredEmail_TextIsDisplayed(){
        val text = "email"
        val email = composeRule.onNodeWithTag(TestTags.General.EMAIL)
        email.assertIsDisplayed().performTextInput(text)
        email.assert(
            hasText(text)
        )
    }

    @Test
    fun enteredUsername_TextIsDisplayed(){
        val text = "username"
        val username = composeRule.onNodeWithTag(TestTags.RegisterScreen.USERNAME)
        username.assertIsDisplayed().performTextInput(text)
        username.assert(
            hasText(text)
        )
    }

    @Test
    fun enteredPassword_TextIsDisplayed(){
        val text = "password"
        val password = composeRule.onNodeWithTag(TestTags.General.PASSWORD)
        password.assertIsDisplayed().performTextInput(text)
        val stringBuilder = StringBuilder()
        for (c in text.indices){
            stringBuilder.append("•")
        }
        password.assert(
            hasText(stringBuilder.toString())
        )
    }

    @Test
    fun enteredConfirmPassword_TextIsDisplayed(){
        val text = "confirm"
        val password = composeRule.onNodeWithTag(TestTags.RegisterScreen.CONFIRM_PASSWORD)
        password.assertIsDisplayed().performTextInput(text)
        val stringBuilder = StringBuilder()
        for (c in text.indices){
            stringBuilder.append("•")
        }
        password.assert(
            hasText(stringBuilder.toString())
        )
    }

    @Test
    fun emptyFields_SnackbarVisible(){
        composeRule.onNodeWithTag(TestTags.General.PASSWORD).performTextClearance()
        composeRule.onNodeWithTag(TestTags.General.EMAIL).performTextClearance()
        composeRule.onNodeWithTag(TestTags.RegisterScreen.USERNAME).performTextClearance()
        composeRule.onNodeWithTag(TestTags.RegisterScreen.CONFIRM_PASSWORD).performTextClearance()
        composeRule.onNodeWithTag(TestTags.General.PRIMARY_BUTTON).performClick()
        composeRule.onNodeWithText(composeRule.activity.getString(R.string.please_make_sure_all_fields_are_filled_in_correctly)).assertIsDisplayed()
    }

}