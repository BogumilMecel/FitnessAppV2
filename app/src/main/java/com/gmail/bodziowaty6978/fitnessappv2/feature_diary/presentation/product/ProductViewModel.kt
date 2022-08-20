package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.bodziowaty6978.fitnessappv2.common.data.navigation.NavigationActions
import com.gmail.bodziowaty6978.fitnessappv2.common.data.singleton.CurrentDate
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.navigation.Navigator
import com.gmail.bodziowaty6978.fitnessappv2.common.util.CustomResult
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product
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
    private val navigator: Navigator,
    private val productUseCases: ProductUseCases,
    private val resourceProvider: ResourceProvider,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(ProductState())
    val state:StateFlow<ProductState> = _state


    init {
        savedStateHandle.get<String>("mealName")?.let { mealName ->
            _state.update {
                it.copy(
                    mealName = mealName
                )
            }
        }
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
                                    nutritionValues = productUseCases.calculateNutritionValues(
                                        weight = newWeight,
                                        product = event.product
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
                        productWithId = event.productWithId,
                        mealName = _state.value.mealName,
                        weight = _state.value.weight.toIntOrNull(),
                        dateModel = CurrentDate.dateModel(resourceProvider = resourceProvider),
                        nutritionValues = _state.value.nutritionData.nutritionValues
                    )
                    if (addingResult is CustomResult.Success) {
                        productUseCases.saveProductToHistory(productWithId = event.productWithId)
                        navigator.navigate(NavigationActions.ProductScreen.productToDiary())
                    } else if (addingResult is CustomResult.Error) {
                        _state.update {
                            it.copy(
                                errorMessage = addingResult.message
                            )
                        }
                        _state.update {
                            it.copy(
                                lastErrorMessage = addingResult.message
                            )
                        }
                    }
                }
            }
            is ProductEvent.ClickedBackArrow -> {
                navigator.navigate(NavigationActions.General.navigateUp())
            }
        }
    }

    fun initializeNutritionData(product: Product) {
        _state.update {
            it.copy(
                nutritionData = NutritionData(
                    nutritionValues = product.nutritionValues,
                    pieEntries = productUseCases.createPieChartData(product = product)
                )
            )
        }
    }
}