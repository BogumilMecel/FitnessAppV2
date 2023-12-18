package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.diary

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import com.gmail.bodziowaty6978.fitnessappv2.util.TAG
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
    private val resourceProvider: ResourceProvider,
    private val navigator: Navigator
) : ViewModel() {


    private val _state = MutableStateFlow(
        DiaryState(
            meals = listOf(
                Meal(
                    mealName = resourceProvider.getString(R.string.breakfast),
                    diaryEntries = emptyList()
                ),
                Meal(
                    mealName = resourceProvider.getString(R.string.lunch),
                    diaryEntries = emptyList()
                ),
                Meal(
                    mealName = resourceProvider.getString(R.string.dinner),
                    diaryEntries = emptyList()
                ),
                Meal(
                    mealName = resourceProvider.getString(R.string.supper),
                    diaryEntries = emptyList()
                ),
            ),
        )
    )
    val state:StateFlow<DiaryState> = _state

    init{
        getDiaryEntries()
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
                        longClickedDiaryEntryWithId = event.diaryEntryWithId,
                        isDialogShowed = true
                    )
                }

            }
            is DiaryEvent.CollectedWantedNutritionValues -> {
                _state.update {
                    it.copy(
                        wantedNutritionValues = event.nutritionValues
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
                _state.value.longClickedDiaryEntryWithId?.let { diaryEntryWithId ->
                    viewModelScope.launch(Dispatchers.IO) {
                        val result = deleteDiaryEntry(diaryEntryWithId.id)
                        if (result is CustomResult.Error) {
                            onError(result.message)
                        } else {
                            _state.update {
                                it.copy(
                                    isDialogShowed = false,
                                    meals = updateDiaryEntriesListAfterDelete(
                                        diaryEntryId = diaryEntryWithId.id,
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
        }
    }

    private fun getDiaryEntries() {
        val currentDate = CurrentDate.dateModel(resourceProvider = resourceProvider).date

        viewModelScope.launch(Dispatchers.IO) {
            val resource = getDiaryEntries(
                date = currentDate,
                mealNames = resourceProvider.getStringArray(R.array.meal_names).toList()
            )
            Log.e(TAG, resource.toString())

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
                    onError(resource.uiText)
                }
            }
        }
    }

    private fun onError(errorMessage: String?) {
        _state.update {
            it.copy(
                errorMessage = errorMessage
            )
        }

        _state.update {
            it.copy(
                lastErrorMessage = errorMessage
            )
        }
    }
}
