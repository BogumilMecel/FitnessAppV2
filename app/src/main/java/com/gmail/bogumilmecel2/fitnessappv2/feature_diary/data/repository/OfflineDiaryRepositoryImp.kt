package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.data.repository

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseRepository
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.database.ProductDiaryEntryQueries
import com.gmail.bogumilmecel2.fitnessappv2.database.ProductQueries
import com.gmail.bogumilmecel2.fitnessappv2.database.RecipeDiaryEntryQueries
import com.gmail.bogumilmecel2.fitnessappv2.database.RecipeQueries
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.toProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.toRecipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.toRecipeDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.toProduct
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.OfflineDiaryRepository

class OfflineDiaryRepositoryImp(
    private val productQueries: ProductQueries,
    private val recipeDiaryEntryQueries: RecipeDiaryEntryQueries,
    private val productDiaryEntryQueries: ProductDiaryEntryQueries,
    private val recipeQueries: RecipeQueries
): OfflineDiaryRepository, BaseRepository() {
    override suspend fun getProducts(
        userId: String?,
        searchText: String?,
        limit: Long,
        skip: Long
    ): Resource<List<Product>> {
        return handleRequest {
            productQueries.getProducts(
                userId = userId,
                searchText = searchText,
                limit = limit,
                offset = skip
            ).executeAsList().map { it.toProduct() }
        }
    }

    override suspend fun getProduct(productId: String): Resource<Product?> {
        return handleRequest {
            productQueries.getProduct(productId).executeAsOneOrNull()?.toProduct()
        }
    }

    override suspend fun insertProducts(products: List<Product>): Resource<Unit> {
        return handleRequest {
            products.forEach { insertProduct(it) }
        }
    }

    override suspend fun insertProduct(product: Product): Resource<Unit> = with(product) {
        return handleRequest {
            productQueries.insertProduct(
                id = id,
                name = name,
                containerWeight = containerWeight,
                nutritionValuesIn = nutritionValuesIn,
                measurementUnit = measurementUnit,
                nutritionValues = nutritionValues,
                barcode = barcode,
                username = username,
                userId = userId,
                dateCreated = dateCreated
            )
        }
    }

    override suspend fun getProductDiaryEntries(
        searchText: String?,
        limit: Long,
        skip: Long,
    ): Resource<List<ProductDiaryEntry>> {
        return handleRequest {
            productDiaryEntryQueries.getProductDiaryEntries(
                searchText = searchText,
                limit = limit,
                offset = skip
            ).executeAsList().map { it.toProductDiaryEntry() }
        }
    }

    override suspend fun getProductDiaryEntries(limit: Long): Resource<List<ProductDiaryEntry>> {
        return handleRequest {
            productDiaryEntryQueries.getAllProductDiaryEntries(limit).executeAsList().map { it.toProductDiaryEntry() }
        }
    }

    override suspend fun getProductDiaryEntries(date: String): Resource<List<ProductDiaryEntry>> {
        return handleRequest {
            productDiaryEntryQueries.getProductDiaryEntriesByDate(date).executeAsList().map { it.toProductDiaryEntry() }
        }
    }

    override suspend fun insertProductDiaryEntries(productDiaryEntries: List<ProductDiaryEntry>): Resource<Unit> {
        return handleRequest {
            productDiaryEntries.forEach { insertProductDiaryEntry(it) }
        }
    }

    override suspend fun insertProductDiaryEntry(productDiaryEntry: ProductDiaryEntry): Resource<Unit> = with(productDiaryEntry) {
        return handleRequest {
            productDiaryEntryQueries.insertProductDiaryEntry(
                id = id,
                nutritionValues = nutritionValues,
                utcTimestamp = utcTimestamp,
                userId = userId,
                date = date,
                mealName = mealName,
                productMeasurementUnit = productMeasurementUnit,
                editedUtcTimestamp = editedUtcTimestamp,
                productName = productName,
                productId = productId,
                weight = weight,
                deleted = deleted
            )
        }
    }

    override suspend fun deleteProductDiaryEntries(
        date: String,
        productDiaryEntriesIds: List<String>
    ): Resource<Unit> {
        return handleRequest {
            productDiaryEntryQueries.deleteProductDiaryEntries(
                date = date,
                diaryEntriesIds = productDiaryEntriesIds
            )
        }
    }

    override suspend fun deleteProductDiaryEntry(productDiaryEntryId: String): Resource<Unit> {
        return handleRequest {
            productDiaryEntryQueries.deleteProductDiaryEntry(productDiaryEntryId)
        }
    }

    override suspend fun getRecipes(
        userId: String?,
        searchText: String?,
        limit: Long,
        skip: Long
    ): Resource<List<Recipe>> {
        return handleRequest {
            recipeQueries.getUserRecipes(
                userId = userId,
                searchText = searchText,
                limit = limit,
                offset = skip
            ).executeAsList().map { it.toRecipe() }
        }
    }

    override suspend fun getRecipe(recipeId: String): Resource<Recipe?> {
        return handleRequest {
            recipeQueries.getRecipe(recipeId).executeAsOneOrNull()?.toRecipe()
        }
    }

    override suspend fun insertRecipes(recipes: List<Recipe>): Resource<Unit> {
        return handleRequest {
            recipes.forEach { insertRecipe(it) }
        }
    }

    override suspend fun insertRecipe(recipe: Recipe): Resource<Unit> = with(recipe) {
        return handleRequest {
            recipeQueries.insertRecipe(
                id = id,
                name = name,
                ingredients = ingredients,
                utcTimestamp = utcTimestamp,
                imageUrl = imageUrl,
                nutritionValues = nutritionValues,
                timeRequired = timeRequired,
                difficulty = difficulty,
                servings = servings,
                isPublic = isPublic,
                username = username,
                userId = userId
            )
        }
    }

    override suspend fun getRecipeDiaryEntries(
        searchText: String?,
        limit: Long,
        skip: Long,
    ): Resource<List<RecipeDiaryEntry>> {
        return handleRequest {
            recipeDiaryEntryQueries.getRecipeDiaryEntries(
                searchText = searchText,
                limit = limit,
                offset = skip
            ).executeAsList().map { it.toRecipeDiaryEntry() }
        }
    }

    override suspend fun getRecipeDiaryEntries(limit: Long): Resource<List<RecipeDiaryEntry>> {
        return handleRequest {
            recipeDiaryEntryQueries.getAllRecipeDiaryEntries(limit).executeAsList().map { it.toRecipeDiaryEntry() }
        }
    }

    override suspend fun getRecipeDiaryEntries(date: String): Resource<List<RecipeDiaryEntry>> {
        return handleRequest {
            recipeDiaryEntryQueries.getRecipeDiaryEntriesByDate(date).executeAsList().map { it.toRecipeDiaryEntry() }
        }
    }

    override suspend fun insertRecipeDiaryEntries(recipeDiaryEntries: List<RecipeDiaryEntry>): Resource<Unit> {
        return handleRequest {
            recipeDiaryEntries.forEach { insertRecipeDiaryEntry(it) }
        }
    }

    override suspend fun insertRecipeDiaryEntry(recipeDiaryEntry: RecipeDiaryEntry): Resource<Unit> = with(recipeDiaryEntry) {
        return handleRequest {
            recipeDiaryEntryQueries.insertRecipeDiaryEntry(
                id = id,
                nutritionValues = nutritionValues,
                utcTimestamp = utcTimestamp,
                userId = userId,
                date = date,
                mealName = mealName,
                editedUtcTimestamp = editedUtcTimestamp,
                recipeName = recipeName,
                recipeId = recipeId,
                servings = servings,
                deleted = deleted

            )
        }
    }

    override suspend fun deleteRecipeDiaryEntries(
        date: String,
        recipeDiaryEntriesIds: List<String>
    ): Resource<Unit> {
        return handleRequest {
            recipeDiaryEntryQueries.deleteRecipeDiaryEntries(
                date = date,
                diaryEntriesIds = recipeDiaryEntriesIds
            )
        }
    }

    override suspend fun deleteRecipeDiaryEntry(recipeDiaryEntryId: String): Resource<Unit> {
        return handleRequest {
            recipeDiaryEntryQueries.deleteRecipeDiaryEntry(recipeDiaryEntryId)
        }
    }

    override suspend fun getDiaryEntriesNutritionValues(date: String): Resource<List<NutritionValues>> {
        return handleRequest {
            buildList {
                addAll(productDiaryEntryQueries.getProductDiaryEntriesNutritionValues(date).executeAsList())
                addAll(recipeDiaryEntryQueries.getRecipeDiaryEntriesNutritionValues(date).executeAsList())
            }
        }
    }
}