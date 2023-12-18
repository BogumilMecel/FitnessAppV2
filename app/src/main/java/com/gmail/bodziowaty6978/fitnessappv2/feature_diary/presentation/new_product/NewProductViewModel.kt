package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_product

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.data.navigation.NavigationActions
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.navigation.Navigator
import com.gmail.bodziowaty6978.fitnessappv2.common.util.CustomResult
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
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
    private val navigator: Navigator,
    private val resourceProvider: ResourceProvider,
    private val saveNewProduct: SaveNewProduct
) : ViewModel() {

    private val _state = MutableStateFlow(
        NewProductState(
            containerWeight = resourceProvider.getString(R.string.container_weight),
            dropDownItems = resourceProvider.getStringArray(R.array.units)
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
                _state.update {
                    it.copy(
                        dropDownMenuImageVector = if (_state.value.isDropDownMenuExpanded) {
                            Icons.Default.ArrowDropUp
                        } else {
                            Icons.Default.ArrowDropDown
                        }
                    )
                }
            }
            is NewProductEvent.ClickedBackArrow -> {
                navigator.navigate(NavigationActions.General.navigateUp())
            }
            is NewProductEvent.EnteredProductName -> {
                _state.update {
                    it.copy(
                        productName = event.value
                    )
                }
            }
            is NewProductEvent.ClickedDropDownMenuItem -> {
                _state.update {
                    it.copy(
                        dropDownSelectedIndex = event.position,
                        isDropDownMenuExpanded = false
                    )
                }
            }
            is NewProductEvent.ClickedNutritionTab -> {
                _state.update {
                    it.copy(
                        nutritionSelectedTabIndex = event.position
                    )
                }
                when (event.position) {
                    0 -> {
                        _state.update {
                            it.copy(
                                containerWeight = resourceProvider.getString(R.string.container_weight)
                            )
                        }
                    }

                    1 -> {
                        _state.update {
                            it.copy(
                                containerWeight = resourceProvider.getString(R.string.container_weight) + "*"
                            )
                        }
                    }

                    2 -> {
                        _state.update {
                            it.copy(
                                containerWeight = resourceProvider.getString(R.string.average_weight) + "*"
                            )
                        }
                    }
                }
            }
            is NewProductEvent.EnteredCalories -> {
                _state.update {
                    it.copy(
                        calories = event.calories.replace(",", "").replace(".", "")
                    )
                }
            }
            is NewProductEvent.EnteredCarbohydrates -> {
                _state.update {
                    it.copy(
                        carbohydrates = event.carbohydrates.replace(",", ".")
                    )
                }
            }

            is NewProductEvent.EnteredProtein -> {
                _state.update {
                    it.copy(
                        protein = event.protein.replace(",", ".")
                    )
                }
            }

            is NewProductEvent.EnteredFat -> {
                _state.update {
                    it.copy(
                        fat = event.fat.replace(",", ".")
                    )
                }
            }

            is NewProductEvent.EnteredContainerWeight -> {
                _state.update {
                    it.copy(
                        containerWeightValue = event.containerWeight.replace(",",".")
                    )
                }
            }

            is NewProductEvent.ClickedSaveButton -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _state.update {
                        it.copy(isLoading = true)
                    }

                    val state = _state.value
                    val result = saveNewProduct(
                        name = state.productName,
                        containerWeight = state.containerWeightValue,
                        position = state.nutritionSelectedTabIndex,
                        unit = state.dropDownItems[state.dropDownSelectedIndex],
                        barcode = state.barcode,
                        calories = state.calories,
                        carbohydrates = state.carbohydrates,
                        protein = state.protein,
                        fat = state.fat,
                    )

                    if(result is CustomResult.Error){
                        onError(result.message)
                    }else{
                        // TODO: On successful result
                    }
                }
            }
            is NewProductEvent.EnteredBarcode -> {
                _state.update {
                    it.copy(
                        barcode = event.barcode.replace(".","").replace(",",""),
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

    private fun onError(message:String){
        _state.update {
            it.copy(
                isLoading = false,
                errorMessage = message
            )
        }

        _state.update {
            it.copy(
                lastErrorMessage = message
            )
        }
    }

}