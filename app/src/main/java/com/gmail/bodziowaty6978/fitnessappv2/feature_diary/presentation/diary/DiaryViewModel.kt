package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.diary

import androidx.lifecycle.viewModelScope
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.data.singleton.CurrentDate
import com.gmail.bodziowaty6978.fitnessappv2.common.util.BaseViewModel
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.destinations.SearchScreenDestination
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Meal
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.diary.DeleteDiaryEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.diary.GetDiaryEntries
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.diary.UpdateDiaryEntriesListAfterDelete
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiaryViewModel @Inject constructor(
    private val getDiaryEntries: GetDiaryEntries,
    private val deleteDiaryEntry: DeleteDiaryEntry,
    private val updateDiaryEntriesListAfterDelete: UpdateDiaryEntriesListAfterDelete,
) : BaseViewModel() {

    private val _state = MutableStateFlow(DiaryState())
    val state: StateFlow<DiaryState> = _state

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
                navigateTo(SearchScreenDestination(mealName = event.mealName))
            }

            is DiaryEvent.ClickedDiaryEntry -> {

            }

            is DiaryEvent.LongClickedDiaryEntry -> {
                _state.update {
                    it.copy(
                        longClickedDiaryItem = event.diaryItem,
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
                _state.value.longClickedDiaryItem?.let { diaryEntry ->
                    viewModelScope.launch(Dispatchers.IO) {
                        deleteDiaryEntry(diaryEntry.id).handle {
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
                navigateUp()
            }
        }
    }

    fun initData() {
        initMeals()
        getDiaryEntries()
        initWantedNutritionValues()
    }

    private fun initWantedNutritionValues() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    wantedNutritionValues = sharedPreferencesUtils.getWantedNutritionValues()
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
                    _state.update {
                        it.copy(
                            meals = resource.data
                        )
                    }
                }

                is Resource.Error -> {
                    showSnackbarError(resource.uiText)
                }
            }
        }
    }
}
