package com.gmail.bodziowaty6978.fitnessappv2.feature_summary.feature_log.domain.use_case

import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.use_case.GetToken
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class GetCaloriesSum(
    private val diaryRepository: DiaryRepository,
    private val getToken: GetToken,
    private val resourceProvider: ResourceProvider
) {
    suspend operator fun invoke(
        date:String
    ):Resource<Int>{
        val token = getToken()

        return token?.let {
            diaryRepository.getCaloriesSum(
                token = it,
                date = date
            )
        } ?: Resource.Error(resourceProvider.getString(R.string.unknown_error))
    }
}