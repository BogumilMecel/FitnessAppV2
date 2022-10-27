package com.gmail.bodziowaty6978.fitnessappv2.feature_account.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.bodziowaty6978.fitnessappv2.feature_account.presentation.components.LogOutButton

@Composable
fun AccountScreen(
    viewModel: AccountViewModel = hiltViewModel()
) {
    LogOutButton(modifier = Modifier.fillMaxWidth()
        .padding(horizontal = 15.dp)
    ) {

    }

}