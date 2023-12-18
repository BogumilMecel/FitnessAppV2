package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.data.singleton.CurrentDate
import com.gmail.bodziowaty6978.fitnessappv2.common.util.BaseViewModel
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.destinations.DiaryScreenDestination
import com.gmail.bodziowaty6978.fitnessappv2.destinations.ProductScreenDestination
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.product.ProductUseCases
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.components.NutritionData
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

    private val _state = MutableStateFlow(ProductState(
        product = ProductScreenDestination.argsFrom(savedStateHandle).product.also {
            initializeProductData()
        }
    ))
    val state: StateFlow<ProductState> = _state

    init {
        savedStateHandle.get<String>("mealName")?.let { mealName ->
            _state.update {
                it.copy(
                    mealName = mealName
                )
            }
        }
        initializeProductData()
    }

    fun onEvent(event: ProductEvent) {
        when (event) {
            is ProductEvent.EnteredWeight -> {
                viewModelScope.launch(Dispatchers.Default) {
                    val enteredValue = event.value.replace(",", "").replace(".", "").toIntOrNull()

                    enteredValue?.let { newWeight ->
                        _state.update {
                            it.copy(
                                weight = newWeight.toString(),
                                nutritionData = it.nutritionData.copy(
                                    nutritionValues = productUseCases.calculateProductNutritionValues(
                                        weight = newWeight,
                                        product = _state.value.product
                                    )
                                )
                            )
                        }
                    } ?: run {
                        _state.update {
                            it.copy(
                                weight = ""
                            )
                        }
                    }
                }
            }

            is ProductEvent.ClickedAddProduct -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val addingResult = productUseCases.addDiaryEntry(
                        product = _state.value.product,
                        mealName = _state.value.mealName,
                        weight = _state.value.weight.toIntOrNull(),
                        dateModel = CurrentDate.dateModel(resourceProvider = resourceProvider)
                    )
                    if (addingResult is Resource.Success) {
                        navigateTo(
                            destination = DiaryScreenDestination,
                            navOptions = NavOptions.Builder().setPopUpTo(0, true).build()
                        )
                    } else if (addingResult is Resource.Error) {
                        showSnackbarError(addingResult.uiText)
                    }
                }
            }

            is ProductEvent.ClickedBackArrow -> {
                navigateUp()
            }

            is ProductEvent.ClickedSubmitNewPrice -> {
                val calculatedPrice = productUseCases.calculatePriceFor100g(
                    priceStringForValue = _state.value.priceForValue,
                    priceStringValue = _state.value.priceValue
                )
                viewModelScope.launch {
                    if (calculatedPrice != null) {
                        val resource = productUseCases.addNewPrice(
                            price = calculatedPrice,
                            productId = _state.value.product.id
                        )
                        when (resource) {
                            is Resource.Success -> {
                                _state.update {
                                    it.copy(
                                        product = it.product.copy(
                                            price = resource.data
                                        ),
                                        priceForValue = "",
                                        priceValue = ""
                                    )
                                }
                                showSnackbarError(resourceProvider.getString(R.string.successfully_submitted_new_price))
                            }

                            is Resource.Error -> {
                                showSnackbarError(resource.uiText)
                            }
                        }
                    } else {
                        showSnackbarError(resourceProvider.getString(R.string.please_make_sure_you_have_entered_correct_values_for_new_price))
                    }
                }
            }

            is ProductEvent.EnteredPriceFor -> {
                val enteredValue = event.value.replace(",", ".")
                _state.update {
                    it.copy(
                        priceForValue = enteredValue
                    )
                }
            }

            is ProductEvent.EnteredPriceValue -> {
                val enteredValue = event.value.replace(",", ".")
                _state.update {
                    it.copy(
                        priceValue = enteredValue
                    )
                }
            }
        }
    }

    private fun initializeProductData() {
        _state.update {
            it.copy(
                nutritionData = NutritionData(
                    nutritionValues = _state.value.product.nutritionValues,
                    pieEntries = productUseCases.createPieChartData(nutritionValues = _state.value.product.nutritionValues)
                ),
            )
        }
    }
}