package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product

import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.OfflineDiaryRepository

class GetProductUseCase(
    private val diaryRepository: DiaryRepository,
    private val offlineDiaryRepository: OfflineDiaryRepository
) {
    suspend operator fun invoke(productId: String): Resource<Product?> {
        val cachedProduct = offlineDiaryRepository.getProduct(productId = productId).data

        if (cachedProduct != null) {
            return Resource.Success(cachedProduct)
        }

        return diaryRepository.getProduct(productId)
    }
}