package com.gmail.bogumilmecel2.fitnessappv2.feature_auth.presentation.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.components.Toolbar
import com.gmail.bogumilmecel2.fitnessappv2.common.util.TestTags
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.presentation.util.AuthEvent
import com.gmail.bogumilmecel2.ui.components.base.CustomBasicTextField
import com.gmail.bogumilmecel2.ui.components.base.CustomButton
import com.gmail.bogumilmecel2.ui.components.base.IconVector
import com.gmail.bogumilmecel2.ui.components.base.LeftContent
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    Scaffold(
        topBar = {
            Toolbar(
                title = stringResource(id = R.string.login),
                isBackArrowVisible = false,
            ) {

            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues.calculateTopPadding())
        ) {
            if (!state.isLoading) {
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

                    Spacer(modifier = Modifier.height(40.dp))

                    CustomButton(
                        onClick = {
                            viewModel.onEvent(AuthEvent.AuthButtonClicked)
                        },
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth()
                            .testTag(TestTags.General.PRIMARY_BUTTON),
                        leftContent = LeftContent.Icon(IconVector.Login),
                        text = stringResource(id = R.string.sign_in)
                    )

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .padding(horizontal = 20.dp)
                    ) {

                        Text(
                            text = stringResource(id = R.string.forgot_password),
                            style = MaterialTheme.typography.body2,
                            modifier = Modifier
                                .clickable {
                                    viewModel.onEvent(AuthEvent.ForgotButtonClicked)
                                }
                                .padding(10.dp)
                        )


                    }
                }

//                GoogleSignInButton()

                Text(
                    text = stringResource(id = R.string.i_don_t_have_an_account_register),
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier
                        .padding(bottom = 50.dp)
                        .clickable {
                            viewModel.onEvent(AuthEvent.RegisterLoginButtonClicked)
                        }
                        .padding(10.dp)
                        .align(Alignment.BottomCenter)
                )

            } else {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}