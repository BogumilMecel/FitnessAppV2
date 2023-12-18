package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search

import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class SearchForProductsUseCase(private val diaryRepository: DiaryRepository) {
    suspend operator fun invoke(
        searchText: String?,
        page: Int
    ): Resource<List<Product>> {
        if (searchText.isNullOrEmpty()) return Resource.Error()

        return diaryRepository.searchForProducts(
            searchText = searchText,
            page = page
        )
    }
}