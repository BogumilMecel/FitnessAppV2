package com.gmail.bogumilmecel2.fitnessappv2.feature_account.presentation

import androidx.lifecycle.viewModelScope
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseViewModel
import com.gmail.bogumilmecel2.fitnessappv2.destinations.EditNutritionGoalsScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.LoginScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.feature_account.domain.use_case.DeleteToken
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.CreatePieChartData
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.domain.model.NutritionData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val deleteToken: DeleteToken,
    private val createPieChartData: CreatePieChartData
) : BaseViewModel<AccountState, AccountEvent>(state = AccountState()) {

    override fun onEvent(event: AccountEvent) {
        when (event) {
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
            initializeProductData(cachedValuesProvider.getWantedNutritionValues())
        }
    }

    private fun initializeProductData(wantedNutritionValues: NutritionValues) {
        _state.update {
            it.copy(
                nutritionData = NutritionData(
                    nutritionValues = wantedNutritionValues,
                    pieChartData = createPieChartData(nutritionValues = wantedNutritionValues)
                ),
            )
        }
    }

    private fun logOut() {
        viewModelScope.launch {
            deleteToken().handle {
                navigateWithPopUp(LoginScreenDestination)
            }
        }
    }
}