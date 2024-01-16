package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_product

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.MeasurementUnit
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BarcodeScanner
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseResultViewModel2
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.clearAndAppendText
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.getText
import com.gmail.bogumilmecel2.fitnessappv2.destinations.DiaryScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.NewProductScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.ProductScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.ProductResult
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.product.NutritionValuesIn
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.new_product.SaveNewProductUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.presentation.ProductEntryData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalFoundationApi::class)
@HiltViewModel
class NewProductViewModel @Inject constructor(
    private val saveNewProductUseCase: SaveNewProductUseCase,
    private val barcodeScanner: BarcodeScanner,
    savedStateHandle: SavedStateHandle
) : BaseResultViewModel2<NewProductEvent, ProductResult>() {
    private val navArguments = NewProductScreenDestination.argsFrom(savedStateHandle)
    private val entryData = navArguments.entryData

    val dropDownMenuExpanded = MutableStateFlow(false)
    val selectedMeasurementUnit = MutableStateFlow(MeasurementUnit.GRAMS)
    val selectedNutritionValuesIn = MutableStateFlow(NutritionValuesIn.HUNDRED_GRAMS)
    val availableMeasurementUnits = MutableStateFlow(MeasurementUnit.entries.toList())
    val nutritionValuesIn: MutableStateFlow<List<NutritionValuesIn>> = MutableStateFlow(emptyList())

    val productName = TextFieldState()
    val barcode = TextFieldState()
    val containerWeight = TextFieldState()
    val calories = TextFieldState()
    val carbohydrates = TextFieldState()
    val protein = TextFieldState()
    val fat = TextFieldState()

    override fun configureOnStart() {
        createNutritionValuesInList()
        barcode.clearAndAppendText(navArguments.barcode)
    }

    override fun onEvent(event: NewProductEvent) {
        when (event) {
            is NewProductEvent.ClickedDropDownMenu -> {
                dropDownMenuExpanded.value = !dropDownMenuExpanded.value
            }

            is NewProductEvent.ClickedBackArrow -> {
                navigateUp()
            }

            is NewProductEvent.SelectedMeasurementUnit -> {
                selectedMeasurementUnit.value = event.measurementUnit
                dropDownMenuExpanded.value = false
                createNutritionValuesInList()
            }

            is NewProductEvent.ClickedNutritionTab -> {
                selectedNutritionValuesIn.value = NutritionValuesIn.entries[event.position]
            }

            is NewProductEvent.ClickedSaveButton -> {
                viewModelScope.launch(Dispatchers.IO) {
                    loaderVisible = true
                    saveNewProductUseCase(
                        name = productName.getText(),
                        containerWeight = containerWeight.getText(),
                        nutritionValuesIn = selectedNutritionValuesIn.value,
                        measurementUnit = selectedMeasurementUnit.value,
                        barcode = barcode.getText(),
                        calories = calories.getText(),
                        carbohydrates = carbohydrates.getText(),
                        protein = protein.getText(),
                        fat = fat.getText()
                    ).handle { product ->
                        when (entryData) {
                            is NewProductEntryData.SearchArguments -> {
                                navigateWithPopUp(
                                    destination = ProductScreenDestination(
                                        entryData = ProductEntryData.Adding(
                                            mealName = entryData.mealName,
                                            product = product,
                                            date = entryData.date
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
                    loaderVisible = false
                }
            }

            is NewProductEvent.ClickedScannerButton -> {
                viewModelScope.launch {
                    barcodeScanner.startScan {
                        it?.let { barcode ->
                            this@NewProductViewModel.barcode.clearAndAppendText(barcode)
                        }
                    }
                }
            }
        }
    }

    private fun createNutritionValuesInList() {
        val selectedMeasurementUnit = selectedMeasurementUnit.value
        nutritionValuesIn.value = NutritionValuesIn.entries
            .filterNot {
                (it == NutritionValuesIn.HUNDRED_GRAMS && selectedMeasurementUnit == MeasurementUnit.MILLILITERS)
                        || (it == NutritionValuesIn.HUNDRED_MILLILITERS && selectedMeasurementUnit == MeasurementUnit.GRAMS)
            }
    }
}