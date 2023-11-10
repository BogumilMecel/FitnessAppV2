package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository

import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe

interface OfflineDiaryRepository {
    suspend fun getProducts(
        userId: String,
        searchText: String?,
        limit: Int,
        skip: Int
    ): Resource<List<Product>>

    suspend fun getRecipes(
        userId: String,
        searchText: String?,
        limit: Int,
        skip: Int
    ): Resource<List<Recipe>>
}