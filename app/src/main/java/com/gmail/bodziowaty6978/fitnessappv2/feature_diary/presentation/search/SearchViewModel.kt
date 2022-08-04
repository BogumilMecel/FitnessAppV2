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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val navigator: Navigator,
    private val searchDiaryUseCases: SearchDiaryUseCases,
    private val resourceProvider: ResourceProvider,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _searchState = MutableStateFlow<SearchState>(SearchState())
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
                navigator.navigate(NavigationActions.General.navigateUp())
            }
            is SearchEvent.ClickedSearch -> {
                _searchState.update {
                    it.copy(
                        isLoading = true,
                        barcode = null
                    )
                }
                viewModelScope.launch(Dispatchers.IO) {
                    val result =
                        searchDiaryUseCases.searchForProducts(_searchState.value.searchBarText)
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
            is SearchEvent.EnteredSearchText -> {
                _searchState.update {
                    it.copy(
                        searchBarText = event.text
                    )
                }
            }
            is SearchEvent.ClickedSearchItem -> {
                navigator.navigate(
                    NavigationActions.SearchScreen.searchToProduct(
                        productWithId = event.item,
                        mealName = event.mealName
                    )
                )
            }
            is SearchEvent.ClickedNewProduct -> {
                savedStateHandle.get<String>("mealName")?.let {
                    navigator.navigate(
                        NavigationActions.SearchScreen.searchToNewProduct(
                            mealName = it,
                            barcode = _searchState.value.barcode
                        )
                    )
                }?: onError(resourceProvider.getString(R.string.unknown_error))

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
                _searchState.update {
                    it.copy(
                        isScannerVisible = false,
                        isLoading = true
                    )
                }
                viewModelScope.launch(Dispatchers.IO) {
                    val resource = searchDiaryUseCases.searchForProductWithBarcode(event.code)
                    if (resource is Resource.Error) {
                        if (resource.uiText == resourceProvider.getString(R.string.there_is_no_product_with_provided_barcode_do_you_want_to_add_it)) {
                            _searchState.update {
                                it.copy(
                                    barcode = event.code
                                )
                            }
                        } else {
                            onError(resource.uiText)
                        }
                    } else {
                        savedStateHandle.get<String>("mealName")?.let { mealName ->
                            resource.data?.let { productWithId ->
                                navigator.navigate(
                                    NavigationActions.SearchScreen.searchToProduct(
                                        productWithId = productWithId,
                                        mealName = mealName
                                    )
                                )
                            } ?: onError(resourceProvider.getString(R.string.unknown_error))
                        } ?: onError(resourceProvider.getString(R.string.unknown_error))
                    }
                }
            }
            is SearchEvent.ShowedPermissionDialog -> {
                _searchState.update {
                    it.copy(
                        hasPermissionDialogBeenShowed = true
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

    private fun onError(errorMessage: String?) {
        _searchState.update {
            it.copy(
                isLoading = false,
                errorMessage = errorMessage
            )
        }
        _searchState.update {
            it.copy(
                lastErrorMessage = errorMessage
            )
        }
    }
}