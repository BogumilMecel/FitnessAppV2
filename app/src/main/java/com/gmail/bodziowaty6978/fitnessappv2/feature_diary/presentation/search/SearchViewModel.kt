package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.data.navigation.NavigationActions
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.navigation.Navigator
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.search.SearchDiaryUseCases
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
class SearchViewModel @Inject constructor(
    private val navigator: Navigator,
    private val searchDiaryUseCases: SearchDiaryUseCases,
    private val resourceProvider: ResourceProvider,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _errorState = Channel<String>()
    val errorState = _errorState.receiveAsFlow()

    private val _searchState = MutableStateFlow(
        SearchState(
            searchBarPlaceholderText = resourceProvider.getString(R.string.products)
        )
    )
    val searchState: StateFlow<SearchState> = _searchState

    init {
        savedStateHandle.get<String>("mealName")?.let { mealName ->
            _searchState.update {
                it.copy(
                    mealName = mealName
                )
            }
        }
    }

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
                navigator.navigate(NavigationActions.General.navigateUp())
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

            is SearchEvent.ClickedSearchItem -> {
                savedStateHandle.get<String>("mealName")?.let { mealName ->
                    navigator.navigate(
                        NavigationActions.SearchScreen.searchToProduct(
                            product = event.item,
                            mealName = mealName
                        )
                    )
                } ?: onError(resourceProvider.getString(R.string.unknown_error))
            }

            is SearchEvent.ClickedNewProduct -> {
                savedStateHandle.get<String>("mealName")?.let {
                    navigator.navigate(
                        NavigationActions.SearchScreen.searchToNewProduct(
                            mealName = it,
                            barcode = _searchState.value.barcode
                        )
                    )
                } ?: onError(resourceProvider.getString(R.string.unknown_error))


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
                navigator.navigate(NavigationActions.SearchScreen.searchToNewRecipe())
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
                onError(result.uiText)
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
                onError(result.uiText)
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
                    onError(resource.uiText)
                }
            } else {
                savedStateHandle.get<String>("mealName")?.let { mealName ->
                    resource.data?.let { product ->
                        navigator.navigate(
                            NavigationActions.SearchScreen.searchToProduct(
                                product = product,
                                mealName = mealName,
                            )
                        )
                    } ?: onError(resourceProvider.getString(R.string.unknown_error))
                } ?: onError(resourceProvider.getString(R.string.unknown_error))
            }
        }
    }

    private fun onError(errorMessage: String?) {
        if (errorMessage != null) {
            viewModelScope.launch {
                _errorState.send(errorMessage)
            }
        }
    }
}