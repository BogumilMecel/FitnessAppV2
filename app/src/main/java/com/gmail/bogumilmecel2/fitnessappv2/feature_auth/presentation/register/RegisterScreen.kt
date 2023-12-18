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
import androidx.compose.material.Scaffold
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.components.Toolbar
import com.gmail.bogumilmecel2.fitnessappv2.common.util.ConfigureViewModel
import com.gmail.bogumilmecel2.fitnessappv2.common.util.TestTags
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.presentation.util.AuthEvent
import com.gmail.bogumilmecel2.ui.components.base.CustomBasicTextField
import com.gmail.bogumilmecel2.ui.components.base.CustomButton
import com.gmail.bogumilmecel2.ui.components.base.IconVector
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun RegisterScreen(navigator: DestinationsNavigator) {
    hiltViewModel<RegisterViewModel>().ConfigureViewModel(navigator = navigator) { viewModel ->
        val state = viewModel.state.collectAsStateWithLifecycle().value

        Scaffold(
            topBar = {
                Toolbar(
                    title = stringResource(id = R.string.register),
                    isBackArrowVisible = true
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
                        testTag = TestTags.General.EMAIL
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
                        testTag = TestTags.RegisterScreen.USERNAME
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
                        testTag = TestTags.General.PASSWORD
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
                        testTag = TestTags.RegisterScreen.CONFIRM_PASSWORD
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
                            .testTag(TestTags.General.PRIMARY_BUTTON),
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
                )
            }
        }
    }
}
