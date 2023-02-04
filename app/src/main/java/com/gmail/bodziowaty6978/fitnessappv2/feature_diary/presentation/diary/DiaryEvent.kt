package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.diary

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.DiaryItem

sealed interface DiaryEvent {
    object ChangedDate:DiaryEvent
    object DismissedDialog:DiaryEvent
    object ClickedEditInDialog:DiaryEvent
    object ClickedDeleteInDialog:DiaryEvent
    data class ClickedDiaryEntry(val diaryItem: DiaryItem):DiaryEvent
    data class LongClickedDiaryEntry(val diaryItem: DiaryItem):DiaryEvent
    data class ClickedAddProduct(val mealName:String):DiaryEvent
    object BackPressed:DiaryEvent
}