package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.diary

import androidx.lifecycle.viewModelScope
import com.gmail.bogumilmecel2.fitnessappv2.common.data.singleton.CurrentDate
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseViewModel
import com.gmail.bogumilmecel2.fitnessappv2.destinations.ProductScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.SearchScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary.CalculateNutritionValuesFromDiaryEntriesUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary.CalculateNutritionValuesFromNutritionValuesUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary.CreateLongClickedDiaryItemParamsUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary.DeleteDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary.GetDiaryEntries
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary.UpdateDiaryEntriesListAfterDelete
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.presentation.ProductEntryData
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
    private val calculateNutritionValuesFromDiaryEntriesUseCase: CalculateNutritionValuesFromDiaryEntriesUseCase,
    private val calculateNutritionValuesFromNutritionValuesUseCase: CalculateNutritionValuesFromNutritionValuesUseCase,
    private val createLongClickedDiaryItemParamsUseCase: CreateLongClickedDiaryItemParamsUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(DiaryState())
    val state: StateFlow<DiaryState> = _state

    fun onEvent(event: DiaryEvent) {
        when (event) {
            is DiaryEvent.ChangedDate -> {
                getDiaryEntries()
            }

            is DiaryEvent.ClickedAddProduct -> {
                navigateTo(SearchScreenDestination(mealName = event.mealName))
            }

            is DiaryEvent.ClickedDiaryEntry -> {
                startEditingDiaryItem(event.diaryItem)
            }

            is DiaryEvent.LongClickedDiaryEntry -> {
                _state.update {
                    it.copy(
                        longClickedDiaryItemParams = createLongClickedDiaryItemParamsUseCase(event.diaryItem),
                        currentlySelectedMealName = event.mealName
                    )
                }
            }

            is DiaryEvent.DismissedDialog -> {
                hideDiaryEntryDialog()
            }

            is DiaryEvent.ClickedDeleteInDialog -> {
                    viewModelScope.launch(Dispatchers.IO) {
                        with(_state.value) {
                            longClickedDiaryItemParams?.let { diaryItemParams ->
                                deleteDiaryEntry(diaryItemParams.longClickedDiaryItem).handle(
                                    finally = {
                                        currentlySelectedMealName?.let {
                                            calculateMealNutritionValues(mealName = it)
                                            recalculateTotalNutritionValues()
                                        }
                                        hideDiaryEntryDialog()
                                    }
                                ) {
                                    currentlySelectedMealName?.let { mealName ->
                                        diaryEntries[mealName]?.let { mealDiaryEntries ->
                                            val mutableDiaryEntries = _state.value.diaryEntries.toMutableMap()
                                            mutableDiaryEntries[mealName] = updateDiaryEntriesListAfterDelete(
                                                diaryEntry = diaryItemParams.longClickedDiaryItem,
                                                diaryEntries = mealDiaryEntries
                                            )
                                            _state.update {
                                                it.copy(
                                                    diaryEntries = mutableDiaryEntries
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                }
            }

            is DiaryEvent.ClickedEditInDialog -> {
                _state.value.longClickedDiaryItemParams?.let {
                    hideDiaryEntryDialog()
                    startEditingDiaryItem(it.longClickedDiaryItem)
                }
            }

            is DiaryEvent.BackPressed -> {
                navigateUp()
            }
        }
    }

    fun initData() {
        getDiaryEntries()
        initWantedNutritionValues()
    }

    private fun hideDiaryEntryDialog() {
        _state.update {
            it.copy(
                currentlySelectedMealName = null,
                longClickedDiaryItemParams = null
            )
        }
    }

    private fun initWantedNutritionValues() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    wantedTotalNutritionValues = sharedPreferencesUtils.getWantedNutritionValues()
                )
            }
        }
    }

    private fun calculateMealNutritionValues(mealName: MealName) {
        val mutableMealNutritionValues = _state.value.mealNutritionValues.toMutableMap()
        mutableMealNutritionValues[mealName] =
            calculateNutritionValuesFromDiaryEntriesUseCase(diaryEntries = _state.value.getDiaryEntries(mealName))
        _state.update {
            it.copy(
                mealNutritionValues = mutableMealNutritionValues
            )
        }
    }

    private fun recalculateTotalNutritionValues() {
        _state.update {
            it.copy(
                currentTotalNutritionValues = calculateNutritionValuesFromNutritionValuesUseCase(
                    nutritionValues = _state.value.mealNutritionValues.values.toList()
                )
            )
        }
    }

    private fun startEditingDiaryItem(diaryItem: DiaryItem) {
        with(diaryItem) {
            when(this) {
                is ProductDiaryEntry -> {
                    navigateTo(
                        destination = ProductScreenDestination(
                            entryData = ProductEntryData.Editing(productDiaryEntry = this)
                        )
                    )
                }
                is RecipeDiaryEntry -> {

                }
                else -> {}
            }
        }
    }

    private fun getDiaryEntries() {
        val currentDate = CurrentDate.dateModel(resourceProvider = resourceProvider)
        viewModelScope.launch(Dispatchers.IO) {
            getDiaryEntries(date = currentDate.date).handle { diaryEntries ->
                _state.update {
                    it.copy(
                        diaryEntries = diaryEntries
                    )
                }
                diaryEntries.keys.forEach {
                    calculateMealNutritionValues(it)
                }
                recalculateTotalNutritionValues()
            }
        }
    }
}
