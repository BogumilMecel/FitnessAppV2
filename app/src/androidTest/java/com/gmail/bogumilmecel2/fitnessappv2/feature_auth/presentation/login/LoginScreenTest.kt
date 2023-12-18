package com.gmail.bogumilmecel2.fitnessappv2.feature_auth.presentation.login

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.BaseAndroidTest
import com.gmail.bogumilmecel2.fitnessappv2.common.util.TestTags
import com.gmail.bogumilmecel2.fitnessappv2.destinations.LoginScreenDestination
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.MockKAnnotations
import org.junit.Before
import org.junit.Test

@HiltAndroidTest
internal class LoginScreenTest : BaseAndroidTest() {

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        setContent(
            destination = LoginScreenDestination,
            screenTestTag = TestTags.Login.LOGIN_SCREEN
        )
    }

    @Test
    fun checkIfEmailIsCorrectlyEntered() {
        performTextInputAndAssertIsDisplayed(
            testTag = TestTags.General.EMAIL,
            text = "abc"
        )
    }

    @Test
    fun checkIfPasswordIsCorrectlyEntered() {
        performTextInputAndAssertIsDisplayed(
            testTag = TestTags.General.PASSWORD,
            text = "abc",
            expectedTest = "•••"
        )
    }

    @Test
    fun checkIfSnackbarIsVisibleWhenAllFieldsAreEmpty() {
        performButtonClickAndAssertSnackbarDisplayed(
            buttonTestTag = TestTags.General.PRIMARY_BUTTON,
            snackbarText = getString(R.string.please_make_sure_all_fields_are_filled_in_correctly)
        )
    }

    @Test
    fun checkIfSnackbarIsVisibleWhenEmailIsNotValid() {
        checkIfEmailIsCorrectlyEntered()
        checkIfPasswordIsCorrectlyEntered()

        performButtonClickAndAssertSnackbarDisplayed(
            buttonTestTag = TestTags.General.PRIMARY_BUTTON,
            snackbarText = getString(R.string.please_make_sure_you_have_entered_correct_email)
        )
    }

    @Test
    fun checkIfUserIsNavigatedToResetPasswordScreenAfterForgotPasswordClicked() {
        composeRule.onNodeWithTag(TestTags.Login.FORGOT_PASSWORD).performClick()
        composeRule.onNodeWithTag(TestTags.ResetPassword.RESET_PASSWORD_SCREEN).assertIsDisplayed()
    }

    @Test
    fun checkIfUserIsNavigatedToRegisterScreenAfterRegisterClicked() {
        composeRule.onNodeWithTag(TestTags.Login.REGISTER_BUTTON).performClick()
        composeRule.onNodeWithTag(TestTags.RegisterScreen.REGISTER_SCREEN).assertIsDisplayed()
    }

    @Test
    fun checkIfLoaderIsVisibleWhenLoginButtonClicked() {
        performTextInputAndAssertIsDisplayed(
            testTag = TestTags.General.EMAIL,
            text = "abc@abc.com"
        )
        performTextInputAndAssertIsDisplayed(
            testTag = TestTags.General.PASSWORD,
            text = "abc",
            expectedTest = "•••"
        )
        composeRule.onNodeWithTag(TestTags.General.PRIMARY_BUTTON).performClick()
        composeRule.onNodeWithTag(TestTags.General.LOADER).assertIsDisplayed()
    }
}