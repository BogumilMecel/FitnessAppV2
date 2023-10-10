package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_product

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BarcodeScanner
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseResultViewModel
import com.gmail.bogumilmecel2.fitnessappv2.destinations.DiaryScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.NewProductScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.ProductScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.ProductResult
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.product.NutritionValuesIn
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.new_product.SaveNewProductUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.presentation.ProductEntryData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewProductViewModel @Inject constructor(
    private val saveNewProductUseCase: SaveNewProductUseCase,
    private val barcodeScanner: BarcodeScanner,
    savedStateHandle: SavedStateHandle
) : BaseResultViewModel<NewProductState, NewProductEvent, NewProductNavArguments, ProductResult>(
    state = NewProductState(),
    navArguments = NewProductScreenDestination.argsFrom(savedStateHandle)
) {

    private val entryData = navArguments.entryData

    override fun configureOnStart() {
        _state.update {
            it.copy(
                barcode = navArguments.barcode ?: "",
            )
        }
    }

    override fun onEvent(event: NewProductEvent) {
        when (event) {
            is NewProductEvent.ClickedDropDownMenu -> {
                _state.update {
                    it.copy(
                        isDropDownMenuExpanded = !it.isDropDownMenuExpanded
                    )
                }
            }

            is NewProductEvent.ClickedBackArrow -> {
                navigateUp()
            }

            is NewProductEvent.EnteredProductName -> {
                _state.update {
                    it.copy(
                        productName = event.value
                    )
                }
            }

            is NewProductEvent.SelectedMeasurementUnit -> {
                _state.update {
                    it.copy(
                        selectedMeasurementUnit = event.measurementUnit,
                        isDropDownMenuExpanded = false
                    )
                }
            }

            is NewProductEvent.ClickedNutritionTab -> {
                _state.update {
                    it.copy(
                        nutritionValuesInSelectedTabIndex = event.position
                    )
                }
            }

            is NewProductEvent.EnteredCalories -> {
                _state.update {
                    it.copy(
                        calories = event.calories
                    )
                }
            }

            is NewProductEvent.EnteredCarbohydrates -> {
                _state.update {
                    it.copy(
                        carbohydrates = event.carbohydrates
                    )
                }
            }

            is NewProductEvent.EnteredProtein -> {
                _state.update {
                    it.copy(
                        protein = event.protein
                    )
                }
            }

            is NewProductEvent.EnteredFat -> {
                _state.update {
                    it.copy(
                        fat = event.fat
                    )
                }
            }

            is NewProductEvent.EnteredContainerWeight -> {
                _state.update {
                    it.copy(
                        containerWeightValue = event.containerWeight
                    )
                }
            }

            is NewProductEvent.ClickedSaveButton -> {
                viewModelScope.launch(Dispatchers.IO) {
                    loaderVisible = true
                    with(_state.value) {
                        saveNewProductUseCase(
                            name = productName,
                            containerWeight = containerWeightValue,
                            nutritionValuesIn = NutritionValuesIn.getValueFromIndex(nutritionValuesInSelectedTabIndex),
                            measurementUnit = selectedMeasurementUnit,
                            barcode = barcode,
                            calories = calories,
                            carbohydrates = carbohydrates,
                            protein = protein,
                            fat = fat
                        ).handle { product ->
                            when(entryData) {
                                is NewProductEntryData.SearchArguments -> {
                                    navigateWithPopUp(
                                        destination = ProductScreenDestination(
                                            entryData = ProductEntryData.Adding(
                                                mealName = entryData.mealName,
                                                product = product,
                                                dateTransferObject = entryData.dateTransferObject
                                            ),
                                        ),
                                        popUpTo = DiaryScreenDestination.route
                                    )
                                }

                                is NewProductEntryData.NewRecipeArguments -> {
                                    navigateWithPopUp(
                                        destination = ProductScreenDestination(
                                            entryData = ProductEntryData.NewRecipe(
                                                recipeName = entryData.recipeName,
                                                product = product,
                                            ),
                                        ),
                                        popUpTo = NewProductScreenDestination.route
                                    )
                                }
                            }

                        }
                    }
                    loaderVisible = false
                }
            }

            is NewProductEvent.EnteredBarcode -> {
                _state.update { it.copy(barcode = event.barcode) }
            }

            is NewProductEvent.ClickedScannerButton -> {
                viewModelScope.launch {
                    barcodeScanner.startScan { barcode ->
                        barcode?.let {
                            _state.update {
                                it.copy(
                                    barcode = barcode
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}