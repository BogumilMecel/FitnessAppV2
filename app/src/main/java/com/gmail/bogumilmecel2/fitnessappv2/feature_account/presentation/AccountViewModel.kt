package com.gmail.bogumilmecel2.fitnessappv2.feature_account.presentation

import androidx.lifecycle.viewModelScope
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseViewModel
import com.gmail.bogumilmecel2.fitnessappv2.destinations.EditNutritionGoalsScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.LoginScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.feature_account.domain.use_case.AccountUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.domain.model.NutritionData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val useCases: AccountUseCases
) : BaseViewModel<AccountState, AccountEvent, Unit>(
    state = AccountState(),
    navArguments = Unit
) {

    override fun configureOnStart() {
        getWantedNutritionValues()
    }

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
                    pieChartData = useCases.createPieChartDataUseCase(nutritionValues = wantedNutritionValues)
                ),
            )
        }
    }

    private fun logOut() {
        viewModelScope.launch {
            useCases.deleteTokenUseCase().handle {
                navigateWithPopUp(LoginScreenDestination)
            }
        }
    }
}