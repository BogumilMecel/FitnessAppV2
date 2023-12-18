package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.data.singleton.CurrentDate
import com.gmail.bodziowaty6978.fitnessappv2.common.util.BaseViewModel
import com.gmail.bodziowaty6978.fitnessappv2.common.util.extensions.toValidInt
import com.gmail.bodziowaty6978.fitnessappv2.destinations.DiaryScreenDestination
import com.gmail.bodziowaty6978.fitnessappv2.destinations.ProductScreenDestination
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.product.ProductUseCases
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.domain.model.NutritionData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productUseCases: ProductUseCases,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _state = MutableStateFlow(
        ProductState(
            product = ProductScreenDestination.argsFrom(savedStateHandle).product,
            mealName = ProductScreenDestination.argsFrom(savedStateHandle).mealName
        )
    )
    val state: StateFlow<ProductState> = _state

    init {
        initializeProductData(product = _state.value.product)
    }

    fun onEvent(event: ProductEvent) {
        when (event) {
            is ProductEvent.EnteredWeight -> {
                viewModelScope.launch(Dispatchers.Default) {
                    event.value.toValidInt()?.let { newWeight ->
                        _state.update {
                            it.copy(
                                nutritionData = it.nutritionData.copy(
                                    nutritionValues = productUseCases.calculateProductNutritionValues(
                                        weight = newWeight,
                                        product = _state.value.product
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
                        productUseCases.addDiaryEntry(
                            product = product,
                            mealName = mealName,
                            weight = weight.toIntOrNull(),
                            dateModel = CurrentDate.dateModel(resourceProvider = resourceProvider)
                        ).handle {
                            navigateWithPopUp(
                                destination = DiaryScreenDestination
                            )
                        }
                    }
                }
            }

            is ProductEvent.ClickedBackArrow -> {
                navigateUp()
            }

            is ProductEvent.ClickedSubmitNewPrice -> {
                viewModelScope.launch {
                    with(_state.value) {
                        productUseCases.submitNewPriceUseCase(
                            paidHowMuch = priceValue,
                            paidForWeight = priceForValue,
                            productId = product.id
                        ).handle { newPrice ->
                            _state.update {
                                it.copy(
                                    product = it.product.copy(
                                        price = newPrice
                                    ),
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
        }
    }

    private fun initializeProductData(product: Product) {
        _state.update {
            it.copy(
                nutritionData = NutritionData(
                    nutritionValues = product.nutritionValues,
                    pieChartData = productUseCases.createPieChartData(nutritionValues = product.nutritionValues)
                ),
            )
        }
    }
}