package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GenerateDiaryItemDialogTitleUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.diary.LongClickedDiaryItemParams

class CreateLongClickedDiaryItemParamsUseCase(
    private val generateDiaryItemDialogTitleUseCase: GenerateDiaryItemDialogTitleUseCase
) {
    operator fun invoke(diaryItem: DiaryItem) = LongClickedDiaryItemParams(
        dialogTitle = generateDiaryItemDialogTitleUseCase(diaryItem = diaryItem),
        longClickedDiaryItem = diaryItem
    )
}