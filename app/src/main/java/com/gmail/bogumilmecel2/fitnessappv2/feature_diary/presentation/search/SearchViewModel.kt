package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseViewModel
import com.gmail.bogumilmecel2.fitnessappv2.destinations.NewProductScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.NewRecipeScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.ProductScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.RecipeScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.SearchScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search.SearchDiaryUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.presentation.ProductEntryData
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

    private var productHistory = emptyList<Product>()

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
                    when (searchState.value.currentTabIndex) {
                        0 -> it.copy(
                            productSearchBarText = event.text
                        )

                        else -> it.copy(
                            recipesSearchBarText = event.text
                        )
                    }
                }
                clearItemsIfHistoryIsPresent()
            }

            is SearchEvent.ClickedProduct -> {
                navigateTo(
                    ProductScreenDestination(
                        product = event.product,
                        mealName = _searchState.value.mealName,
                        entryData = ProductEntryData.Adding,
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
            searchDiaryUseCases.getDiaryHistory().handle { history ->
                productHistory = history
                _searchState.update {
                    it.copy(
                        productItems = productHistory
                    )
                }
            }
        }
    }

    private fun clearItemsIfHistoryIsPresent() {
        with(_searchState.value) {
            when(currentTabIndex) {
                0 -> {
                    if (productHistory.isNotEmpty()) {
                        _searchState.update {
                            it.copy(
                                productItems = productHistory.filter { product ->
                                    product.name.contains(productSearchBarText)
                                }
                            )
                        }
                    }
                }
                else -> {

                }
            }
        }
    }

    private fun searchForRecipes() {
        showOrHideLoader()
        viewModelScope.launch {
            searchDiaryUseCases.searchForRecipes(_searchState.value.recipesSearchBarText)
                .handle(
                    finally = {
                        showOrHideLoader(isLoadingVisible = false)
                    }
                ) { recipes ->
                    _searchState.update {
                        it.copy(
                            recipes = recipes
                        )
                    }
                }
        }
    }

    private fun searchForProducts() {
        showOrHideLoader()
        viewModelScope.launch(Dispatchers.IO) {
            searchDiaryUseCases.searchForProducts(_searchState.value.productSearchBarText).handle(
                finally = {
                    showOrHideLoader(isLoadingVisible = false)
                }
            ) { products ->
                _searchState.update {
                    it.copy(
                        productItems = products
                    )
                }
            }
        }
    }

    private fun showOrHideLoader(isLoadingVisible: Boolean = true) {
        _searchState.update {
            it.copy(
                isLoading = isLoadingVisible,
                barcode = null
            )
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
            searchDiaryUseCases.searchForProductWithBarcode(barcode).handle(
                onError = { errorMessage ->
                    if (errorMessage == resourceProvider.getString(R.string.there_is_no_product_with_provided_barcode_do_you_want_to_add_it)) {
                        _searchState.update {
                            it.copy(
                                barcode = barcode,
                                isLoading = false
                            )
                        }
                    } else {
                        showSnackbarError(errorMessage)
                    }
                }
            ) { product ->
                product?.let {
                    navigate(
                        ProductScreenDestination(
                            product = product,
                            mealName = _searchState.value.mealName,
                            entryData = ProductEntryData.Adding,
                        )
                    )
                }
            }
        }
    }
}