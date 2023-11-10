package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.data.repository

import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseRepository
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.dao.UserDiaryItemsDao
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.OfflineDiaryRepository

class OfflineDiaryRepositoryImp(
    private val userDiaryItemsDao: UserDiaryItemsDao
): OfflineDiaryRepository, BaseRepository() {
    override suspend fun getProducts(
        userId: String,
        searchText: String?,
        limit: Int,
        skip: Int
    ): Resource<List<Product>> {
        return handleRequest {
            userDiaryItemsDao.getUserProducts(
                userId = userId,
                searchText = searchText,
                limit = limit,
                offset = skip
            )
        }
    }

    override suspend fun getRecipes(
        userId: String,
        searchText: String?,
        limit: Int,
        skip: Int
    ): Resource<List<Recipe>> {
        return handleRequest {
            userDiaryItemsDao.getUserRecipes(
                userId = userId,
                searchText = searchText,
                limit = limit,
                offset = skip
            )
        }
    }
}