package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search

import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class SearchForRecipes(private val diaryRepository: DiaryRepository) {

    suspend operator fun invoke(searchText: String): Resource<List<Recipe>>{
        return diaryRepository.searchForRecipes(searchText = searchText)
    }
}