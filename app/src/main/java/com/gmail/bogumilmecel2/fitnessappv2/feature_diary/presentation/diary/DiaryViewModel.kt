package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.diary

import androidx.lifecycle.viewModelScope
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DateTransferObject
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.DateHolder
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseViewModel
import com.gmail.bogumilmecel2.fitnessappv2.destinations.ProductScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.RecipeScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.SearchScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Meal
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary.CreateLongClickedDiaryItemParamsUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary.DeleteDiaryEntryUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary.GetDiaryEntriesUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary.SumNutritionValuesUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.GetProductUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.recipe.GetRecipeUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.presentation.ProductEntryData
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.recipe.RecipeEntryData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiaryViewModel @Inject constructor(
    private val getDiaryEntriesUseCase: GetDiaryEntriesUseCase,
    private val deleteDiaryEntryUseCase: DeleteDiaryEntryUseCase,
    private val sumNutritionValuesUseCase: SumNutritionValuesUseCase,
    private val createLongClickedDiaryItemParamsUseCase: CreateLongClickedDiaryItemParamsUseCase,
    private val getProductUseCase: GetProductUseCase,
    private val getRecipeUseCase: GetRecipeUseCase,
    private val dateHolder: DateHolder
) : BaseViewModel<DiaryState, DiaryEvent>(state = DiaryState()) {

    private var diaryEntries = mutableListOf<DiaryItem>()

    override fun onEvent(event: DiaryEvent) {
        when (event) {
            is DiaryEvent.ClickedAddProduct -> {
                navigateTo(
                    SearchScreenDestination(
                        mealName = event.mealName,
                        dateTransferObject = DateTransferObject(
                            displayedDate = _state.value.displayedDate,
                            realDate = dateHolder.getLocalDateString()
                        )
                    )
                )
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
                            deleteDiaryEntryUseCase(diaryItemParams.longClickedDiaryItem).handle(
                                finally = { hideDiaryEntryDialog() }
                            ) { getDiaryEntries() }
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

            is DiaryEvent.ClickedCalendarArrowBackwards -> {
                dateHolder.deductDay()
                handleDateArrowClick()
            }

            is DiaryEvent.ClickedCalendarArrowForward -> {
                dateHolder.addDay()
                handleDateArrowClick()
            }
        }
    }

    fun initData() {
        getDate()
        getDiaryEntries()
        initWantedNutritionValues()
    }

    private fun handleDateArrowClick() {
        getDate()
        getDiaryEntries()
    }

    private fun getDate() {
        _state.update {
            it.copy(
                displayedDate = dateHolder.getDateString(resourceProvider)
            )
        }
    }

    private fun groupDiaryEntries() {
        val groupedDiaryEntries = diaryEntries
            .toList()
            .sortedByDescending { it.utcTimestamp }
            .groupBy { it.mealName }

        _state.update { state ->
            state.copy(
                diaryEntries = buildMap {
                    MealName.values().forEach { mealName ->
                        put(
                            key = mealName,
                            value = groupedDiaryEntries[mealName]?.let { mealDiaryEntries ->
                                Meal(
                                    diaryEntries = mealDiaryEntries,
                                    nutritionValues = sumNutritionValuesUseCase(
                                        nutritionValues = mealDiaryEntries.map { it.nutritionValues }
                                    )
                                )
                            } ?: Meal.createEmpty()
                        )
                    }
                }
            )
        }
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
                    wantedTotalNutritionValues = cachedValuesProvider.getWantedNutritionValues()
                )
            }
        }
    }

    private fun recalculateTotalNutritionValues() {
        _state.update { state ->
            state.copy(
                currentTotalNutritionValues = sumNutritionValuesUseCase(
                    nutritionValues = diaryEntries.map { it.nutritionValues }
                )
            )
        }
    }

    private fun getRecipeAndNavigateToEditScreen(diaryEntry: RecipeDiaryEntry) {
        viewModelScope.launch {
            getRecipeUseCase(diaryEntry.recipeId).handle { recipe ->
                if (recipe != null) {
                    navigateTo(
                        destination = RecipeScreenDestination(
                            entryData = RecipeEntryData.Editing(
                                displayedDate = _state.value.displayedDate,
                                recipe = recipe,
                                date = diaryEntry.date,
                                mealName = diaryEntry.mealName,
                                recipeDiaryEntry = diaryEntry
                            )
                        )
                    )
                }
            }
        }
    }

    private fun getProductAndNavigateToEditScreen(diaryEntry: ProductDiaryEntry) {
        viewModelScope.launch {
            getProductUseCase(diaryEntry.productId).handle { product ->
                if (product != null) {
                    navigateTo(
                        ProductScreenDestination(
                            entryData = ProductEntryData.Editing(
                                mealName = diaryEntry.mealName,
                                product = product,
                                productDiaryEntry = diaryEntry,
                                date = diaryEntry.date,
                                displayedDate = _state.value.displayedDate,
                            )
                        )
                    )
                }
            }
        }
    }

    private fun startEditingDiaryItem(diaryItem: DiaryItem) {
        with(diaryItem) {
            when (this) {
                is ProductDiaryEntry -> {
                    getProductAndNavigateToEditScreen(this)
                }

                is RecipeDiaryEntry -> {
                    getRecipeAndNavigateToEditScreen(this)
                }

                else -> {}
            }
        }
    }

    private fun getDiaryEntries() {
        viewModelScope.launch(Dispatchers.IO) {
            getDiaryEntriesUseCase(date = dateHolder.getLocalDateString()).handle { diaryEntries ->
                this@DiaryViewModel.diaryEntries = diaryEntries.toMutableList()
                groupDiaryEntries()
                recalculateTotalNutritionValues()
            }
        }
    }
}
