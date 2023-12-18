package com.gmail.bodziowaty6978.fitnessappv2.feature_account.presentation

import androidx.lifecycle.viewModelScope
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.common.util.BaseViewModel
import com.gmail.bodziowaty6978.fitnessappv2.common.util.CustomResult
import com.gmail.bodziowaty6978.fitnessappv2.destinations.EditNutritionGoalsScreenDestination
import com.gmail.bodziowaty6978.fitnessappv2.destinations.LoginScreenDestination
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

    fun onEvent(accountEvent: AccountEvent) {
        when (accountEvent) {
            is AccountEvent.ClickedLogOutButtonClicked -> {
                logOut()
            }

            is AccountEvent.ClickedEditNutritionGoals -> {
                navigateTo(EditNutritionGoalsScreenDestination)
            }

            is AccountEvent.BackPressed -> {
                navigateUp()
            }
        }
    }

    fun initData() {
        getWantedNutritionValues()
    }

    private fun getWantedNutritionValues() {
        viewModelScope.launch {
            initializeProductData(sharedPreferencesUtils.getWantedNutritionValues())
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
                navigateWithPopUp(LoginScreenDestination)
            } else if (result is CustomResult.Error) {
                showSnackbarError(result.message)
            }
        }
    }
}