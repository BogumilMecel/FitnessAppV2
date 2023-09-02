package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary

import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.GetProductUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.recipe.GetRecipeUseCase

data class DiaryUseCases(
    val getOfflineDiaryEntriesUseCase: GetOfflineDiaryEntriesUseCase,
    val getOnlineDiaryEntriesUseCase: GetOnlineDiaryEntriesUseCase,
    val deleteDiaryEntryUseCase: DeleteDiaryEntryUseCase,
    val sumNutritionValuesUseCase: SumNutritionValuesUseCase,
    val createLongClickedDiaryItemParamsUseCase: CreateLongClickedDiaryItemParamsUseCase,
    val getProductUseCase: GetProductUseCase,
    val getRecipeUseCase: GetRecipeUseCase,
)
