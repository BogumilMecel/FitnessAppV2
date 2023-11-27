package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.data.repository

import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseRepository
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.dao.UserDiaryItemsDao
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.OfflineDiaryRepository

class OfflineDiaryRepositoryImp(
    private val userDiaryItemsDao: UserDiaryItemsDao
): OfflineDiaryRepository, BaseRepository() {
    override suspend fun getProducts(
        userId: String?,
        searchText: String?,
        limit: Int,
        skip: Int?
    ): Resource<List<Product>> {
        return handleRequest {
            userDiaryItemsDao.getProducts(
                userId = userId,
                searchText = searchText,
                limit = limit,
                offset = skip ?: 0
            )
        }
    }

    override suspend fun getRecipes(
        userId: String?,
        searchText: String?,
        limit: Int,
        skip: Int?
    ): Resource<List<Recipe>> {
        return handleRequest {
            userDiaryItemsDao.getUserRecipes(
                userId = userId,
                searchText = searchText,
                limit = limit,
                offset = skip ?: 0
            )
        }
    }

    override suspend fun getProductDiaryEntries(
        searchText: String?,
        limit: Int,
        skip: Int?,
    ): Resource<List<ProductDiaryEntry>> {
        return handleRequest {
            userDiaryItemsDao.getProductDiaryEntries(
                searchText = searchText,
                limit = limit,
                offset = skip ?: 0,
            )
        }
    }

    override suspend fun getProductDiaryEntries(limit: Int): Resource<List<ProductDiaryEntry>> {
        return handleRequest {
            userDiaryItemsDao.getProductDiaryEntries(limit)
        }
    }

    override suspend fun getProductDiaryEntries(date: String): Resource<List<ProductDiaryEntry>> {
        return handleRequest {
            userDiaryItemsDao.getProductDiaryEntries(date)
        }
    }

    override suspend fun getRecipeDiaryEntries(
        searchText: String?,
        limit: Int,
        skip: Int?,
    ): Resource<List<RecipeDiaryEntry>> {
        return handleRequest {
            userDiaryItemsDao.getRecipeDiaryEntries(
                searchText = searchText,
                limit = limit,
                offset = skip ?: 0,
            )
        }
    }

    override suspend fun getRecipeDiaryEntries(limit: Int): Resource<List<RecipeDiaryEntry>> {
        return handleRequest {
            userDiaryItemsDao.getRecipeDiaryEntries(limit)
        }
    }

    override suspend fun getRecipeDiaryEntries(date: String): Resource<List<RecipeDiaryEntry>> {
        return handleRequest {
            userDiaryItemsDao.getRecipeDiaryEntries(date)
        }
    }

    override suspend fun insertProducts(products: List<Product>): Resource<Unit> {
        return handleRequest {
            userDiaryItemsDao.insertProducts(products)
        }
    }

    override suspend fun insertRecipes(recipes: List<Recipe>): Resource<Unit> {
        return handleRequest {
            userDiaryItemsDao.insertRecipes(recipes)
        }
    }

    override suspend fun insertProductDiaryEntries(productDiaryEntries: List<ProductDiaryEntry>): Resource<Unit> {
        return handleRequest {
            userDiaryItemsDao.insertProductDiaryEntries(productDiaryEntries)
        }
    }

    override suspend fun insertRecipeDiaryEntries(recipeDiaryEntries: List<RecipeDiaryEntry>): Resource<Unit> {
        return handleRequest {
            userDiaryItemsDao.insertRecipeDiaryEntries(recipeDiaryEntries)
        }
    }
}