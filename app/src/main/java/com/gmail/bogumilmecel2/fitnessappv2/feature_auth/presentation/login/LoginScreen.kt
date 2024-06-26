package com.gmail.bogumilmecel2.fitnessappv2.feature_auth.presentation.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.util.TestTags
import com.gmail.bogumilmecel2.fitnessappv2.common.util.ViewModelLayout
import com.gmail.bogumilmecel2.fitnessappv2.components.defaultRoundedCornerShape
import com.gmail.bogumilmecel2.ui.components.base.CustomBasicTextField
import com.gmail.bogumilmecel2.ui.components.base.CustomButton
import com.gmail.bogumilmecel2.ui.components.base.HeightSpacer
import com.gmail.bogumilmecel2.ui.components.base.IconVector
import com.gmail.bogumilmecel2.ui.components.complex.HeaderRow
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun LoginScreen(navigator: DestinationsNavigator) {
    hiltViewModel<LoginViewModel>().ViewModelLayout(
        navigator = navigator,
        screenTestTag = TestTags.LOGIN_SCREEN
    ) {
        val state by __state.collectAsStateWithLifecycle()
        Box(modifier = Modifier.fillMaxSize()) {
            HeaderRow(middlePrimaryText = stringResource(id = R.string.login))

            Column(modifier = Modifier.align(Alignment.Center)) {
                CustomBasicTextField(
                    value = state.email,
                    placeholder = stringResource(id = R.string.email_address),
                    onValueChange = { onEvent(LoginEvent.EnteredEmail(email = it)) },
                    keyboardOptions = KeyboardOptions().copy(
                        keyboardType = KeyboardType.Email
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    leadingIcon = IconVector.Email,
                    testTag = TestTags.EMAIL
                )

                HeightSpacer(16.dp)

                CustomBasicTextField(
                    value = state.password,
                    placeholder = stringResource(id = R.string.password),
                    onValueChange = { onEvent(LoginEvent.EnteredPassword(password = it)) },
                    keyboardOptions = KeyboardOptions().copy(
                        keyboardType = KeyboardType.Password,
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    leadingIcon = IconVector.Password,
                    testTag = TestTags.PASSWORD
                )

                HeightSpacer(32.dp)

                CustomButton(
                    onClick = { onEvent(LoginEvent.LoginButtonClicked) },
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth()
                        .testTag(TestTags.PRIMARY_BUTTON),
                    leftIcon = IconVector.Login,
                    text = stringResource(id = R.string.sign_in)
                )

                HeightSpacer(16.dp)

                Text(
                    text = stringResource(id = R.string.forgot_password),
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier
                        .testTag(TestTags.FORGOT_PASSWORD)
                        .clip(defaultRoundedCornerShape())
                        .clickable { onEvent(LoginEvent.ForgotButtonClicked) }
                        .padding(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        )
                )
            }

            Text(
                text = stringResource(id = R.string.i_don_t_have_an_account_register),
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .padding(bottom = 50.dp)
                    .clip(defaultRoundedCornerShape())
                    .clickable { onEvent(LoginEvent.RegisterLoginButtonClicked) }
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    )
                    .align(Alignment.BottomCenter)
                    .testTag(TestTags.REGISTER_BUTTON)
            )
        }
    }
}