package com.gmail.bodziowaty6978.fitnessappv2.feature_auth.presentation.reset_password

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.DefaultTextField
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.Toolbar
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.presentation.util.AuthEvent
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun ResetPasswordScreen(
    viewModel: ResetPasswordViewModel = hiltViewModel()
) {
    val isLoadingState = viewModel.isLoading.value
    val emailState = viewModel.emailState.value

    Scaffold(
        topBar = {
            Toolbar(
                title = stringResource(id = R.string.reset_your_password),
                isBackArrowVisible = true,
            ){
                viewModel.onEvent(AuthEvent.RegisterLoginButtonClicked)
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues.calculateTopPadding())
        ) {
            if (!isLoadingState) {

                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                ) {


                    DefaultTextField(
                        value = emailState.text,
                        placeholder = {
                            Text(text = emailState.hint)
                        },
                        onValueChange = {
                            viewModel.onEvent(AuthEvent.EnteredEmail(email = it))
                        },
                        keyboardOptions = KeyboardOptions().copy(
                            keyboardType = KeyboardType.Email
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag(stringResource(id = R.string.EMAIL))
                            .padding(horizontal = 20.dp),
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Email, contentDescription = emailState.hint)
                        }

                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    Button(
                        onClick = {
                            viewModel.onEvent(AuthEvent.AuthButtonClicked)
                        },
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth()
                            .testTag(stringResource(id = R.string.BUTTON)),
                        shape = RoundedCornerShape(50)
                    ) {

                        Text(
                            text = stringResource(id = R.string.reset_my_password).uppercase(),
                            style = MaterialTheme.typography.button,
                            modifier = Modifier
                                .padding(5.dp),
                        )

                    }
                }

            } else {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}