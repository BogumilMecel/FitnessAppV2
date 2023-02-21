package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.util.BaseViewModel
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.destinations.NewProductScreenDestination
import com.gmail.bodziowaty6978.fitnessappv2.destinations.NewRecipeScreenDestination
import com.gmail.bodziowaty6978.fitnessappv2.destinations.ProductScreenDestination
import com.gmail.bodziowaty6978.fitnessappv2.destinations.RecipeScreenDestination
import com.gmail.bodziowaty6978.fitnessappv2.destinations.SearchScreenDestination
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.search.SearchDiaryUseCases
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator.navigate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchDiaryUseCases: SearchDiaryUseCases,
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _searchState = MutableStateFlow(
        SearchState(
            mealName = SearchScreenDestination.argsFrom(savedStateHandle).mealName
        )
    )
    val searchState: StateFlow<SearchState> = _searchState

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.ClosedScanner -> {
                _searchState.update {
                    it.copy(
                        isScannerVisible = false
                    )
                }
            }

            is SearchEvent.ClickedBackArrow -> {
                navigateUp()
            }

            is SearchEvent.ClickedSearch -> {
                when (searchState.value.currentTabIndex) {
                    0 -> searchForProducts()
                    1 -> searchForRecipes()
                }
            }

            is SearchEvent.EnteredSearchText -> {
                _searchState.update {
                    val state = when (searchState.value.currentTabIndex) {
                        0 -> it.copy(
                            productSearchBarText = event.text
                        )

                        else -> it.copy(
                            recipesSearchBarText = event.text
                        )
                    }
                    state
                }
            }

            is SearchEvent.ClickedProduct -> {
                navigateTo(
                    ProductScreenDestination(
                        product = event.product,
                        mealName = _searchState.value.mealName
                    )
                )
            }

            is SearchEvent.ClickedNewProduct -> {
                    navigateTo(
                        NewProductScreenDestination(
                            mealName = _searchState.value.mealName,
                            barcode = _searchState.value.barcode
                        )
                    )
            }

            is SearchEvent.ClickedScanButton -> {
                _searchState.update {
                    it.copy(
                        isScannerVisible = true,
                        barcode = null
                    )
                }
            }

            is SearchEvent.ScannedBarcode -> {
                onBarcodeScanned(event.code)
            }

            is SearchEvent.ShowedPermissionDialog -> {
                _searchState.update {
                    it.copy(
                        hasPermissionDialogBeenShowed = true
                    )
                }
            }

            is SearchEvent.ClickedCreateNewRecipe -> {
                navigateTo(NewRecipeScreenDestination(mealName = _searchState.value.mealName))
            }

            is SearchEvent.ClickedProductsTab -> {
                _searchState.update {
                    it.copy(
                        searchBarPlaceholderText = resourceProvider.getString(R.string.product_name),
                        currentTabIndex = 0
                    )
                }
            }

            is SearchEvent.ClickedRecipesTab -> {
                _searchState.update {
                    it.copy(
                        searchBarPlaceholderText = resourceProvider.getString(R.string.recipe_name),
                        currentTabIndex = 1
                    )
                }
            }

            is SearchEvent.ClickedRecipe -> {
                navigateTo(
                    RecipeScreenDestination(
                        recipe = event.recipe,
                        mealName = _searchState.value.mealName
                    )
                )
            }
        }
    }

    fun initializeHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            val history = searchDiaryUseCases.getDiaryHistory()
            _searchState.update {
                it.copy(
                    items = history.data ?: emptyList()
                )
            }
        }
    }

    private fun searchForRecipes() {
        _searchState.update {
            it.copy(
                isLoading = true,
                barcode = null
            )
        }

        viewModelScope.launch {
            val result =
                searchDiaryUseCases.searchForRecipes(_searchState.value.recipesSearchBarText)
            if (result is Resource.Error) {
                showSnackbarError(result.uiText)
            } else {
                _searchState.update {
                    it.copy(
                        recipes = result.data ?: emptyList()
                    )
                }
            }
        }
    }

    private fun searchForProducts() {
        _searchState.update {
            it.copy(
                isLoading = true,
                barcode = null
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            val result =
                searchDiaryUseCases.searchForProducts(_searchState.value.productSearchBarText)
            if (result is Resource.Error) {
                showSnackbarError(result.uiText)
            } else {
                _searchState.update {
                    it.copy(
                        items = result.data ?: emptyList(),
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun onBarcodeScanned(barcode: String) {
        _searchState.update {
            it.copy(
                isScannerVisible = false,
                isLoading = true
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            val resource = searchDiaryUseCases.searchForProductWithBarcode(barcode)
            if (resource is Resource.Error) {
                if (resource.uiText == resourceProvider.getString(R.string.there_is_no_product_with_provided_barcode_do_you_want_to_add_it)) {
                    _searchState.update {
                        it.copy(
                            barcode = barcode,
                            isLoading = false
                        )
                    }
                } else {
                    showSnackbarError(resource.uiText)
                }
            } else {
                resource.data?.let { product ->
                    navigate(
                        ProductScreenDestination(
                            product = product,
                            mealName = _searchState.value.mealName
                        )
                    )
                }
            }
        }
    }
}