package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.diary

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName

sealed interface DiaryEvent {
    object DismissedDialog : DiaryEvent
    object ClickedEditInDialog : DiaryEvent
    object ClickedDeleteInDialog : DiaryEvent
    object ClickedCalendarArrowForward : DiaryEvent
    object ClickedCalendarArrowBackwards : DiaryEvent
    data class ClickedDiaryEntry(val diaryItem: DiaryItem) : DiaryEvent
    data class LongClickedDiaryEntry(val diaryItem: DiaryItem, val mealName: MealName) : DiaryEvent
    data class ClickedAddProduct(val mealName: MealName) : DiaryEvent
    object BackPressed : DiaryEvent
}