package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseViewModel
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.toValidInt
import com.gmail.bogumilmecel2.fitnessappv2.destinations.DiaryScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.ProductScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.EditProductDiaryEntryUseCase
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
    private val editProductDiaryEntryUseCase: EditProductDiaryEntryUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ProductState, ProductEvent>(
    state = with(ProductScreenDestination.argsFrom(savedStateHandle)) {
        ProductState(
            weight = if (entryData is ProductEntryData.Editing) entryData.weight else "",
            date = entryData.dateTransferObject.displayedDate
        )
    }
) {

    val entryData = ProductScreenDestination.argsFrom(savedStateHandle).entryData

    init {
        initializeProductData()
        getProductPrice()
    }

    override fun onEvent(event: ProductEvent) {
        when (event) {
            is ProductEvent.EnteredWeight -> {
                viewModelScope.launch(Dispatchers.Default) {
                    event.value.toValidInt()?.let { newWeight ->
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
                            weight = event.value,
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
                                    productId = entryData.product.id,
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
                                editProductDiaryEntryUseCase(
                                    productDiaryEntryId = entryData.productDiaryEntryId,
                                    newWeightStringValue = weight
                                ).handle {
                                    navigateUp()
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

    private fun initializeProductData() {
        with(entryData.product.nutritionValues) {
            _state.update {
                it.copy(
                    nutritionData = NutritionData(
                        nutritionValues = this,
                        pieChartData = productUseCases.createPieChartData(nutritionValues = this)
                    )
                )
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