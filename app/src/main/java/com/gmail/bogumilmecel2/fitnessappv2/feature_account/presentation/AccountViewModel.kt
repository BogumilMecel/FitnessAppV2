package com.gmail.bogumilmecel2.fitnessappv2.feature_account.presentation

import androidx.lifecycle.viewModelScope
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.CachedValuesProvider
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
    private val useCases: AccountUseCases,
    private val cachedValuesProvider: CachedValuesProvider
) : BaseViewModel<AccountState, AccountEvent, Unit>(
    state = AccountState(),
    navArguments = Unit
) {

    override fun configureOnStart() {
        getWantedNutritionValues()
        getWeightDialogsData()
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

            is AccountEvent.AskForWeightDailyClicked -> {
                handleAskForWeightDaily(event.checked)
            }
        }
    }

    private fun getWantedNutritionValues() {
        viewModelScope.launch {
            with(cachedValuesProvider.getWantedNutritionValues()) {
                _state.update {
                    it.copy(
                        nutritionData = NutritionData(
                            nutritionValues = this,
                            pieChartData = useCases.createPieChartDataUseCase(nutritionValues = this)
                        ),
                    )
                }
            }
        }
    }

    private fun handleAskForWeightDaily(accepted: Boolean) {
        changeAskForWeightDaily(accepted = accepted)
        viewModelScope.launch {
            useCases.saveAskForWeightDailyUseCase(
                accepted = accepted,
                cachedValuesProvider = cachedValuesProvider
            ).handle(
                onError = { changeAskForWeightDaily(accepted = !accepted) }
            ) {}
        }
    }

    private fun changeAskForWeightDaily(accepted: Boolean) {
        _state.update {
            it.copy(
                askForWeightDaily = accepted,
            )
        }
    }

    private fun getWeightDialogsData() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    askForWeightDaily = cachedValuesProvider.getUser()?.askForWeightDaily == true
                )
            }
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