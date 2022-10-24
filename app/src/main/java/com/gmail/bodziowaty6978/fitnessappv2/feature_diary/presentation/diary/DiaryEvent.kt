package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.diary

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.diary_entry.DiaryEntry

sealed class DiaryEvent {
    object ChangedDate:DiaryEvent()
    object DismissedDialog:DiaryEvent()
    object ClickedEditInDialog:DiaryEvent()
    object ClickedDeleteInDialog:DiaryEvent()
    data class ClickedDiaryEntry(val diaryEntry: DiaryEntry):DiaryEvent()
    data class LongClickedDiaryEntry(val diaryEntry: DiaryEntry):DiaryEvent()
    data class ClickedAddProduct(val mealName:String):DiaryEvent()
}