package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.data.singleton.CurrentDate
import com.gmail.bodziowaty6978.fitnessappv2.common.util.BaseViewModel
import com.gmail.bodziowaty6978.fitnessappv2.common.util.extensions.toValidInt
import com.gmail.bodziowaty6978.fitnessappv2.destinations.DiaryScreenDestination
import com.gmail.bodziowaty6978.fitnessappv2.destinations.ProductScreenDestination
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
        initializeProductData()
        getProductPrice()
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
                            productId = product.id
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
        with(_state.value.product.nutritionValues) {
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

    private fun getProductPrice(){
        viewModelScope.launch {
            productUseCases.getPriceUseCase(productId = _state.value.product.id).handle { price ->
                _state.update {
                    it.copy(
                        productPrice = price
                    )
                }
            }
        }
    }
}