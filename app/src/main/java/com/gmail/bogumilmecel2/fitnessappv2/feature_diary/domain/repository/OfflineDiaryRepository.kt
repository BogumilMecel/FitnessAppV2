package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntry
import kotlinx.datetime.LocalDate

interface OfflineDiaryRepository {
    suspend fun getProducts(
        userId: String? = null,
        searchText: String? = null,
        limit: Long,
        skip: Long = 0,
    ): Resource<List<Product>>

    suspend fun getProduct(productId: String): Resource<Product?>

    suspend fun insertProducts(products: List<Product>): Resource<Unit>

    suspend fun insertProduct(product: Product): Resource<Unit>

    suspend fun getProductDiaryEntries(
        searchText: String? = null,
        limit: Long,
        skip: Long = 0,
    ): Resource<List<ProductDiaryEntry>>

    suspend fun getProductDiaryEntries(limit: Long): Resource<List<ProductDiaryEntry>>

    suspend fun getProductDiaryEntries(date: LocalDate): Resource<List<ProductDiaryEntry>>

    suspend fun insertProductDiaryEntries(productDiaryEntries: List<ProductDiaryEntry>): Resource<Unit>

    suspend fun insertProductDiaryEntry(productDiaryEntry: ProductDiaryEntry): Resource<Unit>

    suspend fun deleteProductDiaryEntries(date: LocalDate): Resource<Unit>

    suspend fun deleteProductDiaryEntry(productDiaryEntryId: String): Resource<Unit>

    suspend fun getRecipes(
        userId: String? = null,
        searchText: String? = null,
        limit: Long,
        skip: Long = 0,
    ): Resource<List<Recipe>>

    suspend fun getRecipe(recipeId: String): Resource<Recipe?>

    suspend fun insertRecipes(recipes: List<Recipe>): Resource<Unit>

    suspend fun insertRecipe(recipe: Recipe): Resource<Unit>

    suspend fun getRecipeDiaryEntries(
        searchText: String? = null,
        limit: Long,
        skip: Long = 0,
    ): Resource<List<RecipeDiaryEntry>>

    suspend fun getRecipeDiaryEntries(limit: Long): Resource<List<RecipeDiaryEntry>>

    suspend fun getRecipeDiaryEntries(date: LocalDate): Resource<List<RecipeDiaryEntry>>

    suspend fun insertRecipeDiaryEntries(recipeDiaryEntries: List<RecipeDiaryEntry>): Resource<Unit>

    suspend fun insertRecipeDiaryEntry(recipeDiaryEntry: RecipeDiaryEntry): Resource<Unit>

    suspend fun deleteRecipeDiaryEntries(date: LocalDate): Resource<Unit>

    suspend fun deleteRecipeDiaryEntry(recipeDiaryEntryId: String): Resource<Unit>

    suspend fun getDiaryEntriesNutritionValues(date: LocalDate): Resource<List<NutritionValues>>
}