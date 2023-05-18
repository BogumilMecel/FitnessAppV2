package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bogumilmecel2.fitnessappv2.common.util.RealResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntry

class GenerateDiaryItemDialogTitleUseCase(
    private val realResourceProvider: RealResourceProvider
) {
    operator fun invoke(diaryItem: DiaryItem) = when(diaryItem) {
        is RecipeDiaryEntry -> {
            "${diaryItem.recipe.name} (${realResourceProvider.getPluralString(R.plurals.servings, diaryItem.servings)})"
        }
        is ProductDiaryEntry -> {
            "${diaryItem.product.name} (${diaryItem.weight}${realResourceProvider.getString(stringResId = diaryItem.product.measurementUnit.getStringRes())})"
        }
        else -> ""
    }
}