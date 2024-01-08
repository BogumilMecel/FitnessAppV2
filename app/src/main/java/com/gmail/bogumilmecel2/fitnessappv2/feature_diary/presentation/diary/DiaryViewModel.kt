package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.diary

import androidx.lifecycle.viewModelScope
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.mapNutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseViewModel
import com.gmail.bogumilmecel2.fitnessappv2.common.util.CustomDateUtils
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.getDisplayDate
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.let3
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.minusDays
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.plusDays
import com.gmail.bogumilmecel2.fitnessappv2.destinations.ProductScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.RecipeScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.SearchScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Meal
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary.DiaryUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.presentation.ProductEntryData
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.recipe.RecipeEntryData
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search.SearchEntryData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import javax.inject.Inject

@HiltViewModel
class DiaryViewModel @Inject constructor(
    private val diaryUseCases: DiaryUseCases
) : BaseViewModel<DiaryState, DiaryEvent, Unit>(
    state = DiaryState(),
    navArguments = Unit
) {
    private var currentDate: LocalDate = CustomDateUtils.getDate()
    private var diaryEntries = mutableListOf<DiaryItem>()

    override fun configureOnStart() {
        getDate()
        getDiaryEntries(withOnlineDiaryEntries = true)
        initWantedNutritionValues()
    }

    override fun onEvent(event: DiaryEvent) {
        when (event) {
            is DiaryEvent.ClickedAddProduct -> {
                navigateTo(
                    SearchScreenDestination(
                        entryData = SearchEntryData.DiaryArguments(
                            mealName = event.mealName,
                            date = currentDate
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
                        longClickedDiaryItemParams = diaryUseCases.createLongClickedDiaryItemParamsUseCase(event.diaryItem),
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
                            diaryUseCases.deleteDiaryEntryUseCase(diaryItemParams.longClickedDiaryItem)
                                .handle(
                                    finally = { hideDiaryEntryDialog() }
                                ) { getDiaryEntries(withOnlineDiaryEntries = false) }
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
                currentDate.minusDays(1)
                handleDateArrowClick()
            }

            is DiaryEvent.ClickedCalendarArrowForward -> {
                currentDate.plusDays(1)
                handleDateArrowClick()
            }
        }
    }

    private fun handleDateArrowClick() {
        getDate()
        getDiaryEntries(withOnlineDiaryEntries = true)
    }

    private fun getDate() {
        _state.update {
            it.copy(displayedDate = currentDate.getDisplayDate(resourceProvider))
        }
    }

    private fun groupDiaryEntries() {
        val groupedDiaryEntries = diaryEntries
            .toList()
            .sortedByDescending { it.creationDateTime }
            .groupBy { it.mealName }

        _state.update { state ->
            state.copy(
                diaryEntries = buildMap {
                    MealName.entries.forEach { mealName ->
                        put(
                            key = mealName,
                            value = groupedDiaryEntries[mealName]?.let { mealDiaryEntries ->
                                Meal(
                                    diaryEntries = mealDiaryEntries,
                                    nutritionValues = diaryUseCases.sumNutritionValuesUseCase(
                                        nutritionValues = mealDiaryEntries.mapNutritionValues()
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
                currentTotalNutritionValues = diaryUseCases.sumNutritionValuesUseCase(
                    nutritionValues = diaryEntries.mapNutritionValues()
                )
            )
        }
    }

    private fun getRecipeAndNavigateToEditScreen(diaryEntry: RecipeDiaryEntry) = with(diaryEntry) {
        let3(
            p1 = recipeId,
            p2 = mealName,
            p3 = date
        ) { recipeId, mealName, date ->
            viewModelScope.launch(Dispatchers.IO) {
                diaryUseCases.getRecipeUseCase(recipeId).handle { recipe ->
                    if (recipe != null) {
                        navigateTo(
                            destination = RecipeScreenDestination(
                                entryData = RecipeEntryData.Editing(
                                    recipe = recipe,
                                    recipeDiaryEntry = diaryEntry,
                                    mealName = mealName,
                                    date = date
                                )
                            )
                        )
                    }
                }
            }
        }
    }

    private fun getProductAndNavigateToEditScreen(diaryEntry: ProductDiaryEntry) {
        diaryEntry.productId?.let {
            viewModelScope.launch(Dispatchers.IO) {
                diaryUseCases.getProductUseCase(diaryEntry.productId).handle { product ->
                    if (product != null) {
                        navigateTo(
                            ProductScreenDestination(
                                entryData = ProductEntryData.Editing(
                                    product = product,
                                    productDiaryEntry = diaryEntry,
                                )
                            )
                        )
                    }
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

    private fun getDiaryEntries(withOnlineDiaryEntries: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            diaryEntries = diaryUseCases.getOfflineDiaryEntriesUseCase(date = currentDate).toMutableList()

            groupDiaryEntries()
            recalculateTotalNutritionValues()

            if (withOnlineDiaryEntries) {
                getOnlineDiaryEntries()
            }
        }
    }

    private fun getOnlineDiaryEntries() {
        viewModelScope.launch(Dispatchers.IO) {
            diaryUseCases.getOnlineDiaryEntriesUseCase(date = currentDate)
                .handle(
                    showSnackbar = false
                ) { getDiaryEntries(withOnlineDiaryEntries = false) }
        }
    }
}
