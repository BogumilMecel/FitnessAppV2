package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_recipe

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseViewModel
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.TAG
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.toValidInt
import com.gmail.bogumilmecel2.fitnessappv2.destinations.NewRecipeScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.RecipeScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Ingredient
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipePrice
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.new_recipe.NewRecipeUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.CalculateProductNutritionValuesUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_recipe.util.SelectedNutritionType
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.recipe.RecipeEntryData
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
) : BaseViewModel<NewRecipeState, NewRecipeEvent, NewRecipeNavArguments>(
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
                if (_state.value.isSearchSectionVisible) changeState(isRecipeSectionVisible = true)
                else if (_state.value.isProductSectionVisible) changeState(isSearchSectionVisible = true)
                else if (_state.value.isRecipeSectionVisible) navigateUp()
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
                changeState(
                    isSearchSectionVisible = true
                )
            }

            is NewRecipeEvent.ClickedProduct -> {
                _state.update {
                    it.copy(
                        selectedProduct = event.product
                    )
                }
                changeState(isProductSectionVisible = true)
                initPieChartData()
            }

            is NewRecipeEvent.EnteredSearchText -> {
                _state.update {
                    it.copy(
                        searchText = event.value
                    )
                }
            }

            is NewRecipeEvent.ClickedSearchButton -> {
                getProducts()
            }

            is NewRecipeEvent.EnteredProductWeight -> {
                _state.update {
                    it.copy(
                        productWeight = event.value
                    )
                }
                updateProductNutritionData()
            }

            is NewRecipeEvent.ClickedSaveProduct -> {
                addProductToRecipe()
            }

            is NewRecipeEvent.ChangedSelectedNutritionType -> {
                _state.update {
                    it.copy(
                        selectedNutritionType = event.value
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
                    servings = servings,
                    difficulty = selectedDifficulty,
                    recipeName = name,
                    isRecipePublic = isRecipePublic
                ).handle {
                    navigateTo(
                        destination = RecipeScreenDestination(
                            entryData = RecipeEntryData.Adding(
                                recipe = it,
                                mealName = navArguments.mealName,
                                dateTransferObject = navArguments.dateTransferObject
                            )
                        )
                    )
                }
            }
        }
    }

    private fun addProductToRecipe() {
        viewModelScope.launch(Dispatchers.Default) {
            with(_state.value) {
                selectedProduct?.let { product ->
                    productWeight.toIntOrNull()?.let { weight ->
                        val newIngredient = Ingredient(
                            weight = weight,
                            productName = product.name,
                            measurementUnit = product.measurementUnit,
                            nutritionValues = calculateProductNutritionValuesUseCase(
                                product = product,
                                weight = weight
                            ),
                            productId = product.id
                        )

                        ingredients.removeIf { it.productId == newIngredient.productId }
                        ingredients.add(newIngredient)

                        createRecipeIngredientsParams()
                        fetchPrices()
                        changeState(isRecipeSectionVisible = true)
                    }
                }
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
        _state.update {
            it.copy(
                nutritionData = it.nutritionData.copy(
                    nutritionValues = newRecipeUseCases.calculateRecipeNutritionValues(
                        servings = servings,
                        ingredients = ingredients
                    )
                )
            )
        }
    }

    private fun changeState(
        isRecipeSectionVisible: Boolean = false,
        isProductSectionVisible: Boolean = false,
        isSearchSectionVisible: Boolean = false
    ) {
        _state.update {
            it.copy(
                isRecipeSectionVisible = isRecipeSectionVisible,
                isProductSectionVisible = isProductSectionVisible,
                isSearchSectionVisible = isSearchSectionVisible
            )
        }
    }

    private fun getProducts() {
        viewModelScope.launch {
            newRecipeUseCases.searchForProductsUseCase(
                searchText = _state.value.searchText,
                page = 1
            ).handle { products ->
                _state.update {
                    it.copy(
                        searchItems = products.map { product ->
                            newRecipeUseCases.createSearchItemParamsFromProductUseCase(
                                product = product,
                                onClick = {
                                    onEvent(NewRecipeEvent.ClickedProduct(product))
                                },
                                onLongClick = {
                                    // TODO: Add on long click for product
                                }
                            )
                        }
                    )
                }
                Log.e(TAG, _state.value.searchItems.toString())
            }
        }
    }

    private fun initPieChartData() {
        _state.value.selectedProduct?.let { product ->
            _state.update {
                it.copy(
                    nutritionData = it.nutritionData.copy(
                        pieChartData = newRecipeUseCases.createPieChartDataUseCase(nutritionValues = product.nutritionValues)
                    )
                )
            }
        }
    }

    private fun updateProductNutritionData() {
        state.value.productWeight.toIntOrNull()?.let { weight ->
            _state.value.selectedProduct?.let { product ->
                _state.update {
                    it.copy(
                        nutritionData = it.nutritionData.copy(
                            nutritionValues = newRecipeUseCases.calculateProductNutritionValuesUseCase(
                                weight = weight,
                                product = product
                            ),
                        )
                    )
                }
            }
        }
    }
}