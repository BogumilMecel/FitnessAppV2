package com.gmail.bodziowaty6978.fitnessappv2.feature_account.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.bodziowaty6978.fitnessappv2.common.data.navigation.NavigationActions
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.navigation.Navigator
import com.gmail.bodziowaty6978.fitnessappv2.common.util.CustomResult
import com.gmail.bodziowaty6978.fitnessappv2.feature_account.domain.use_case.DeleteToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val navigator: Navigator,
    private val deleteToken: DeleteToken
): ViewModel(){

    private val _errorState = Channel<String>()
    val errorState = _errorState.consumeAsFlow()

    fun onEvent(accountEvent: AccountEvent){
        when(accountEvent){
            is AccountEvent.OnLogOutButtonClicked -> {
                logOut()
            }
        }
    }

    private fun logOut(){
        viewModelScope.launch {
            val result = deleteToken()
            if (result is CustomResult.Success){
                navigator.navigate(NavigationActions.AccountScreen.accountToLogin())
            } else if(result is CustomResult.Error) {
                _errorState.send(result.message)
            }
        }
    }
}