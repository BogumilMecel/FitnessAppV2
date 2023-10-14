package com.gmail.bogumilmecel2.fitnessappv2.feature_auth.presentation.register

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.gmail.bogumilmecel2.fitnessappv2.AndroidMockConstants
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
        setContent(
            destination = RegisterScreenDestination,
            screenTestTag = TestTags.REGISTER_SCREEN
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
    fun checkIfUsernameIsCorrectlyEntered() {
        performTextInputAndAssertHasText(
            testTag = TestTags.USERNAME,
            text = AndroidMockConstants.CORRECT_USERNAME
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
    fun checkIfConfirmPasswordIsCorrectlyEntered() {
        performTextInputAndAssertHasText(
            testTag = TestTags.CONFIRM_PASSWORD,
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
        enterFieldValues(email = AndroidMockConstants.INCORRECT_EMAIL)

        performButtonClickAndAssertSnackbarDisplayed(
            buttonTestTag = TestTags.PRIMARY_BUTTON,
            snackbarText = getString(R.string.please_make_sure_you_have_entered_correct_email)
        )
    }

    @Test
    fun checkIfSnackbarIsVisibleWhenPasswordsAreNotMatching() {
        enterFieldValues(confirmPassword = AndroidMockConstants.CORRECT_PASSWORD_2)

        performButtonClickAndAssertSnackbarDisplayed(
            buttonTestTag = TestTags.PRIMARY_BUTTON,
            snackbarText = getString(R.string.please_make_sure_both_passwords_are_the_same)
        )
    }

    @Test
    fun checkIfSnackbarIsVisibleShorterThan6Characters() {
        enterFieldValues(
            password = AndroidMockConstants.TOO_SHORT_PASSWORD,
            confirmPassword = AndroidMockConstants.TOO_SHORT_PASSWORD
        )

        performButtonClickAndAssertSnackbarDisplayed(
            buttonTestTag = TestTags.PRIMARY_BUTTON,
            snackbarText = getString(R.string.please_make_sure_your_password_is_at_least_6_characters_and_is_not_longer_than_24_characters)
        )
    }

    @Test
    fun checkIfSnackbarIsVisibleLongerThan24Characters() {
        enterFieldValues(
            password = AndroidMockConstants.TOO_LONG_PASSWORD,
            confirmPassword = AndroidMockConstants.TOO_LONG_PASSWORD
        )

        performButtonClickAndAssertSnackbarDisplayed(
            buttonTestTag = TestTags.PRIMARY_BUTTON,
            snackbarText = getString(R.string.please_make_sure_your_password_is_at_least_6_characters_and_is_not_longer_than_24_characters)
        )
    }

    @Test
    fun checkIfUserIsNavigatedToResetPasswordScreenAfterForgotPasswordClicked() {
        performButtonClickAndAssertNewScreenIsOpened(
            buttonTestTag = TestTags.LOGIN_BUTTON,
            newScreenTestTag = TestTags.LOGIN_SCREEN
        )
    }

    @Test
    fun checkIfLoaderIsVisibleWhenLoginButtonClicked() {
        enterFieldValues()
        composeRule.onNodeWithTag(TestTags.PRIMARY_BUTTON).performClick()
        composeRule.onNodeWithTag(TestTags.LOADER).assertIsDisplayed()
    }

    private fun enterFieldValues(
        email: String = AndroidMockConstants.CORRECT_EMAIL,
        password: String = AndroidMockConstants.CORRECT_PASSWORD,
        confirmPassword: String = AndroidMockConstants.CORRECT_PASSWORD,
        username: String = AndroidMockConstants.CORRECT_USERNAME
    ) {
        performTextInput(
            testTag = TestTags.EMAIL,
            text = email
        )

        performTextInput(
            testTag = TestTags.PASSWORD,
            text = password
        )

        performTextInput(
            testTag = TestTags.CONFIRM_PASSWORD,
            text = confirmPassword
        )

        performTextInput(
            testTag = TestTags.USERNAME,
            text = username
        )
    }
}