package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class GetUserDiaryAndSaveItLocallyUseCase(private val diaryRepository: DiaryRepository) {
    suspend operator fun invoke(): Resource<Unit> {
        val userDiaryItems = diaryRepository.getUserDiaryItems().data ?: return Resource.Error()

        if (userDiaryItems.userProducts.isNotEmpty()) {
            diaryRepository.insertUserProductsLocally(userDiaryItems.userProducts)
        }

        if (userDiaryItems.userRecipes.isNotEmpty()) {
            diaryRepository.insertUserRecipesLocally(userDiaryItems.userRecipes)
        }

        return Resource.Success(Unit)
    }
}