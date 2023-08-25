package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class GetUserDiaryAndSaveItLocallyUseCase(private val diaryRepository: DiaryRepository) {
    suspend operator fun invoke(): Resource<Unit> {
        val userRecipesAndProducts = diaryRepository.getUserDiaryItems().data ?: return Resource.Error()

        if (userRecipesAndProducts.userProducts.isNotEmpty()) {
            diaryRepository.insertUserProductsLocally(userRecipesAndProducts.userProducts)
        }

        if (userRecipesAndProducts.userRecipes.isNotEmpty()) {
            diaryRepository.insertUserRecipesLocally(userRecipesAndProducts.userRecipes)
        }

        return Resource.Success(Unit)
    }
}