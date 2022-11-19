package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.edit_nutrition_goals

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.bodziowaty6978.fitnessappv2.common.data.navigation.NavigationActions
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.navigation.Navigator
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.use_case.CalculateNutritionValuesPercentages
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.use_case.GetWantedNutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.use_case.SaveNutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.extensions.round
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNutritionGoalsViewModel @Inject constructor(
    private val navigator: Navigator,
    private val savedStateHandle: SavedStateHandle,
    private val getWantedNutritionValues: GetWantedNutritionValues,
    private val calculateNutritionValuesPercentages: CalculateNutritionValuesPercentages,
    private val saveNutritionValues: SaveNutritionValues
) : ViewModel() {

    private val _errorState = Channel<String>()
    val errorState = _errorState.consumeAsFlow()

    private val _state = MutableStateFlow(EditNutritionGoalsState())
    val state: StateFlow<EditNutritionGoalsState> = _state

    init {
        initWantedNutritionValues()
    }

    fun onEvent(event: EditNutritionGoalsEvent) {
        when (event) {
            is EditNutritionGoalsEvent.EnteredCalories -> {
                _state.update {
                    it.copy(
                        calories = event.value
                    )
                }
                event.value.toIntOrNull()?.let { newValue ->
                    _state.update {
                        it.copy(
                            nutritionValues = it.nutritionValues.copy(calories = newValue)
                        )
                    }
                    calculateAllNutritionValues()
                }
            }
            is EditNutritionGoalsEvent.CarbohydratesPickerValueChanged -> {
                _state.update {
                    it.copy(
                        carbohydratesPercentageValue = event.value.replace("%", "").toFloat()
                    )
                }
                calculateTotalPercentage()
                calculateCarbohydratesValue()
            }
            is EditNutritionGoalsEvent.ProteinPickerValueChanged -> {
                _state.update {
                    it.copy(
                        proteinPercentageValue = event.value.replace("%", "").toFloat()
                    )
                }
                calculateTotalPercentage()
                calculateProteinValue()
            }
            is EditNutritionGoalsEvent.FatPickerValueChanged -> {
                _state.update {
                    it.copy(
                        fatPercentageValue = event.value.replace("%", "").toFloat()
                    )
                }
                calculateTotalPercentage()
                calculateFatValue()
            }
            is EditNutritionGoalsEvent.BackArrowPressed -> {
                navigator.navigate(NavigationActions.General.navigateUp())
            }
            is EditNutritionGoalsEvent.SaveButtonClicked -> {
                saveNutritionValues()
            }
        }
    }

    private fun saveNutritionValues() {
        viewModelScope.launch {
            when (val resource = saveNutritionValues(_state.value.nutritionValues)) {
                is Resource.Error -> {
                    resource.uiText?.let {
                        _errorState.send(it)
                    }
                }
                is Resource.Success -> {
                    navigator.navigate(NavigationActions.EditNutritionGoals.editNutritionGoalsToAccount())
                }
            }
        }
    }

    private fun calculateAllNutritionValues() {
        calculateFatValue()
        calculateProteinValue()
        calculateCarbohydratesValue()
    }

    private fun calculateCarbohydratesValue() {
        _state.update {
            it.copy(
                nutritionValues = it.nutritionValues.copy(
                    carbohydrates = ((it.nutritionValues.calories / 4.0) * it.carbohydratesPercentageValue.toDouble() / 100.0).round(2)
                )
            )
        }
    }

    private fun calculateProteinValue() {
        _state.update {
            it.copy(
                nutritionValues = it.nutritionValues.copy(
                    protein = ((it.nutritionValues.calories / 4.0) * it.proteinPercentageValue.toDouble() / 100.0).round(2)
                )
            )
        }
    }

    private fun calculateFatValue() {
        _state.update {
            it.copy(
                nutritionValues = it.nutritionValues.copy(
                    fat = ((it.nutritionValues.calories / 9.0) * it.fatPercentageValue.toDouble() / 100.0).round(2)
                )
            )
        }
    }

    private fun initWantedNutritionValues() {
        val wantedNutritionValues = getWantedNutritionValues()
        _state.update {
            it.copy(
                nutritionValues = wantedNutritionValues,
                calories = wantedNutritionValues.calories.toString()
            )
        }
        calculatePercentages()
    }

    private fun calculatePercentages() {
        val percentages = calculateNutritionValuesPercentages(_state.value.nutritionValues)
        _state.update {
            it.copy(
                carbohydratesPercentageValue = percentages["carbohydrates"] ?: 0F,
                proteinPercentageValue = percentages["protein"] ?: 0F,
                fatPercentageValue = percentages["fat"] ?: 0F,
            )
        }
        calculateTotalPercentage()
    }

    private fun calculateTotalPercentage() {
        _state.update {
            it.copy(
                totalPercentage = it.fatPercentageValue + it.proteinPercentageValue + it.carbohydratesPercentageValue
            )
        }
    }
}