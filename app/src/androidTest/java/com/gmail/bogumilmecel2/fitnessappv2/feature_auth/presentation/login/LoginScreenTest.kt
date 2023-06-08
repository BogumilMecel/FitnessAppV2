package com.gmail.bogumilmecel2.fitnessappv2.feature_auth.presentation.login

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
import com.gmail.bogumilmecel2.fitnessappv2.common.util.TestTags
import com.gmail.bogumilmecel2.fitnessappv2.destinations.LoginScreenDestination
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Test

@HiltAndroidTest
internal class LoginScreenTest: BaseAndroidTest() {

    @Before
    fun setUp() {
        setContent(destination = LoginScreenDestination)
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
    fun enteredPassword_TextIsDisplayed(){
        val text = "abc"
        val loginPassword = composeRule.onNodeWithTag(TestTags.General.PASSWORD)
        loginPassword.assertIsDisplayed().performTextInput(text)
        val stringBuilder = StringBuilder()
        for (c in text.indices){
            stringBuilder.append("•")
        }
        loginPassword.assert(
            hasText(stringBuilder.toString())
        )
    }

    @Test
    fun emptyFields_SnackbarVisible(){
        composeRule.onNodeWithTag(TestTags.General.PASSWORD).performTextClearance()
        composeRule.onNodeWithTag(TestTags.General.EMAIL).performTextClearance()
        composeRule.onNodeWithTag(TestTags.General.PRIMARY_BUTTON).performClick()
        composeRule.onNodeWithText(composeRule.activity.getString(R.string.please_make_sure_all_fields_are_filled_in_correctly)).assertIsDisplayed()
    }
}