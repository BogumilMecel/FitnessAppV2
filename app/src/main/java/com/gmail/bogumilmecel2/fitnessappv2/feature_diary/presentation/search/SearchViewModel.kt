package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Constants
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BarcodeScanner
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseResultViewModel
import com.gmail.bogumilmecel2.fitnessappv2.destinations.NewProductScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.NewRecipeScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.ProductScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.RecipeScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.SearchScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.ProductResult
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search.SearchDiaryUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_product.NewProductEntryData
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.presentation.ProductEntryData
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.recipe.RecipeEntryData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchDiaryUseCases: SearchDiaryUseCases,
    private val barcodeScanner: BarcodeScanner,
    savedStateHandle: SavedStateHandle,
) : BaseResultViewModel<SearchState, SearchEvent, SearchNavArguments, ProductResult>(
    state = SearchState(),
    navArguments = SearchScreenDestination.argsFrom(savedStateHandle)
) {

    private val everythingProducts = mutableListOf<Product>()
    private val diaryHistory = mutableListOf<ProductDiaryEntry>()
    private var userRecipes = mutableListOf<Recipe>()
    private var userProducts = mutableListOf<Product>()
    private var everythingPage = 1
    private var userRecipesPage = 1
    private var userProductsPage = 1
    private var isDataInitialized: Boolean = false
    private var isDisplayingHistory = true
    private var currentTabIndex = SearchTab.EVERYTHING.ordinal
    private var searchText: String? = null
    private var barcode: String? = null
    private val entryData = navArguments.entryData

    override fun configureOnStart() {
        with(entryData) {
            when (this) {
                is SearchEntryData.DiaryArguments -> {
                    _state.update {
                        it.copy(
                            headerPrimaryText = resourceProvider.getString(stringResId = mealName.getDisplayValue()),
                            headerSecondaryText = dateTransferObject.displayedDate,
                        )
                    }
                }

                is SearchEntryData.NewRecipeArguments -> {
                    _state.update { state ->
                        state.copy(
                            headerPrimaryText = searchDiaryUseCases.generateNewRecipeSearchTitleUseCase(
                                recipeName = recipeName
                            ),
                            recipeTabVisible = false
                        )
                    }
                }
            }
        }

        if (!isDataInitialized) {
            fetchUserRecipes()
            fetchUserProducts()
            requestHistory()
            isDataInitialized = true
        }
    }

    override fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.ClickedBackArrow -> {
                navigateUp()
            }

            is SearchEvent.ClickedSearch -> {
                when (currentTabIndex) {
                    SearchTab.EVERYTHING.ordinal -> {
                        everythingPage = 1
                        isDisplayingHistory = false
                        fetchProducts()
                    }

                    else -> {}
                }
            }

            is SearchEvent.EnteredSearchText -> {
                searchText = event.text
                _state.update {
                    it.copy(
                        searchBarText = event.text
                    )
                }
                handleSearchTextChange()
            }

            is SearchEvent.ClickedProduct -> {
                navigateToProductScreen(event.product)
            }

            is SearchEvent.ClickedNewProduct -> {
                _state.update {
                    it.copy(noProductFoundVisible = false)
                }
                when(entryData) {
                    is SearchEntryData.DiaryArguments -> {
                        navigateTo(
                            NewProductScreenDestination(
                                entryData = NewProductEntryData.SearchArguments(
                                    mealName = entryData.mealName,
                                    dateTransferObject = entryData.dateTransferObject
                                ),
                                barcode = barcode,
                            )
                        )
                    }

                    is SearchEntryData.NewRecipeArguments -> {
                        navigateTo(
                            NewProductScreenDestination(
                                entryData = NewProductEntryData.NewRecipeArguments(
                                    recipeName = entryData.recipeName
                                ),
                                barcode = barcode,
                            )
                        )
                    }
                }
                barcode = null
            }

            is SearchEvent.ClickedScanButton -> {
                viewModelScope.launch {
                    barcodeScanner.startScan { barcode ->
                        barcode?.let {
                            this@SearchViewModel.barcode = barcode
                            onBarcodeScanned(barcode)
                        }
                    }
                }
            }

            is SearchEvent.ScannedBarcode -> {
                onBarcodeScanned(event.code)
            }

            is SearchEvent.ClickedCreateNewRecipe -> {
                if (entryData is SearchEntryData.DiaryArguments) {
                    navigateTo(
                        NewRecipeScreenDestination(
                            mealName = entryData.mealName,
                            dateTransferObject = entryData.dateTransferObject
                        )
                    )
                }
            }

            is SearchEvent.SelectedTab -> {
                currentTabIndex = event.index
                _state.update {
                    it.copy(
                        selectedTabIndex = event.index,
                        searchButtonVisible = event.index == SearchTab.EVERYTHING.ordinal
                    )
                }
            }

            is SearchEvent.ClickedRecipe -> {
                if (entryData is SearchEntryData.DiaryArguments) {
                    navigateTo(
                        RecipeScreenDestination(
                            entryData = RecipeEntryData.Adding(
                                recipe = event.recipe,
                                mealName = entryData.mealName,
                                dateTransferObject = entryData.dateTransferObject
                            )
                        )
                    )
                }
            }

            is SearchEvent.ReachedListEnd -> {
                handleEndOfListReached()
            }

            is SearchEvent.DismissedNoProductFoundDialog -> {
                barcode = null
                _state.update {
                    it.copy(
                        noProductFoundVisible = false
                    )
                }
            }

            is SearchEvent.ReceivedProductResult -> {
                viewModelScope.launch {
                    resultBack.send(event.productResult)
                }
            }
        }
    }

    private fun handleEndOfListReached() {
        when (currentTabIndex) {
            SearchTab.EVERYTHING.ordinal -> {
                if (isDisplayingHistory) {
                    if (
                        searchDiaryUseCases.shouldDisplayNextPageUseCase(
                            size = diaryHistory.size,
                            perPage = Constants.DEFAULT_OFFLINE_PAGE_SIZE,
                            currentPage = everythingPage
                        )
                    ) {
                        everythingPage++
                        requestHistory()
                    }
                } else {
                    if (
                        searchDiaryUseCases.shouldDisplayNextPageUseCase(
                            size = everythingProducts.size,
                            perPage = Constants.DEFAULT_PAGE_SIZE,
                            currentPage = everythingPage
                        )
                    ) {
                        everythingPage++
                        fetchProducts()
                    }
                }
            }

            SearchTab.PRODUCTS.ordinal -> {
                if (
                    searchDiaryUseCases.shouldDisplayNextPageUseCase(
                        size = userProducts.size,
                        perPage = Constants.DEFAULT_OFFLINE_PAGE_SIZE,
                        currentPage = userProductsPage
                    )
                ) {
                    userProductsPage++
                    fetchUserProducts()
                }
            }

            SearchTab.RECIPES.ordinal -> {
                if (
                    searchDiaryUseCases.shouldDisplayNextPageUseCase(
                        size = userRecipes.size,
                        perPage = Constants.DEFAULT_OFFLINE_PAGE_SIZE,
                        currentPage = userRecipesPage
                    )
                ) {
                    userRecipesPage++
                    fetchUserRecipes()
                }
            }
        }
    }

    private fun fetchUserRecipes() {
        viewModelScope.launch(Dispatchers.IO) {
            loaderVisible = true
            searchDiaryUseCases.getLocalUserRecipesUseCase(
                page = userRecipesPage,
                searchText = searchText
            ).handle(
                finally = { loaderVisible = false }
            ) {
                userRecipes.addAll(it)
                createSearchItemParamsFromRecipeUseCase()
            }
        }
    }

    private fun fetchUserProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            loaderVisible = true
            searchDiaryUseCases.getLocalUserProductsUseCase(
                page = userProductsPage,
                searchText = searchText
            ).handle(
                finally = { loaderVisible = false }
            ) {
                userProducts.addAll(it)
                createSearchItemParamsFromUserProductsUseCase()
            }
        }
    }

    private fun requestHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            loaderVisible = true
            searchDiaryUseCases.getDiaryHistoryUseCase(
                page = everythingPage,
                searchText = searchText
            ).handle(finally = { loaderVisible = false }) {
                diaryHistory.addAll(it)
                createSearchItemParamsFromHistoryItems()
            }
        }
    }

    private fun handleSearchTextChange() {
        everythingProducts.clear()
        userProducts.clear()
        userRecipes.clear()
        diaryHistory.clear()
        userProductsPage = 1
        userRecipesPage = 1
        everythingPage = 1
        isDisplayingHistory = true
        requestHistory()
        fetchUserProducts()
        fetchUserRecipes()
    }

    private fun createSearchItemParamsFromHistoryItems() {
        _state.update {
            it.copy(
                everythingSearchItems = diaryHistory.map { item ->
                    searchDiaryUseCases.createSearchItemParamsFromProductDiaryEntryUseCase(
                        productDiaryEntry = item,
                        onClick = { getProduct(productId = item.productId) },
                        onLongClick = {}
                    )
                }
            )
        }
    }

    private fun createEverythingSearchItemParamsFromProductUseCase() {
        _state.update {
            it.copy(
                everythingSearchItems = everythingProducts.map { product ->
                    searchDiaryUseCases.createSearchItemParamsFromProductUseCase(
                        product = product,
                        onClick = { onEvent(SearchEvent.ClickedProduct(product)) },
                        onLongClick = {}
                    )
                }
            )
        }
    }

    private fun createSearchItemParamsFromUserProductsUseCase() {
        _state.update {
            it.copy(
                myProductsSearchItems = userProducts.map { product ->
                    searchDiaryUseCases.createSearchItemParamsFromProductUseCase(
                        product = product,
                        onClick = { onEvent(SearchEvent.ClickedProduct(product)) },
                        onLongClick = {}
                    )
                }
            )
        }
    }

    private fun createSearchItemParamsFromRecipeUseCase() {
        _state.update {
            it.copy(
                myRecipesSearchItems = userRecipes.map { recipe ->
                    searchDiaryUseCases.createSearchItemParamsFromRecipeUseCase(
                        recipe = recipe,
                        onClick = { onEvent(SearchEvent.ClickedRecipe(recipe)) },
                        onLongClick = {}
                    )
                }
            )
        }
    }

    private fun getProduct(productId: String) {
        viewModelScope.launch {
            loaderVisible = true
            searchDiaryUseCases.getProductUseCase(
                productId = productId
            ).handle(
                finally = { loaderVisible = false },
                showSnackbar = false
            ) { product ->
                product?.let {
                    navigateToProductScreen(product = product)
                }
            }
        }
    }

    private fun fetchProducts() {
        loaderVisible = true
        viewModelScope.launch(Dispatchers.IO) {
            searchDiaryUseCases.searchForProductsUseCase(
                searchText = searchText,
                page = everythingPage
            ).handle(
                finally = { loaderVisible = false }
            ) { products ->
                everythingProducts.addAll(products)
                createEverythingSearchItemParamsFromProductUseCase()
            }
        }
    }

    private fun onBarcodeScanned(barcode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            loaderVisible = true
            searchDiaryUseCases.searchForProductWithBarcode(barcode).handleWithHttpCode(
                finally = { loaderVisible = false },
                onError = { _, code ->
                    if (code == Constants.HttpStatusCodes.NOT_FOUND) {
                        _state.update { it.copy(noProductFoundVisible = true) }
                    }
                }
            ) { product ->
                product?.let {
                    navigateToProductScreen(it)
                }
            }
        }
    }

    private fun navigateToProductScreen(product: Product) {
        navigateTo(
            ProductScreenDestination(
                entryData = when (entryData) {
                    is SearchEntryData.DiaryArguments -> {
                        ProductEntryData.Adding(
                            product = product,
                            mealName = entryData.mealName,
                            dateTransferObject = entryData.dateTransferObject
                        )
                    }

                    is SearchEntryData.NewRecipeArguments -> {
                        ProductEntryData.NewRecipe(
                            product = product,
                            recipeName = entryData.recipeName
                        )
                    }
                }
            )
        )
    }
}

enum class SearchTab(val textResId: Int) {
    EVERYTHING(textResId = R.string.search_everything),
    PRODUCTS(textResId = R.string.search_my_products),
    RECIPES(textResId = R.string.search_my_recipes)
}