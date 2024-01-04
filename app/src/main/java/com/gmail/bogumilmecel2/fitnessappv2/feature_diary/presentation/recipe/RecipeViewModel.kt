package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.recipe

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.github.tehras.charts.piechart.PieChartData
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.multiplyBy
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseViewModel
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.getDisplayDate
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.let2
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
                    date = navArguments.entryData.date.getDisplayDate(resourceProvider),
                    difficultyText = difficulty?.let {
                        resourceProvider.getString(
                            stringResId = R.string.recipe_difficulty_out_of_5,
                            difficulty.displayValue
                        )
                    }.orEmpty(),
                    recipeName = name.orEmpty(),
                    recipeCaloriesText = nutritionValues?.calories?.let { calories ->
                        resourceProvider.getString(
                            stringResId = R.string.kcal_with_value,
                            calories
                        )
                    }.orEmpty(),
                    timeRequiredText = timeRequired?.displayValue.orEmpty(),
                    servingsText = servings?.let {
                        resourceProvider.getString(
                            stringResId = R.string.recipe_serves,
                            servings
                        )
                    }.orEmpty(),
                    saveButtonText = resourceProvider.getString(
                        stringResId = R.string.recipe_save_to,
                        resourceProvider.getString(navArguments.entryData.mealName.getDisplayValue())
                    ),
                    nutritionData = NutritionData(
                        pieChartData = nutritionValues?.let {
                            recipeUseCases.createPieChartDataUseCase(
                                nutritionValues = nutritionValues
                            ).also {
                                assignNutritionValues()
                            }
                        } ?: PieChartData(slices = emptyList())
                    ),
                    ingredientsParams = ingredients?.map { ingredient ->
                        recipeUseCases.createSearchItemParamsFromIngredientUseCase(
                            ingredient = ingredient,
                            onClick = {},
                            onLongClick = {}
                        )
                    }.orEmpty()
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
                            date = navArguments.entryData.date,
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
        with(recipe) {
            let2(servings, nutritionValues) { servings, nutritionValues ->
                val recipeServings = if (servings == 0) 1.0 else servings.toDouble()
                nutritionValues.multiplyBy(
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
        }
    }

    private fun fetchRecipePrice() {
        viewModelScope.launch {
            recipeUseCases.getRecipePriceFromIngredientsUseCase(
                ingredients = navArguments.entryData.recipe.ingredients.orEmpty()
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
                navArguments.entryData.recipe.servings?.let { recipeServings ->
                    _state.update {
                        it.copy(
                            servingPrice = recipeUseCases.calculateSelectedServingPriceUseCase(
                                recipeServings = recipeServings,
                                selectedServings = servings,
                                totalPrice = recipePrice.totalPrice
                            ),
                        )
                    }
                }
            }
        }
    }
}