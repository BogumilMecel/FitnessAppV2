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
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.ProductDiaryHistoryItem
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
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
    private val diaryRepository: DiaryRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<SearchState, SearchEvent>(
    state = SearchState(
        mealName = SearchScreenDestination.argsFrom(savedStateHandle).mealName,
        date = SearchScreenDestination.argsFrom(savedStateHandle).dateTransferObject.displayedDate
    )
) {
    private val dateTransferObject =
        SearchScreenDestination.argsFrom(savedStateHandle).dateTransferObject
    private var productHistory = emptyList<ProductDiaryHistoryItem>()
    private var userRecipes = emptyList<Recipe>()
    private var userProducts = emptyList<Product>()

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
                when (_state.value.selectedTabIndex) {
                    0 -> searchForProducts()
                    1 -> searchForRecipes()
                }
            }

            is SearchEvent.EnteredSearchText -> {
                _state.update {
                    it.copy(
                        searchBarText = event.text
                    )
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

            is SearchEvent.SelectedTab -> {
                _state.update {
                    it.copy(
                        selectedTabIndex = event.index
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

    fun initializeData() {
        fetchUserRecipes()
        fetchUserProducts()
    }

    private fun fetchUserRecipes() {
        viewModelScope.launch(Dispatchers.IO) {
            diaryRepository.getLocalUserRecipes().handle { recipes ->
                userRecipes = recipes
                _state.update {
                    it.copy(
                        myRecipesSearchItems = recipes.map { recipe ->
                            searchDiaryUseCases.createSearchItemParamsFromRecipeUseCase(
                                recipe = recipe,
                                onClick = { onEvent(SearchEvent.ClickedRecipe(recipe)) },
                                onLongClick = {
                                    // TODO: Add on long click action to recipe
                                }
                            )
                        }
                    )
                }
            }
        }
    }

    private fun fetchUserProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            diaryRepository.getLocalUserProducts().handle { products ->
                userProducts = products
                _state.update {
                    it.copy(
                        myProductsSearchItems = products.map { product ->
                            searchDiaryUseCases.createSearchItemParamsFromProductUseCase(
                                product = product,
                                onClick = {
                                    onEvent(SearchEvent.ClickedProduct(product))
                                },
                                onLongClick = {}
                            )
                        }
                    )
                }
            }
        }
    }

    fun initializeHistory() {
        viewModelScope.launch(Dispatchers.IO) {
//            diaryRepository.getProductDiaryHistory().handle {
//                productHistory = history
//                _state.update {
//                    it.copy(
//                        productItems = productHistory
//                    )
//                }
//            }
        }
    }

    private fun clearItemsIfHistoryIsPresent() {
//        with(_state.value) {
//            when(currentTabIndex) {
//                0 -> {
//                    if (productHistory.isNotEmpty()) {
//                        _state.update {
//                            it.copy(
//                                productItems = productHistory.filter { product ->
//                                    product.name.contains(productSearchBarText)
//                                }
//                            )
//                        }
//                    }
//                }
//                else -> {
//
//                }
//            }
//        }
    }

    private fun searchForRecipes() {
        showOrHideLoader()
        viewModelScope.launch {
            searchDiaryUseCases.searchForRecipes(_state.value.searchBarText)
                .handle(
                    finally = {
                        showOrHideLoader(isLoadingVisible = false)
                    }
                ) { recipes ->
//                    _state.update {
//                        it.copy(
//                            recipes = recipes
//                        )
//                    }
                }
        }
    }

    private fun searchForProducts() {
        showOrHideLoader()
        viewModelScope.launch(Dispatchers.IO) {
            searchDiaryUseCases.searchForProductsUseCase(_state.value.searchBarText).handle(
                finally = {
                    showOrHideLoader(isLoadingVisible = false)
                }
            ) { products ->
//                _state.update {
//                    it.copy(
//                        productItems = products
//                    )
//                }
            }
        }
    }

    private fun showOrHideLoader(isLoadingVisible: Boolean = true) {
//        _state.update {
//            it.copy(
//                isLoading = isLoadingVisible,
//                barcode = null
//            )
//        }
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
//                        _state.update {
//                            it.copy(
//                                barcode = barcode,
//                                isLoading = false
//                            )
//                        }
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

enum class SearchTab(val textResId: Int) {
    EVERYTHING(textResId = R.string.search_everything),
    PRODUCTS(textResId = R.string.search_my_products),
    RECIPES(textResId = R.string.search_my_recipes)
}