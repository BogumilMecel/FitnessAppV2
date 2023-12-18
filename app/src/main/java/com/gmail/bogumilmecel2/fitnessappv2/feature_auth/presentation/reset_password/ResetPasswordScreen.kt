package com.gmail.bogumilmecel2.fitnessappv2.feature_auth.presentation.reset_password

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.components.Toolbar
import com.gmail.bogumilmecel2.fitnessappv2.common.util.TestTags
import com.gmail.bogumilmecel2.fitnessappv2.common.util.ViewModelLayout
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.presentation.util.AuthEvent
import com.gmail.bogumilmecel2.ui.components.base.CustomBasicTextField
import com.gmail.bogumilmecel2.ui.components.base.CustomButton
import com.gmail.bogumilmecel2.ui.components.base.IconVector
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun ResetPasswordScreen(navigator: DestinationsNavigator) {
    hiltViewModel<ResetPasswordViewModel>().ViewModelLayout(
        navigator = navigator,
        screenTestTag = TestTags.ResetPassword.RESET_PASSWORD_SCREEN
    ) { viewModel, state ->
        Scaffold(
            topBar = {
                Toolbar(
                    title = stringResource(id = R.string.reset_your_password),
                    isBackArrowVisible = true,
                ) {
                    viewModel.onEvent(AuthEvent.RegisterLoginButtonClicked)
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues.calculateTopPadding())
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                ) {
                    CustomBasicTextField(
                        value = state.email,
                        placeholder = stringResource(id = R.string.email_address),
                        onValueChange = {
                            viewModel.onEvent(AuthEvent.EnteredEmail(email = it))
                        },
                        keyboardOptions = KeyboardOptions().copy(
                            keyboardType = KeyboardType.Email
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        leadingIcon = IconVector.Email,
                        testTag = TestTags.General.EMAIL
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    CustomButton(
                        onClick = {
                            viewModel.onEvent(AuthEvent.AuthButtonClicked)
                        },
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth()
                            .testTag(TestTags.General.PRIMARY_BUTTON),
                        text = stringResource(id = R.string.reset_my_password)
                    )
                }
            }
        }
    }
}