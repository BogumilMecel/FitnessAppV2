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
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchDiaryUseCases: SearchDiaryUseCases,
    private val diaryRepository: DiaryRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<SearchState, SearchEvent, SearchNavArguments>(
    state = SearchState(),
    navArguments = SearchScreenDestination.argsFrom(savedStateHandle)
) {
    private val dateTransferObject =
        SearchScreenDestination.argsFrom(savedStateHandle).dateTransferObject
    private var startingProductHistory = emptyList<ProductDiaryHistoryItem>()
    private var productHistory = emptyList<ProductDiaryHistoryItem>()
    private var userRecipes = emptyList<Recipe>()
    private var userProducts = emptyList<Product>()
    private var everythingPage = 1
    private var everythingFirstRequest: Boolean = true
    private var everythingJob: Job? = null
    private var isDataInitialized: Boolean = false

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
            fetchHistory()
            isDataInitialized = true
        }
    }

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
                    SearchTab.EVERYTHING.ordinal -> {
                        searchForProducts()
                    }

                    else -> {}
                }
            }

            is SearchEvent.EnteredSearchText -> {
                _state.update {
                    it.copy(
                        searchBarText = event.text
                    )
                }
                handleSearchText(event.text)
            }

            is SearchEvent.ClickedProduct -> {
                navigateToProductScreen(event.product)
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

            is SearchEvent.ReachedListEnd -> {

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

    private fun fetchHistory() {
        requestHistory(searchText = null)
    }

    private fun requestHistory(searchText: String?) {
        everythingJob?.cancel()
        everythingJob = viewModelScope.launch {
            setLoader(true)
            if (!everythingFirstRequest) {
                delay(500)
            }
            diaryRepository.getOnlineDiaryHistory(
                page = everythingPage,
                searchText = searchText
            ).handle(
                finally = { setLoader(false) }
            ) { productHistoryItems ->
                if (everythingFirstRequest) {
                    startingProductHistory = productHistoryItems
                    everythingFirstRequest = false
                }
                productHistory = productHistoryItems
                createSearchItemParamsFromHistoryItemUseCase()
            }
        }
    }

    private fun setLoader(isLoading: Boolean) {
        _state.update { it.copy(isLoading = isLoading) }
    }

    private fun handleSearchText(searchText: String) {
        when (_state.value.selectedTabIndex) {
            SearchTab.EVERYTHING.ordinal -> {
                everythingPage = 1
                if (searchText.isEmpty()) {
                    everythingJob?.cancel()
                    setLoader(false)
                    productHistory = startingProductHistory
                    createSearchItemParamsFromHistoryItemUseCase()
                } else {
                    requestHistory(searchText)
                }
            }

            SearchTab.RECIPES.ordinal -> {
                filterUserRecipes(searchText)
            }

            SearchTab.PRODUCTS.ordinal -> {
                filterUserProducts(searchText)
            }

            else -> {}
        }
    }

    private fun filterUserRecipes(searchText: String) {
        createSearchItemParamsFromRecipeUseCase(
            items = userRecipes.filter {
                it.name.contains(
                    other = searchText,
                    ignoreCase = true
                )
            }
        )
    }

    private fun filterUserProducts(searchText: String) {
        createMyProductsSearchItemParamsFromProductUseCase(
            items = userProducts.filter {
                it.name.contains(
                    other = searchText,
                    ignoreCase = true
                )
            }
        )
    }

    private fun createSearchItemParamsFromHistoryItemUseCase() {
        _state.update {
            it.copy(
                everythingSearchItems = productHistory.map { item ->
                    searchDiaryUseCases.createSearchItemParamsFromHistoryItemUseCase(
                        productDiaryHistoryItem = item,
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
            diaryRepository.getProduct(productId = productId).handle(
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
        _state.update { it.copy(isScannerVisible = false) }
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
                                dateTransferObject = dateTransferObject
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
                    dateTransferObject = dateTransferObject
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