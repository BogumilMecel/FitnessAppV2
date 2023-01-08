package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_recipe

import androidx.lifecycle.viewModelScope
import com.gmail.bodziowaty6978.fitnessappv2.common.data.navigation.NavigationActions
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.navigation.Navigator
import com.gmail.bodziowaty6978.fitnessappv2.common.util.BaseViewModel
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.Ingredient
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.new_recipe.NewRecipeUseCases
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_recipe.util.SelectedNutritionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewRecipeViewModel @Inject constructor(
    private val navigator: Navigator,
    private val newRecipeUseCases: NewRecipeUseCases
) : BaseViewModel() {

    private val _state = MutableStateFlow(NewRecipeState())
    val state: StateFlow<NewRecipeState> = _state

    init {
        _state.update {
            it.copy(
                times = listOf("15 min", "30 min", "45 min", "60+ min"),
                difficulties = listOf("1", "2", "3", "4", "5")
            )
        }
    }

    fun onEvent(event: NewRecipeEvent) {
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
                else if (_state.value.isRecipeSectionVisible) navigator.navigate(NavigationActions.General.navigateUp())
            }

            is NewRecipeEvent.SelectedDifficulty -> {
                _state.update {
                    it.copy(
                        selectedDifficulty = it.difficulties[event.index],
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
                        selectedTime = it.times[event.index].split(" ").getOrNull(0) ?: "15",
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
        }
    }

    private fun saveNewRecipe() {
        viewModelScope.launch {
            val state = _state.value
            val resource = newRecipeUseCases.addNewRecipe(
                ingredients = state.ingredients,
                time = state.selectedTime,
                servings = state.servings,
                difficulty = state.selectedDifficulty,
                recipeName = state.name,
                nutritionValues = state.nutritionData.nutritionValues
            )
            when (resource) {
                is Resource.Success -> {

                }

                is Resource.Error -> {
                    showSnackbarError(resource.uiText)
                }
            }
        }
    }

    private fun addProductToRecipe() {
        _state.value.selectedProduct?.let { product ->
            val productWeight = _state.value.productWeight.toIntOrNull()
            productWeight?.let { weight ->
                val newRecipeList = _state.value.ingredients.toMutableList()
                newRecipeList.removeIf { it.product == product }
                newRecipeList.add(
                    Ingredient(
                        weight = weight,
                        product = product
                    )
                )
                _state.update {
                    it.copy(
                        ingredients = newRecipeList
                    )
                }
                changeState(isRecipeSectionVisible = true)
            }
        }
        calculateRecipeInformation()
    }

    private fun calculateRecipeInformation() {
        viewModelScope.launch(Dispatchers.Default) {
            val servings: Int? =
                if (_state.value.selectedNutritionType is SelectedNutritionType.Recipe) 1 else _state.value.servings.toIntOrNull()
            servings?.let {
                if (servings > 0) {
                    val newPrice = newRecipeUseCases.calculateRecipePrice(
                        ingredients = _state.value.ingredients,
                        servings = servings
                    )
                    _state.update {
                        it.copy(
                            nutritionData = it.nutritionData.copy(
                                nutritionValues = newRecipeUseCases.calculateRecipeNutritionValues(
                                    servings = servings,
                                    ingredients = _state.value.ingredients
                                )
                            ),
                            recipePrice = newPrice.first,
                            shouldShowPriceWarning = newPrice.second
                        )
                    }
                }
            }
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
            val resource = newRecipeUseCases.searchForProducts(
                searchText = _state.value.searchText
            )
            when (resource) {
                is Resource.Error -> {
                    showSnackbarError(resource.uiText)
                }

                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            searchItems = resource.data
                        )
                    }
                }
            }
        }
    }

    private fun initPieChartData() {
        _state.value.selectedProduct?.let { product ->
            _state.update {
                it.copy(
                    nutritionData = it.nutritionData.copy(
                        pieEntries = newRecipeUseCases.createPieChartData(nutritionValues = product.nutritionValues)
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
                            nutritionValues = newRecipeUseCases.calculateProductNutritionValues(
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