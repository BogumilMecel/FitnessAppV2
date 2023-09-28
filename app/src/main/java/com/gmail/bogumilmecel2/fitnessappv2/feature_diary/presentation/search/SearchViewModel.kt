package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.util.ApiConstants
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BarcodeScanner
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseViewModel
import com.gmail.bogumilmecel2.fitnessappv2.destinations.NewProductScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.NewRecipeScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.ProductScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.RecipeScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.SearchScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
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
    private val barcodeScanner: BarcodeScanner,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<SearchState, SearchEvent, SearchNavArguments>(
    state = SearchState(),
    navArguments = SearchScreenDestination.argsFrom(savedStateHandle)
) {
    private val diaryHistory = mutableListOf<ProductDiaryEntry>()
    private var userRecipes = emptyList<Recipe>()
    private var userProducts = emptyList<Product>()
    private var everythingPage = 1
    private var isDataInitialized: Boolean = false
    private var isDisplayingHistory = true
    private var currentTabIndex = SearchTab.EVERYTHING.ordinal
    private var searchText: String? = null

    override fun configureOnStart() {
        _state.update {
            it.copy(
                mealName = navArguments.mealName,
                date = navArguments.dateTransferObject.displayedDate
            )
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
                        isDisplayingHistory = false
                        searchForProducts()
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
                navigateTo(
                    NewProductScreenDestination(
                        mealName = _state.value.mealName,
                        barcode = _state.value.barcode,
                        dateTransferObject = navArguments.dateTransferObject
                    )
                )
            }

            is SearchEvent.ClickedScanButton -> {
                viewModelScope.launch {
                    barcodeScanner.startScan { barcode ->
                        barcode?.let {
                            _state.update {
                                it.copy(
                                    barcode = barcode
                                )
                            }
                        }
                    }
                }
            }

            is SearchEvent.ScannedBarcode -> {
                onBarcodeScanned(event.code)
            }

            is SearchEvent.ClickedCreateNewRecipe -> {
                navigateTo(
                    NewRecipeScreenDestination(
                        mealName = navArguments.mealName,
                        dateTransferObject = navArguments.dateTransferObject
                    )
                )
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
                navigateTo(
                    RecipeScreenDestination(
                        entryData = RecipeEntryData.Adding(
                            recipe = event.recipe,
                            mealName = navArguments.mealName,
                            dateTransferObject = navArguments.dateTransferObject
                        )
                    )
                )
            }

            is SearchEvent.ReachedListEnd -> {
                handleEndOfListReached()
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
                            perPage = ApiConstants.DEFAULT_OFFLINE_PAGE_SIZE,
                            currentPage = everythingPage
                        )
                    ) {
                        everythingPage++
                        requestHistory()
                    }
                } else {
                    // TODO: implement getting pages for products
                }
            }

            SearchTab.PRODUCTS.ordinal -> {

            }

            SearchTab.RECIPES.ordinal -> {

            }
        }
    }

    private fun fetchUserRecipes() {
        viewModelScope.launch(Dispatchers.IO) {
            diaryRepository.getLocalUserRecipes(
                userId = cachedValuesProvider.getUserId()
            ).handle { recipes ->
                userRecipes = recipes
                createSearchItemParamsFromRecipeUseCase(items = recipes)
            }
        }
    }

    private fun fetchUserProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            diaryRepository.getLocalUserProducts(
                userId = cachedValuesProvider.getUserId()
            ).handle { products ->
                userProducts = products
                createMyProductsSearchItemParamsFromProductUseCase(items = products)
            }
        }
    }

    private fun requestHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            setLoader(true)
            searchDiaryUseCases.getDiaryHistoryUseCase(
                page = everythingPage,
                searchText = searchText
            ).handle(
                finally = { setLoader(false) }
            ) {
                diaryHistory.addAll(it)
                createSearchItemParamsFromHistoryItems()
            }
        }
    }

    private fun setLoader(isLoading: Boolean) {
        _state.update { it.copy(isLoading = isLoading) }
    }

    private fun handleSearchTextChange() {
        isDisplayingHistory = true
        diaryHistory.clear()
        everythingPage = 1
        requestHistory()
        filterUserRecipes()
        filterUserProducts()
    }

    private fun filterUserRecipes() {
        createSearchItemParamsFromRecipeUseCase(
            items = userRecipes.filter {
                it.name.contains(
                    other = searchText.orEmpty(),
                    ignoreCase = true
                )
            }
        )
    }

    private fun filterUserProducts() {
        createMyProductsSearchItemParamsFromProductUseCase(
            items = userProducts.filter {
                it.name.contains(
                    other = searchText.orEmpty(),
                    ignoreCase = true
                )
            }
        )
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

    private fun createEverythingSearchItemParamsFromProductUseCase(items: List<Product>) {
        _state.update {
            it.copy(
                everythingSearchItems = items.map { product ->
                    searchDiaryUseCases.createSearchItemParamsFromProductUseCase(
                        product = product,
                        onClick = { onEvent(SearchEvent.ClickedProduct(product)) },
                        onLongClick = {}
                    )
                }
            )
        }
    }

    private fun createMyProductsSearchItemParamsFromProductUseCase(items: List<Product>) {
        _state.update {
            it.copy(
                myProductsSearchItems = items.map { product ->
                    searchDiaryUseCases.createSearchItemParamsFromProductUseCase(
                        product = product,
                        onClick = { onEvent(SearchEvent.ClickedProduct(product)) },
                        onLongClick = {}
                    )
                }
            )
        }
    }

    private fun createSearchItemParamsFromRecipeUseCase(items: List<Recipe>) {
        _state.update {
            it.copy(
                myRecipesSearchItems = items.map { recipe ->
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
            setLoader(true)
            searchDiaryUseCases.getProductUseCase(
                productId = productId
            ).handle(
                finally = { setLoader(false) },
                showSnackbar = false
            ) { product ->
                product?.let {
                    navigateToProductScreen(product = it)
                }
            }
        }
    }

    private fun searchForProducts() {
        showOrHideLoader()
        everythingPage = 1
        viewModelScope.launch(Dispatchers.IO) {
            searchDiaryUseCases.searchForProductsUseCase(_state.value.searchBarText).handle(
                finally = {
                    showOrHideLoader(isLoadingVisible = false)
                }
            ) { products ->
                createEverythingSearchItemParamsFromProductUseCase(products)
            }
        }
    }

    private fun showOrHideLoader(isLoadingVisible: Boolean = true) {
        setLoader(isLoadingVisible)
        _state.update {
            it.copy(
                barcode = null
            )
        }
    }

    private fun onBarcodeScanned(barcode: String) {
        setLoader(true)
        viewModelScope.launch(Dispatchers.IO) {
            searchDiaryUseCases.searchForProductWithBarcode(barcode).handle(
                onError = { errorMessage ->
                    if (errorMessage == resourceProvider.getString(R.string.there_is_no_product_with_provided_barcode_do_you_want_to_add_it)) {
                        setLoader(false)
                        _state.update { it.copy(barcode = barcode) }
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
                                dateTransferObject = navArguments.dateTransferObject
                            ),
                        )
                    )
                }
            }
        }
    }

    private fun navigateToProductScreen(product: Product) {
        navigateTo(
            ProductScreenDestination(
                entryData = ProductEntryData.Adding(
                    product = product,
                    mealName = _state.value.mealName,
                    dateTransferObject = navArguments.dateTransferObject
                ),
            )
        )
    }
}

enum class SearchTab(val textResId: Int) {
    EVERYTHING(textResId = R.string.search_everything),
    PRODUCTS(textResId = R.string.search_my_products),
    RECIPES(textResId = R.string.search_my_recipes)
}