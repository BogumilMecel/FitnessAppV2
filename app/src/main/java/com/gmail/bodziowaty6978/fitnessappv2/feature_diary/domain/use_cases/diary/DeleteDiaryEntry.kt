package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.diary

import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.use_case.GetToken
import com.gmail.bodziowaty6978.fitnessappv2.common.util.CustomResult
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class DeleteDiaryEntry(
    private val diaryRepository: DiaryRepository,
    private val getToken: GetToken,
    private val resourceProvider: ResourceProvider
) {
    suspend operator fun invoke(diaryEntryId:Int):CustomResult{
        val token = getToken()

        return token?.let {
            diaryRepository.deleteDiaryEntry(
                diaryEntryId = diaryEntryId,
                token = it
            )
        }?:CustomResult.Error(resourceProvider.getString(R.string.unknown_error))
    }
}