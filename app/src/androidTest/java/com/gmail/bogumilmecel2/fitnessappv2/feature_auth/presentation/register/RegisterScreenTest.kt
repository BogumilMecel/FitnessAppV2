package com.gmail.bogumilmecel2.fitnessappv2.feature_auth.presentation.register

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
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
        val email = composeRule.onNodeWithTag(TestTags.EMAIL)
        email.assertIsDisplayed().performTextInput(text)
        email.assert(
            hasText(text)
        )
    }

    @Test
    fun enteredUsername_TextIsDisplayed(){
        val text = "username"
        val username = composeRule.onNodeWithTag(TestTags.USERNAME)
        username.assertIsDisplayed().performTextInput(text)
        username.assert(
            hasText(text)
        )
    }

    @Test
    fun enteredPassword_TextIsDisplayed(){
        val text = "password"
        val password = composeRule.onNodeWithTag(TestTags.PASSWORD)
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
        val password = composeRule.onNodeWithTag(TestTags.CONFIRM_PASSWORD)
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
        composeRule.onNodeWithTag(TestTags.PASSWORD).performTextClearance()
        composeRule.onNodeWithTag(TestTags.EMAIL).performTextClearance()
        composeRule.onNodeWithTag(TestTags.USERNAME).performTextClearance()
        composeRule.onNodeWithTag(TestTags.CONFIRM_PASSWORD).performTextClearance()
        composeRule.onNodeWithTag(TestTags.PRIMARY_BUTTON).performClick()
        composeRule.onNodeWithText(composeRule.activity.getString(R.string.please_make_sure_all_fields_are_filled_in_correctly)).assertIsDisplayed()
    }

}