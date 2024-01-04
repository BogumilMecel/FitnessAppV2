package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary

import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.let2
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.let3
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.diary.LongClickedDiaryItemParams

class CreateLongClickedDiaryItemParamsUseCase(private val resourceProvider: ResourceProvider) {
    operator fun invoke(diaryItem: DiaryItem): LongClickedDiaryItemParams = with(diaryItem){
        return LongClickedDiaryItemParams(
        dialogTitle = when(this) {
            is RecipeDiaryEntry -> let2(
                p1 = recipeName,
                p2 = servings
            ) { recipeName, servings ->
                "$recipeName (${resourceProvider.getPluralString(R.plurals.servings, servings)})"
            }.orEmpty()

            is ProductDiaryEntry -> let3(
                p1 = productName,
                p2 = weight,
                p3 = productMeasurementUnit
            ) { productName, weight, measurementUnit ->
                "$productName ($weight${resourceProvider.getString(stringResId = measurementUnit.getStringRes())})"
            }.orEmpty()

            else -> ""
        },
        longClickedDiaryItem = diaryItem
    )
    }
}