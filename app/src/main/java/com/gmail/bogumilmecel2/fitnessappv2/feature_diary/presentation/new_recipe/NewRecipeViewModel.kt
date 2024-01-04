package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_recipe

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseResultViewModel
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.toValidInt
import com.gmail.bogumilmecel2.fitnessappv2.destinations.NewRecipeScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.RecipeScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.SearchScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.ProductResult
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Ingredient
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipePrice
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.new_recipe.NewRecipeUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.CalculateProductNutritionValuesUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_recipe.util.SelectedNutritionType
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.domain.model.NutritionData
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.recipe.RecipeEntryData
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search.SearchEntryData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewRecipeViewModel @Inject constructor(
    private val newRecipeUseCases: NewRecipeUseCases,
    savedStateHandle: SavedStateHandle,
    private val calculateProductNutritionValuesUseCase: CalculateProductNutritionValuesUseCase
) : BaseResultViewModel<NewRecipeState, NewRecipeEvent, NewRecipeNavArguments, ProductResult>(
    state = NewRecipeState(),
    navArguments = NewRecipeScreenDestination.argsFrom(savedStateHandle)
) {
    private val ingredients = mutableListOf<Ingredient>()

    override fun onEvent(event: NewRecipeEvent) {
        when (event) {
            is NewRecipeEvent.ClickedTimeArrow -> {
                _state.update {
                    it.copy(
                        isTimeExpanded = !it.isTimeExpanded
                    )
                }
            }

            is NewRecipeEvent.ClickedDifficultyArrow -> {
                _state.update {
                    it.copy(
                        isDifficultyExpanded = !it.isDifficultyExpanded
                    )
                }
            }

            is NewRecipeEvent.ClickedServingsArrow -> {
                _state.update {
                    it.copy(
                        isServingsExpanded = !it.isServingsExpanded
                    )
                }
            }

            is NewRecipeEvent.ClickedBackArrow -> {
                navigateUp()
            }

            is NewRecipeEvent.SelectedDifficulty -> {
                _state.update {
                    it.copy(
                        selectedDifficulty = event.difficulty,
                        isDifficultyExpanded = !it.isDifficultyExpanded
                    )
                }
            }

            is NewRecipeEvent.EnteredServing -> {
                _state.update {
                    it.copy(
                        servings = event.value,
                        isServingsExpanded = !it.isServingsExpanded
                    )
                }
                calculateRecipeInformation()
            }

            is NewRecipeEvent.SelectedTime -> {
                _state.update {
                    it.copy(
                        selectedTime = event.time,
                        isTimeExpanded = !it.isTimeExpanded
                    )
                }
            }

            is NewRecipeEvent.EnteredName -> {
                _state.update {
                    it.copy(
                        name = event.value,
                    )
                }
            }

            is NewRecipeEvent.ClickedAddNewIngredient -> {
                navigateTo(
                    destination = SearchScreenDestination(
                        entryData = SearchEntryData.NewRecipeArguments(
                            recipeName = _state.value.name
                        )
                    )
                )
            }

            is NewRecipeEvent.CheckedNutritionType -> {
                _state.update {
                    it.copy(
                        selectedNutritionType = if (event.checked) SelectedNutritionType.Recipe else SelectedNutritionType.Serving
                    )
                }
                calculateRecipeInformation()
            }

            is NewRecipeEvent.ClickedSaveRecipe -> {
                saveNewRecipe()
            }

            is NewRecipeEvent.SwitchedPublic -> {
                _state.update {
                    it.copy(
                        isRecipePublic = event.value
                    )
                }
            }

            is NewRecipeEvent.ClickedIngredientsListArrow -> {
                _state.update {
                    it.copy(
                        isIngredientsListExpanded = !it.isIngredientsListExpanded
                    )
                }
            }

            is NewRecipeEvent.LongClickedIngredient -> {
                _state.update {
                    it.copy(
                        isDeleteIngredientDialogVisible = true,
                        longClickedIngredient = event.ingredient
                    )
                }
            }

            is NewRecipeEvent.DismissedDeleteIngredientDialog -> {
                _state.update {
                    it.copy(
                        isDeleteIngredientDialogVisible = false
                    )
                }
            }

            is NewRecipeEvent.ClickedDeleteInIngredientDialog -> {
                _state.value.longClickedIngredient?.let { ingredient ->
                    ingredients.remove(ingredient)
                    _state.update {
                        it.copy(
                            isDeleteIngredientDialogVisible = false
                        )
                    }
                    createRecipeIngredientsParams()
                    fetchPrices()
                }
            }

            is NewRecipeEvent.ReceivedProductResult -> {
                addProductToRecipe(event.productResult)
            }
        }
    }

    private fun createRecipeIngredientsParams() {
        _state.update {
            it.copy(
                ingredientsItemsParams = ingredients.map { ingredient ->
                    newRecipeUseCases.createSearchItemParamsFromIngredientUseCase(
                        ingredient = ingredient,
                        onClick = {
                            // TODO: Add action for onclick ingredient
                        },
                        onLongClick = {
                            onEvent(NewRecipeEvent.LongClickedIngredient(ingredient))
                        }
                    )
                }
            )
        }
    }

    private fun saveNewRecipe() {
        viewModelScope.launch {
            with(_state.value) {
                newRecipeUseCases.addNewRecipe(
                    ingredients = this@NewRecipeViewModel.ingredients,
                    time = selectedTime,
                    servings = servings.toValidInt() ?: return@launch,
                    difficulty = selectedDifficulty,
                    recipeName = name,
                    isRecipePublic = isRecipePublic
                ).handle {
                    navigateTo(
                        destination = RecipeScreenDestination(
                            entryData = RecipeEntryData.Adding(
                                recipe = it,
                                mealName = navArguments.mealName,
                                date = navArguments.date
                            )
                        )
                    )
                }
            }
        }
    }

    private fun addProductToRecipe(productResult: ProductResult) {
        viewModelScope.launch(Dispatchers.Default) {
            with(productResult) {
                // TODO: Handle nulls
                val newIngredient = Ingredient(
                    weight = weight,
                    productName = product.name ?: return@launch,
                    measurementUnit = product.measurementUnit ?: return@launch,
                    nutritionValues = calculateProductNutritionValuesUseCase(
                        product = product,
                        weight = weight
                    ) ?: return@launch,
                    productId = product.id ?: return@launch
                )

                ingredients.removeIf { it.productId == newIngredient.productId }
                ingredients.add(newIngredient)

                createRecipeIngredientsParams()
                fetchPrices()
                calculateRecipeInformation()
            }
        }
    }

    private fun fetchPrices() {
        viewModelScope.launch {
            if (ingredients.isNotEmpty()) {
                newRecipeUseCases.getRecipePriceFromIngredientsUseCase(
                    ingredients = ingredients
                ).handle(
                    showSnackbar = false
                ) { response ->
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
                    updateRecipeServingPrice()
                }
            } else {
                _state.update {
                    it.copy(
                        recipePrice = null
                    )
                }
            }
        }
    }

    private fun calculateRecipeInformation() {
        viewModelScope.launch(Dispatchers.Default) {
            with(_state.value) {
                val servings: Int? =
                    if (selectedNutritionType is SelectedNutritionType.Recipe) 1 else servings.toIntOrNull()
                servings?.let {
                    if (servings > 0) {
                        updateRecipeNutritionData(it)
                        updateRecipeServingPrice()
                    }
                }
            }
        }
    }

    private fun updateRecipeServingPrice() = with(_state.value) {
        servings.toValidInt()?.let { servings ->
            recipePrice?.let { recipePrice ->
                _state.update {
                    it.copy(
                        servingPrice = newRecipeUseCases.calculateServingPrice(
                            servings = servings,
                            priceValue = recipePrice.totalPrice
                        )
                    )
                }
            }
        }
    }

    private fun updateRecipeNutritionData(servings: Int) {
        val nutritionValues = newRecipeUseCases.calculateRecipeNutritionValues(
            servings = servings,
            ingredients = ingredients
        )
        _state.update {
            it.copy(
                nutritionData = NutritionData(
                    nutritionValues = nutritionValues,
                    pieChartData = newRecipeUseCases.createPieChartDataUseCase(
                        nutritionValues = nutritionValues
                    )
                )
            )
        }
    }
}