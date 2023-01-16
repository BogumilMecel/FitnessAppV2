package com.gmail.bodziowaty6978.fitnessappv2.feature_account.presentation

import androidx.lifecycle.viewModelScope
import com.gmail.bodziowaty6978.fitnessappv2.FitnessApp
import com.gmail.bodziowaty6978.fitnessappv2.common.data.navigation.NavigationActions
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.common.util.BaseViewModel
import com.gmail.bodziowaty6978.fitnessappv2.common.util.CustomResult
import com.gmail.bodziowaty6978.fitnessappv2.feature_account.domain.use_case.DeleteToken
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.product.CreatePieChartData
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.components.NutritionData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val deleteToken: DeleteToken,
    private val createPieChartData: CreatePieChartData
) : BaseViewModel() {
    private val _state = MutableStateFlow(AccountState())
    val state: StateFlow<AccountState> = _state

    init {
        getWantedNutritionValues()
    }

    fun onEvent(accountEvent: AccountEvent) {
        when (accountEvent) {
            is AccountEvent.ClickedLogOutButtonClicked -> {
                logOut()
            }

            is AccountEvent.ClickedEditNutritionGoals -> {
                navigate(NavigationActions.AccountScreen.accountToEditNutritionGoals())
            }

            is AccountEvent.BackPressed -> {
                navigate(NavigationActions.AccountScreen.accountToSummary())
            }
        }
    }

    private fun getWantedNutritionValues() {
        viewModelScope.launch {
            initializeProductData(FitnessApp.getWantedNutritionValues())
        }
    }

    private fun initializeProductData(wantedNutritionValues: NutritionValues) {
        _state.update {
            it.copy(
                nutritionData = NutritionData(
                    nutritionValues = wantedNutritionValues,
                    pieEntries = createPieChartData(nutritionValues = wantedNutritionValues)
                ),
            )
        }
    }

    private fun logOut() {
        viewModelScope.launch {
            val result = deleteToken()
            if (result is CustomResult.Success) {
                navigate(NavigationActions.AccountScreen.accountToLogin())
            } else if (result is CustomResult.Error) {
                showSnackbarError(result.message)
            }
        }
    }
}