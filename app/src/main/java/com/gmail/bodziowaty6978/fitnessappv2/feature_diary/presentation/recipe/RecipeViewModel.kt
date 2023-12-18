package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.recipe

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.gmail.bodziowaty6978.fitnessappv2.common.data.singleton.CurrentDate
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.multiplyBy
import com.gmail.bodziowaty6978.fitnessappv2.common.util.BaseViewModel
import com.gmail.bodziowaty6978.fitnessappv2.destinations.DiaryScreenDestination
import com.gmail.bodziowaty6978.fitnessappv2.destinations.RecipeScreenDestination
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.product.CreatePieChartData
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.recipe.PostRecipeDiaryEntryUseCase
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.components.NutritionData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val createPieChartData: CreatePieChartData,
    private val postRecipeDiaryEntryUseCase: PostRecipeDiaryEntryUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _state = MutableStateFlow(
        RecipeState(
            recipe = RecipeScreenDestination.argsFrom(savedStateHandle = savedStateHandle).recipe,
            mealName = RecipeScreenDestination.argsFrom(savedStateHandle = savedStateHandle).mealName
        )
    )
    val state: StateFlow<RecipeState> = _state

    init {
        initializeRecipeData()
    }

    fun onEvent(event: RecipeEvent) {
        when (event) {
            is RecipeEvent.ClickedBackArrow -> navigateUp()
            is RecipeEvent.ClickedFavorite -> {
                onFavoriteClicked()
            }

            is RecipeEvent.EnteredPortions -> {
                _state.update {
                    it.copy(
                        portions = event.value
                    )
                }
                recalculateNutritionData()
            }

            is RecipeEvent.ClickedExpandIngredientsList -> {
                _state.update {
                    it.copy(
                        isIngredientsListExpanded = !_state.value.isIngredientsListExpanded
                    )
                }
            }

            is RecipeEvent.ClickedSaveRecipeDiaryEntry -> {
                viewModelScope.launch {
                    postRecipeDiaryEntryUseCase(
                        dateModel = CurrentDate.dateModel(resourceProvider = resourceProvider),
                        mealName = _state.value.mealName,
                        recipe = _state.value.recipe,
                        servingsString = _state.value.portions
                    ).handle {
                        navigateWithPopUp(
                            destination = DiaryScreenDestination
                        )
                    }
                }
            }
        }
    }

    private fun onFavoriteClicked() {
        _state.update {
            it.copy(
                isFavorite = !_state.value.isFavorite
            )
        }
    }

    private fun recalculateNutritionData() {
        assignNutritionValues()
    }

    private fun initializeRecipeData() {
        _state.value.recipe.nutritionValues.let { recipeNutritionValues ->
            _state.update {
                it.copy(
                    nutritionData = NutritionData(
                        pieEntries = createPieChartData(nutritionValues = recipeNutritionValues)
                    ),
                )
            }
            assignNutritionValues()
        }
    }

    private fun assignNutritionValues() {
        val recipe = _state.value.recipe
        val recipeServings = if (recipe.servings == 0) 1.0 else recipe.servings.toDouble()
        recipe.nutritionValues.multiplyBy(
            number = _state.value.portions.toDoubleOrNull()?.let { portions ->
                portions / recipeServings
            } ?: (1.0 / recipeServings)
        ).let { calculatedNutritionValues ->
            _state.update {
                it.copy(
                    nutritionData = _state.value.nutritionData.copy(
                        nutritionValues = calculatedNutritionValues
                    )
                )
            }
        }
    }
}