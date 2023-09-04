package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.recipe

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.multiplyBy
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseViewModel
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.toValidInt
import com.gmail.bogumilmecel2.fitnessappv2.destinations.DiaryScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.RecipeScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipePrice
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.recipe.RecipeUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.domain.model.NutritionData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipeUseCases: RecipeUseCases,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<RecipeState, RecipeEvent, RecipeNavArguments>(
    state = RecipeState(),
    navArguments = RecipeScreenDestination.argsFrom(savedStateHandle = savedStateHandle)
) {

    override fun configureOnStart() {
        with(navArguments.entryData.recipe) {
            _state.update {
                it.copy(
                    servings = if (navArguments.entryData is RecipeEntryData.Editing) navArguments.entryData.recipeDiaryEntry.servings.toString() else "",
                    date = navArguments.entryData.dateTransferObject.displayedDate,
                    difficultyText = resourceProvider.getString(
                        stringResId = R.string.recipe_difficulty_out_of_5,
                        difficulty.displayValue
                    ),
                    recipeName = name,
                    recipeCaloriesText = resourceProvider.getString(
                        stringResId = R.string.kcal_with_value,
                        nutritionValues.calories
                    ),
                    timeRequiredText = timeRequired.displayValue,
                    servingsText = resourceProvider.getString(
                        stringResId = R.string.recipe_serves,
                        servings
                    ),
                    saveButtonText = resourceProvider.getString(
                        stringResId = R.string.recipe_save_to,
                        resourceProvider.getString(navArguments.entryData.mealName.getDisplayValue())
                    ),
                    nutritionData = NutritionData(
                        pieChartData = recipeUseCases.createPieChartData(
                            nutritionValues = nutritionValues
                        ).also {
                            assignNutritionValues()
                        }
                    ),
                    ingredientsParams = ingredients.map { ingredient ->
                        recipeUseCases.createSearchItemParamsFromIngredientUseCase(
                            ingredient = ingredient,
                            onClick = {},
                            onLongClick = {}
                        )
                    }
                )
            }
        }

        fetchRecipePrice()
    }

    override fun onEvent(event: RecipeEvent) {
        when (event) {
            is RecipeEvent.ClickedBackArrow -> navigateUp()
            is RecipeEvent.ClickedFavorite -> {
                onFavoriteClicked()
            }

            is RecipeEvent.EnteredServings -> {
                _state.update {
                    it.copy(
                        servings = event.value
                    )
                }
                recalculateNutritionData()
                updateServingPrice()
            }

            is RecipeEvent.ClickedExpandIngredientsList -> {
                _state.update {
                    it.copy(
                        isIngredientsListExpanded = !_state.value.isIngredientsListExpanded
                    )
                }
            }

            is RecipeEvent.ClickedSaveRecipeDiaryEntry -> {
                handleSaveButtonClicked()
            }
        }
    }

    private fun handleSaveButtonClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            with(_state.value) {
                when (navArguments.entryData) {
                    is RecipeEntryData.Editing -> {
                        recipeUseCases.editRecipeDiaryEntryUseCase(
                            recipeDiaryEntry = navArguments.entryData.recipeDiaryEntry,
                            newServingsStringValue = servings,
                            recipe = navArguments.entryData.recipe
                        ).handle {
                            navigateUp()
                        }
                    }

                    is RecipeEntryData.Adding -> {
                        recipeUseCases.postRecipeDiaryEntryUseCase(
                            date = navArguments.entryData.dateTransferObject.realDate,
                            mealName = navArguments.entryData.mealName,
                            recipe = navArguments.entryData.recipe,
                            servingsString = _state.value.servings
                        ).handle {
                            navigateWithPopUp(
                                destination = DiaryScreenDestination,
                                popUpTo = DiaryScreenDestination.route
                            )
                        }
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

    private fun assignNutritionValues() {
        val recipe = navArguments.entryData.recipe
        val recipeServings = if (recipe.servings == 0) 1.0 else recipe.servings.toDouble()
        recipe.nutritionValues.multiplyBy(
            number = _state.value.servings.toDoubleOrNull()?.let { portions ->
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

    private fun fetchRecipePrice() {
        viewModelScope.launch {
            recipeUseCases.getRecipePriceFromIngredientsUseCase(
                ingredients = navArguments.entryData.recipe.ingredients
            ).handle(showSnackbar = false) { response ->
                _state.update {
                    it.copy(
                        recipePrice = response?.let {
                            RecipePrice(
                                totalPrice = response.totalPrice,
                                shouldShowPriceWarning = response.shouldShowPriceWarning
                            )
                        }
                    )
                }
                updateServingPrice()
            }
        }
    }

    private fun updateServingPrice() = with(_state.value) {
        servings.toValidInt()?.let { servings ->
            recipePrice?.let { recipePrice ->
                _state.update {
                    it.copy(
                        servingPrice = recipeUseCases.calculateSelectedServingPriceUseCase(
                            recipeServings = navArguments.entryData.recipe.servings,
                            selectedServings = servings,
                            totalPrice = recipePrice.totalPrice
                        ),
                    )
                }
            }
        }
    }
}