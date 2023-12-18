package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary

import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.diary.LongClickedDiaryItemParams

class CreateLongClickedDiaryItemParamsUseCase(private val resourceProvider: ResourceProvider) {
    operator fun invoke(diaryItem: DiaryItem) = LongClickedDiaryItemParams(
        dialogTitle = when(diaryItem) {
            is RecipeDiaryEntry -> "${diaryItem.recipeName} (${resourceProvider.getPluralString(R.plurals.servings, diaryItem.servings)})"
            is ProductDiaryEntry -> "${diaryItem.productName} (${diaryItem.weight}${resourceProvider.getString(stringResId = diaryItem.productMeasurementUnit.getStringRes())})"
            else -> throw Exception()
        },
        longClickedDiaryItem = diaryItem
    )
}