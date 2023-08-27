package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.recipe

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
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
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<RecipeState, RecipeEvent>(
    state = with(RecipeScreenDestination.argsFrom(savedStateHandle = savedStateHandle)) {
        RecipeState(
            entryData = entryData,
            servings = if (entryData is RecipeEntryData.Editing) entryData.servings else "",
            date = entryData.dateTransferObject.displayedDate
        )
    }
) {

    init {
        initializeRecipeData()
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
                when(entryData) {
                    is RecipeEntryData.Editing -> {
                        recipeUseCases.editRecipeDiaryEntryUseCase(
                            recipeDiaryEntryId = entryData.recipeDiaryEntryId,
                            newServingsStringValue = servings,
                            originalServingsStringValue = entryData.servings
                        ).handle {
                            navigateUp()
                        }
                    }
                    is RecipeEntryData.Adding -> {
                        recipeUseCases.postRecipeDiaryEntryUseCase(
                            date = RecipeScreenDestination.argsFrom(savedStateHandle).entryData.dateTransferObject.realDate,
                            mealName = _state.value.entryData.mealName,
                            recipeId = _state.value.entryData.recipe.id,
                            servingsString = _state.value.servings
                        ).handle {
                            navigateWithPopUp(
                                destination = DiaryScreenDestination
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

    private fun initializeRecipeData() {
        _state.value.entryData.recipe.nutritionValues.let { recipeNutritionValues ->
            _state.update {
                it.copy(
                    nutritionData = NutritionData(
                        pieChartData = recipeUseCases.createPieChartData(nutritionValues = recipeNutritionValues)
                    ),
                )
            }
            assignNutritionValues()
        }
        _state.update {
            it.copy(
                ingredientsParams = _state.value.entryData.recipe.ingredients.map { ingredient ->
                    recipeUseCases.createSearchItemParamsFromIngredientUseCase(
                        ingredient = ingredient,
                        onClick = {},
                        onLongClick = {}
                    )
                }
            )
        }
    }

    private fun assignNutritionValues() {
        val recipe = _state.value.entryData.recipe
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
                ingredients = _state.value.entryData.recipe.ingredients
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

    private fun updateServingPrice() = with(_state.value){
        servings.toValidInt()?.let { servings ->
            recipePrice?.let { recipePrice ->
                _state.update {
                    it.copy(
                        servingPrice = recipeUseCases.calculateSelectedServingPriceUseCase(
                            recipeServings = it.entryData.recipe.servings,
                            selectedServings = servings,
                            totalPrice = recipePrice.totalPrice
                        ),
                    )
                }
            }
        }
    }
}