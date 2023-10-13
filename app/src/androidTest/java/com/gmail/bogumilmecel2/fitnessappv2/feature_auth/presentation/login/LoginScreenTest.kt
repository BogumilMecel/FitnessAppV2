package com.gmail.bogumilmecel2.fitnessappv2.feature_auth.presentation.login

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.gmail.bogumilmecel2.fitnessappv2.AndroidMockConstants
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.BaseAndroidTest
import com.gmail.bogumilmecel2.fitnessappv2.common.util.TestTags
import com.gmail.bogumilmecel2.fitnessappv2.destinations.LoginScreenDestination
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Test

@HiltAndroidTest
internal class LoginScreenTest : BaseAndroidTest() {

    @Before
    fun setUp() {
        setContent(
            destination = LoginScreenDestination,
            screenTestTag = TestTags.LOGIN_SCREEN
        )
    }

    @Test
    fun checkIfEmailIsCorrectlyEntered() {
        performTextInputAndAssertHasText(
            testTag = TestTags.EMAIL,
            text = AndroidMockConstants.CORRECT_EMAIL
        )
    }

    @Test
    fun checkIfPasswordIsCorrectlyEntered() {
        performTextInputAndAssertHasText(
            testTag = TestTags.PASSWORD,
            text = AndroidMockConstants.CORRECT_PASSWORD,
            expectedTest = AndroidMockConstants.CORRECT_PASSWORD_DOTTED
        )
    }

    @Test
    fun checkIfSnackbarIsVisibleWhenAllFieldsAreEmpty() {
        performButtonClickAndAssertSnackbarDisplayed(
            buttonTestTag = TestTags.PRIMARY_BUTTON,
            snackbarText = getString(R.string.please_make_sure_all_fields_are_filled_in_correctly)
        )
    }

    @Test
    fun checkIfSnackbarIsVisibleWhenEmailIsNotValid() {
        enterEmail(email = AndroidMockConstants.INCORRECT_EMAIL)
        enterCorrectPassword()

        performButtonClickAndAssertSnackbarDisplayed(
            buttonTestTag = TestTags.PRIMARY_BUTTON,
            snackbarText = getString(R.string.please_make_sure_you_have_entered_correct_email)
        )
    }

    @Test
    fun checkIfUserIsNavigatedToResetPasswordScreenAfterForgotPasswordClicked() {
        performButtonClickAndAssertNewScreenIsOpened(
            buttonTestTag = TestTags.FORGOT_PASSWORD,
            newScreenTestTag = TestTags.RESET_PASSWORD_SCREEN
        )
    }

    @Test
    fun checkIfUserIsNavigatedToRegisterScreenAfterRegisterClicked() {
        performButtonClickAndAssertNewScreenIsOpened(
            buttonTestTag = TestTags.REGISTER_BUTTON,
            newScreenTestTag = TestTags.REGISTER_SCREEN
        )
    }

    @Test
    fun checkIfLoaderIsVisibleWhenLoginButtonClicked() {
        enterEmail()
        enterCorrectPassword()
        composeRule.onNodeWithTag(TestTags.PRIMARY_BUTTON).performClick()
        composeRule.onNodeWithTag(TestTags.LOADER).assertIsDisplayed()
    }

    private fun enterEmail(email: String = AndroidMockConstants.CORRECT_EMAIL) {
        performTextInput(
            testTag = TestTags.EMAIL,
            text = email
        )
    }

    private fun enterCorrectPassword() {
        performTextInput(
            testTag = TestTags.PASSWORD,
            text = AndroidMockConstants.CORRECT_PASSWORD
        )
    }
}