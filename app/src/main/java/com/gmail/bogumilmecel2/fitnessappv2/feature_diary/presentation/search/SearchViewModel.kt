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
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.recipe.RecipeEntryData
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator.navigate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchDiaryUseCases: SearchDiaryUseCases,
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel<SearchState, SearchEvent>(
    state = SearchState(
        mealName = SearchScreenDestination.argsFrom(savedStateHandle).mealName,
        date = SearchScreenDestination.argsFrom(savedStateHandle).dateTransferObject.displayedDate
    )
) {
    private val dateTransferObject = SearchScreenDestination.argsFrom(savedStateHandle).dateTransferObject
    private var productHistory = emptyList<Product>()

    override fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.ClosedScanner -> {
                _state.update {
                    it.copy(
                        isScannerVisible = false
                    )
                }
            }

            is SearchEvent.ClickedBackArrow -> {
                navigateUp()
            }

            is SearchEvent.ClickedSearch -> {
                when (_state.value.currentTabIndex) {
                    0 -> searchForProducts()
                    1 -> searchForRecipes()
                }
            }

            is SearchEvent.EnteredSearchText -> {
                _state.update {
                    when (_state.value.currentTabIndex) {
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
                        entryData = ProductEntryData.Adding(
                            product = event.product,
                            mealName = _state.value.mealName,
                            dateTransferObject = dateTransferObject
                        ),
                    )
                )
            }

            is SearchEvent.ClickedNewProduct -> {
                navigateTo(
                    NewProductScreenDestination(
                        mealName = _state.value.mealName,
                        barcode = _state.value.barcode,
                        dateTransferObject = dateTransferObject
                    )
                )
            }

            is SearchEvent.ClickedScanButton -> {
                _state.update {
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
                _state.update {
                    it.copy(
                        hasPermissionDialogBeenShowed = true
                    )
                }
            }

            is SearchEvent.ClickedCreateNewRecipe -> {
                with(_state.value) {
                    navigateTo(
                        NewRecipeScreenDestination(
                            mealName = mealName,
                            dateTransferObject = dateTransferObject
                        )
                    )
                }
            }

            is SearchEvent.ClickedProductsTab -> {
                _state.update {
                    it.copy(
                        searchBarPlaceholderText = resourceProvider.getString(R.string.product_name),
                        currentTabIndex = 0
                    )
                }
            }

            is SearchEvent.ClickedRecipesTab -> {
                _state.update {
                    it.copy(
                        searchBarPlaceholderText = resourceProvider.getString(R.string.recipe_name),
                        currentTabIndex = 1
                    )
                }
            }

            is SearchEvent.ClickedRecipe -> {
                navigateTo(
                    RecipeScreenDestination(
                        entryData = RecipeEntryData.Adding(
                            recipe = event.recipe,
                            mealName = _state.value.mealName,
                            dateTransferObject = dateTransferObject
                        )
                    )
                )
            }
        }
    }

    fun initializeHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            searchDiaryUseCases.getDiaryHistory().handle { history ->
                productHistory = history
                _state.update {
                    it.copy(
                        productItems = productHistory
                    )
                }
            }
        }
    }

    private fun clearItemsIfHistoryIsPresent() {
        with(_state.value) {
            when(currentTabIndex) {
                0 -> {
                    if (productHistory.isNotEmpty()) {
                        _state.update {
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
            searchDiaryUseCases.searchForRecipes(_state.value.recipesSearchBarText)
                .handle(
                    finally = {
                        showOrHideLoader(isLoadingVisible = false)
                    }
                ) { recipes ->
                    _state.update {
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
            searchDiaryUseCases.searchForProductsUseCase(_state.value.productSearchBarText).handle(
                finally = {
                    showOrHideLoader(isLoadingVisible = false)
                }
            ) { products ->
                _state.update {
                    it.copy(
                        productItems = products
                    )
                }
            }
        }
    }

    private fun showOrHideLoader(isLoadingVisible: Boolean = true) {
        _state.update {
            it.copy(
                isLoading = isLoadingVisible,
                barcode = null
            )
        }
    }

    private fun onBarcodeScanned(barcode: String) {
        _state.update {
            it.copy(
                isScannerVisible = false,
                isLoading = true
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            searchDiaryUseCases.searchForProductWithBarcode(barcode).handle(
                onError = { errorMessage ->
                    if (errorMessage == resourceProvider.getString(R.string.there_is_no_product_with_provided_barcode_do_you_want_to_add_it)) {
                        _state.update {
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
                            entryData = ProductEntryData.Adding(
                                product = product,
                                mealName = _state.value.mealName,
                                dateTransferObject = dateTransferObject
                            ),
                        )
                    )
                }
            }
        }
    }
}