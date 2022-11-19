package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.data.navigation.NavigationActions
import com.gmail.bodziowaty6978.fitnessappv2.common.data.singleton.CurrentDate
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.navigation.Navigator
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.product.ProductUseCases
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.components.NutritionData
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
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

    private val _errorState = Channel<String>()
    val errorState = _errorState.receiveAsFlow()

    private val _state = MutableStateFlow(ProductState(
        product = savedStateHandle.get<String>("product")?.let { productString ->
            Gson().fromJson(productString, Product::class.java)
        } ?: kotlin.run {
            navigator.navigate(NavigationActions.General.navigateUp())
            Product()
        },
        recipe = savedStateHandle.get<String>("recipe")?.let { recipeString ->
            Gson().fromJson(recipeString, Recipe::class.java)
        }
    ))
    val state:StateFlow<ProductState> = _state

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
                        navigator.navigate(NavigationActions.ProductScreen.productToDiary())
                    } else if (addingResult is Resource.Error) {
                        addingResult.uiText?.let {error ->
                            _errorState.send(error)
                        }
                    }
                }
            }
            is ProductEvent.ClickedBackArrow -> {
                navigator.navigate(NavigationActions.General.navigateUp())
            }
            is ProductEvent.ClickedSubmitNewPrice -> {
                val calculatedPrice = productUseCases.calculatePriceFor100g(
                    priceStringForValue = _state.value.priceForValue,
                    priceStringValue = _state.value.priceValue
                )
                viewModelScope.launch {
                    _state.value.product.id?.let { id ->
                        if (calculatedPrice != null && _state.value.product.id != null){
                            val resource = productUseCases.addNewPrice(
                                price = calculatedPrice,
                                productId = id
                            )
                            when(resource){
                                is Resource.Success -> {
                                    resource.data?.let { newPrice ->
                                        _state.update {
                                            it.copy(
                                                product = it.product.copy(
                                                    price = newPrice
                                                ),
                                                priceForValue = "",
                                                priceValue = ""
                                            )
                                        }
                                        _errorState.send(resourceProvider.getString(R.string.successfully_submitted_new_price))
                                    }
                                }
                                is Resource.Error -> {
                                    resource.uiText?.let {
                                        _errorState.send(it)
                                    }
                                }
                            }
                        }else{
                            _errorState.send(resourceProvider.getString(R.string.please_make_sure_you_have_entered_correct_values_for_new_price))
                        }
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