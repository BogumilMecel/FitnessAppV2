package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.bodziowaty6978.fitnessappv2.common.data.navigation.NavigationActions
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.navigation.Navigator
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
                        isLoading = true
                    )
                }
                viewModelScope.launch(Dispatchers.IO) {
                    val result =
                        searchDiaryUseCases.searchForProducts(_searchState.value.searchBarText)
                    _searchState.update {
                        it.copy(
                            errorMessage = result.uiText,
                            items = result.data ?: emptyList(),
                            isLoading = false
                        )
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
                navigator.navigate(NavigationActions.SearchScreen.searchToNewProduct(event.mealName))
            }
            is SearchEvent.ClickedScanButton -> {
                _searchState.update {
                    it.copy(
                        isScannerVisible = true,
                    )
                }
            }
            is SearchEvent.ScannedBarcode -> {
                _searchState.update {
                    it.copy(
                        isScannerVisible = false
                    )
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
}