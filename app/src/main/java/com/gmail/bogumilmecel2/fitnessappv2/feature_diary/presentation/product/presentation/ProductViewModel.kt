package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseResultViewModel
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.toValidInt
import com.gmail.bogumilmecel2.fitnessappv2.destinations.DiaryScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.ProductScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.ProductResult
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.ProductUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.domain.model.NutritionData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productUseCases: ProductUseCases,
    savedStateHandle: SavedStateHandle
) : BaseResultViewModel<ProductState, ProductEvent, ProductNavArguments, ProductResult>(
    state = ProductState(),
    navArguments = ProductScreenDestination.argsFrom(savedStateHandle)
) {

    private val entryData = navArguments.entryData
    private var weight: String = ""

    override fun configureOnStart() {
        when (entryData) {
            is ProductEntryData.Editing -> {
                _state.update {
                    it.copy(
                        weight = entryData.productDiaryEntry.weight.toString(),
                        headerSecondaryText = entryData.displayedDate,
                        headerPrimaryText = resourceProvider.getString(stringResId = entryData.mealName.getDisplayValue())
                    )
                }
            }

            is ProductEntryData.Adding -> {
                _state.update {
                    it.copy(
                        headerSecondaryText = entryData.dateTransferObject.displayedDate,
                        headerPrimaryText = resourceProvider.getString(stringResId = entryData.mealName.getDisplayValue())
                    )
                }
            }

            is ProductEntryData.NewRecipe -> {
                _state.update {
                    it.copy(
                        headerPrimaryText = productUseCases.generateNewRecipeSearchTitleUseCase(
                            recipeName = entryData.recipeName
                        )
                    )
                }
            }
        }

        _state.update {
            it.copy(
                nutritionData = NutritionData(
                    nutritionValues = entryData.product.nutritionValues,
                    pieChartData = productUseCases.createPieChartDataUseCase(nutritionValues = entryData.product.nutritionValues)
                )
            )
        }

        getProductPrice()
    }

    override fun onEvent(event: ProductEvent) {
        when (event) {
            is ProductEvent.EnteredWeight -> {
                viewModelScope.launch(Dispatchers.Default) {
                    weight = event.value
                    weight.toValidInt()?.let { newWeight ->
                        _state.update {
                            it.copy(
                                nutritionData = it.nutritionData.copy(
                                    nutritionValues = productUseCases.calculateProductNutritionValuesUseCase(
                                        weight = newWeight,
                                        product = entryData.product
                                    )
                                )
                            )
                        }
                    }
                    _state.update {
                        it.copy(
                            weight = weight,
                        )
                    }
                }
            }

            is ProductEvent.ClickedAddProduct -> {
                viewModelScope.launch(Dispatchers.IO) {
                    with(_state.value) {
                        when (entryData) {
                            is ProductEntryData.Adding -> {
                                productUseCases.insertProductDiaryEntryUseCase(
                                    product = entryData.product,
                                    mealName = entryData.mealName,
                                    weightStringValue = weight,
                                    date = entryData.dateTransferObject.realDate
                                ).handle {
                                    navigateWithPopUp(
                                        destination = DiaryScreenDestination
                                    )
                                }
                            }

                            is ProductEntryData.Editing -> {
                                productUseCases.editProductDiaryEntryUseCase(
                                    productDiaryEntry = entryData.productDiaryEntry,
                                    product = entryData.product,
                                    newWeightStringValue = weight,
                                ).handle {
                                    navigateUp()
                                }
                            }

                            is ProductEntryData.NewRecipe -> {
                                weight.toValidInt()?.let {
                                    resultBack.send(
                                        element = ProductResult(
                                            product = entryData.product,
                                            weight = it
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            is ProductEvent.ClickedBackArrow -> {
                navigateUp()
            }

            is ProductEvent.SubmitNewPrice -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            isSubmitPriceDialogVisible = false
                        )
                    }

                    with(_state.value) {
                        productUseCases.submitNewPriceUseCase(
                            paidHowMuch = priceValue,
                            paidForWeight = priceForValue,
                            productId = entryData.product.id
                        ).handle { newPrice ->
                            _state.update {
                                it.copy(
                                    productPrice = newPrice,
                                    priceForValue = "",
                                    priceValue = ""
                                )
                            }
                            showSnackbarError(resourceProvider.getString(R.string.successfully_submitted_new_price))
                        }
                    }
                }
            }

            is ProductEvent.EnteredPriceFor -> {
                _state.update {
                    it.copy(
                        priceForValue = event.value
                    )
                }
            }

            is ProductEvent.EnteredPriceValue -> {
                _state.update {
                    it.copy(
                        priceValue = event.value
                    )
                }
            }

            is ProductEvent.DismissedSubmitNewPriceDialog -> {
                _state.update {
                    it.copy(
                        isSubmitPriceDialogVisible = false
                    )
                }
            }

            is ProductEvent.ClickedInfoPriceButton -> {

            }

            is ProductEvent.ClickedSubmitNewPrice -> {
                _state.update {
                    it.copy(
                        isSubmitPriceDialogVisible = true
                    )
                }
            }
        }
    }

    private fun getProductPrice() {
        viewModelScope.launch {
            productUseCases.getPriceUseCase(productId = entryData.product.id)
                .handle { price ->
                    _state.update {
                        it.copy(
                            productPrice = price
                        )
                    }
                }
        }
    }
}