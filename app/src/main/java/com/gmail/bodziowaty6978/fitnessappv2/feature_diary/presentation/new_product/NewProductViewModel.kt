package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_product

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.gmail.bodziowaty6978.fitnessappv2.common.util.BaseViewModel
import com.gmail.bodziowaty6978.fitnessappv2.destinations.NewProductScreenDestination
import com.gmail.bodziowaty6978.fitnessappv2.destinations.ProductScreenDestination
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.product.NutritionValuesIn
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.new_product.SaveNewProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewProductViewModel @Inject constructor(
    private val saveNewProduct: SaveNewProduct,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _state = MutableStateFlow(
        NewProductState(
            mealName = NewProductScreenDestination.argsFrom(savedStateHandle).mealName,
            barcode = NewProductScreenDestination.argsFrom(savedStateHandle).barcode ?: "",
        )
    )
    val state: StateFlow<NewProductState> = _state

    fun onEvent(event: NewProductEvent) {
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
                    _state.update {
                        it.copy(isLoading = true)
                    }

                    with(_state.value) {
                        saveNewProduct(
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
                            navigateWithPopUp(
                                destination = ProductScreenDestination(
                                    mealName = _state.value.mealName,
                                    product = product
                                )
                            )
                        }
                    }

                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                }
            }

            is NewProductEvent.EnteredBarcode -> {
                _state.update {
                    it.copy(
                        barcode = event.barcode,
                        isScannerVisible = false
                    )
                }
            }

            is NewProductEvent.ClickedScannerButton -> {
                _state.update {
                    it.copy(
                        isScannerVisible = true,
                    )
                }
            }

            is NewProductEvent.ClosedScanner -> {
                _state.update {
                    it.copy(
                        isScannerVisible = false
                    )
                }
            }
        }
    }
}