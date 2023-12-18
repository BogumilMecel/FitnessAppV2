package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.data.repository

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseRepository
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.database.SqlProductQueries
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.dao.UserDiaryItemsDao
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.toProduct
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.OfflineDiaryRepository

class OfflineDiaryRepositoryImp(
    private val userDiaryItemsDao: UserDiaryItemsDao,
    private val productQueries: SqlProductQueries
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

    override suspend fun getProduct(productId: String): Resource<Product?> {
        return handleRequest {
            productQueries.getProduct(productId).executeAsOneOrNull()?.toProduct()
        }
    }

    override suspend fun insertProducts(products: List<Product>): Resource<Unit> {
        return handleRequest {
            userDiaryItemsDao.insertProducts(products)
        }
    }

    override suspend fun insertProduct(product: Product): Resource<Unit> {
        return handleRequest {
            userDiaryItemsDao.insertProduct(product)
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

    override suspend fun insertProductDiaryEntries(productDiaryEntries: List<ProductDiaryEntry>): Resource<Unit> {
        return handleRequest {
            userDiaryItemsDao.insertProductDiaryEntries(productDiaryEntries)
        }
    }

    override suspend fun insertProductDiaryEntry(productDiaryEntry: ProductDiaryEntry): Resource<Unit> {
        return handleRequest {
            userDiaryItemsDao.insertProductDiaryEntry(productDiaryEntry)
        }
    }

    override suspend fun deleteProductDiaryEntries(
        date: String,
        productDiaryEntriesIds: List<String>
    ): Resource<Unit> {
        return handleRequest {
            userDiaryItemsDao.deleteProductDiaryEntries(
                date = date,
                diaryEntriesIds = productDiaryEntriesIds
            )
        }
    }

    override suspend fun deleteProductDiaryEntry(productDiaryEntryId: String): Resource<Unit> {
        return handleRequest {
            userDiaryItemsDao.deleteProductDiaryEntry(productDiaryEntryId)
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

    override suspend fun getRecipe(recipeId: String): Resource<Recipe?> {
        return handleRequest {
            userDiaryItemsDao.getRecipe(recipeId).firstOrNull()
        }
    }

    override suspend fun insertRecipes(recipes: List<Recipe>): Resource<Unit> {
        return handleRequest {
            userDiaryItemsDao.insertRecipes(recipes)
        }
    }

    override suspend fun insertRecipe(recipe: Recipe): Resource<Unit> {
        return handleRequest {
            userDiaryItemsDao.insertRecipe(recipe)
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

    override suspend fun insertRecipeDiaryEntries(recipeDiaryEntries: List<RecipeDiaryEntry>): Resource<Unit> {
        return handleRequest {
            userDiaryItemsDao.insertRecipeDiaryEntries(recipeDiaryEntries)
        }
    }

    override suspend fun insertRecipeDiaryEntry(recipeDiaryEntry: RecipeDiaryEntry): Resource<Unit> {
        return handleRequest {
            userDiaryItemsDao.insertRecipeDiaryEntry(recipeDiaryEntry)
        }
    }

    override suspend fun deleteRecipeDiaryEntries(
        date: String,
        recipeDiaryEntriesIds: List<String>
    ): Resource<Unit> {
        return handleRequest {
            userDiaryItemsDao.deleteRecipeDiaryEntries(
                date = date,
                diaryEntriesIds = recipeDiaryEntriesIds
            )
        }
    }

    override suspend fun deleteRecipeDiaryEntry(recipeDiaryEntryId: String): Resource<Unit> {
        return handleRequest {
            userDiaryItemsDao.deleteRecipeDiaryEntry(recipeDiaryEntryId)
        }
    }

    override suspend fun getDiaryEntriesNutritionValues(date: String): Resource<List<NutritionValues>> {
        return handleRequest {
            buildList {
                addAll(userDiaryItemsDao.getProductDiaryEntriesNutritionValues(date))
                addAll(userDiaryItemsDao.getRecipeDiaryEntriesNutritionValues(date))
            }
        }
    }
}