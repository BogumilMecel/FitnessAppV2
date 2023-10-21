package com.gmail.bogumilmecel2.fitnessappv2.feature_auth.presentation.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.util.TestTags
import com.gmail.bogumilmecel2.fitnessappv2.common.util.ViewModelLayout
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.presentation.util.AuthEvent
import com.gmail.bogumilmecel2.ui.components.base.CustomBasicTextField
import com.gmail.bogumilmecel2.ui.components.base.CustomButton
import com.gmail.bogumilmecel2.ui.components.base.IconVector
import com.gmail.bogumilmecel2.ui.components.complex.HeaderRow
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun RegisterScreen(navigator: DestinationsNavigator) {
    hiltViewModel<RegisterViewModel>().ViewModelLayout(
        navigator = navigator,
        screenTestTag = TestTags.REGISTER_SCREEN
    ) { viewModel, state ->
        Box(modifier = Modifier.fillMaxSize()) {
            HeaderRow(middlePrimaryText = stringResource(id = R.string.register))

            Column(modifier = Modifier.align(Alignment.Center)) {
                CustomBasicTextField(
                    value = state.email,
                    placeholder = stringResource(id = R.string.email_address),
                    onValueChange = {
                        viewModel.onEvent(
                            AuthEvent.EnteredEmail(email = it)
                        )
                    },
                    keyboardOptions = KeyboardOptions().copy(
                        keyboardType = KeyboardType.Email
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    leadingIcon = IconVector.Email,
                    maxLines = 2,
                    testTag = TestTags.EMAIL
                )

                Spacer(modifier = Modifier.height(15.dp))

                CustomBasicTextField(
                    value = state.username,
                    placeholder = stringResource(id = R.string.username),
                    onValueChange = {
                        viewModel.onEvent(
                            AuthEvent.EnteredUsername(username = it)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    leadingIcon = IconVector.Account,
                    maxLines = 2,
                    testTag = TestTags.USERNAME
                )

                Spacer(modifier = Modifier.height(15.dp))

                CustomBasicTextField(
                    value = state.password,
                    placeholder = stringResource(id = R.string.password),
                    onValueChange = {
                        viewModel.onEvent(
                            AuthEvent.EnteredPassword(password = it)
                        )
                    },
                    keyboardOptions = KeyboardOptions().copy(
                        keyboardType = KeyboardType.Password,
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    leadingIcon = IconVector.Password,
                    maxLines = 2,
                    testTag = TestTags.PASSWORD
                )

                Spacer(modifier = Modifier.height(15.dp))

                CustomBasicTextField(
                    value = state.confirmPassword,
                    placeholder = stringResource(id = R.string.confirm_your_password),
                    onValueChange = {
                        viewModel.onEvent(
                            AuthEvent.EnteredConfirmPassword(confirmPassword = it)
                        )
                    },
                    keyboardOptions = KeyboardOptions().copy(
                        keyboardType = KeyboardType.Password,
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    leadingIcon = IconVector.Password,
                    maxLines = 2,
                    testTag = TestTags.CONFIRM_PASSWORD
                )

                Spacer(modifier = Modifier.height(40.dp))

                CustomButton(
                    onClick = {
                        viewModel.onEvent(AuthEvent.AuthButtonClicked)
                    },
                    modifier = Modifier
                        .padding(
                            top = 20.dp,
                            end = 20.dp,
                            start = 20.dp,
                            bottom = 100.dp
                        )
                        .fillMaxWidth()
                        .testTag(TestTags.PRIMARY_BUTTON),
                    text = stringResource(id = R.string.sign_up)
                )
            }

            Text(
                text = stringResource(id = R.string.i_have_an_account_login),
                style = MaterialTheme.typography.body2,
                color = Color.LightGray,
                modifier = Modifier
                    .padding(bottom = 50.dp)
                    .clickable {
                        viewModel.onEvent(AuthEvent.RegisterLoginButtonClicked)
                    }
                    .align(Alignment.BottomCenter)
                    .padding(10.dp)
                    .testTag(TestTags.LOGIN_BUTTON)
            )
        }
    }
}
