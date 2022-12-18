package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.diary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.bodziowaty6978.fitnessappv2.FitnessApp
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.data.navigation.NavigationActions
import com.gmail.bodziowaty6978.fitnessappv2.common.data.singleton.CurrentDate
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.navigation.Navigator
import com.gmail.bodziowaty6978.fitnessappv2.common.util.CustomResult
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Meal
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.diary.DeleteDiaryEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.diary.GetDiaryEntries
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.diary.UpdateDiaryEntriesListAfterDelete
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
class DiaryViewModel @Inject constructor(
    private val getDiaryEntries: GetDiaryEntries,
    private val deleteDiaryEntry: DeleteDiaryEntry,
    private val updateDiaryEntriesListAfterDelete: UpdateDiaryEntriesListAfterDelete,
    private val resourceProvider: ResourceProvider,
    private val navigator: Navigator,
) : ViewModel() {

    private val _errorState = Channel<String>()
    val errorState = _errorState.receiveAsFlow()

    private val _state = MutableStateFlow(DiaryState())
    val state: StateFlow<DiaryState> = _state

    init {
        initMeals()
        getDiaryEntries()
        initWantedNutritionValues()
    }

    private fun initMeals() {
        val mealNames = resourceProvider.getStringArray(R.array.meal_names)
        _state.update {
            it.copy(
                meals = mealNames.map { mealName ->
                    Meal(
                        mealName = mealName,
                        diaryEntries = emptyList()
                    )
                }
            )
        }
    }

    fun onEvent(event: DiaryEvent) {
        when (event) {
            is DiaryEvent.ChangedDate -> {
                getDiaryEntries()
            }

            is DiaryEvent.ClickedAddProduct -> {
                navigator.navigate(NavigationActions.DiaryScreen.diaryToSearch(event.mealName))
            }

            is DiaryEvent.ClickedDiaryEntry -> {

            }

            is DiaryEvent.LongClickedDiaryEntry -> {
                _state.update {
                    it.copy(
                        longClickedDiaryEntry = event.diaryEntry,
                        isDialogShowed = true
                    )
                }

            }

            is DiaryEvent.DismissedDialog -> {
                _state.update {
                    it.copy(
                        isDialogShowed = false
                    )
                }
            }

            is DiaryEvent.ClickedDeleteInDialog -> {
                _state.value.longClickedDiaryEntry?.let { diaryEntry ->
                    viewModelScope.launch(Dispatchers.IO) {
                        val result = deleteDiaryEntry(diaryEntry.id)
                        if (result is CustomResult.Error) {
                            _errorState.send(result.message)
                        } else {
                            _state.update {
                                it.copy(
                                    isDialogShowed = false,
                                    meals = updateDiaryEntriesListAfterDelete(
                                        diaryEntryId = diaryEntry.id,
                                        meals = _state.value.meals
                                    )
                                )
                            }
                        }
                    }
                }
            }

            is DiaryEvent.ClickedEditInDialog -> {

            }
            is DiaryEvent.BackPressed -> {
                navigator.navigate(NavigationActions.DiaryScreen.diaryToSummary())
            }
        }
    }

    private fun initWantedNutritionValues() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    wantedNutritionValues = FitnessApp.getWantedNutritionValues()
                )
            }
        }
    }

    private fun getDiaryEntries() {
        val currentDate = CurrentDate.dateModel(resourceProvider = resourceProvider)
        viewModelScope.launch(Dispatchers.IO) {
            val resource = getDiaryEntries(
                timestamp = currentDate.timestamp,
                mealNames = resourceProvider.getStringArray(R.array.meal_names).toList()
            )
            when (resource) {
                is Resource.Success -> {
                    val data = resource.data
                    data?.let { meals ->
                        _state.update {
                            it.copy(
                                meals = meals
                            )
                        }
                    }
                }

                is Resource.Error -> {
                    resource.uiText?.let { error ->
                        _errorState.send(error)
                    }
                }
            }
        }
    }
}
