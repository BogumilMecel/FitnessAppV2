package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.data.api

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Price
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntryPostRequest
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntryRequest
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.domain.model.CaloriesSumResponse
import retrofit2.http.*

interface DiaryApi {

    @GET("/diaryEntries")
    suspend fun getDiaryEntries(
        @Query("date") date: String
    ): List<ProductDiaryEntry>

    @POST("/diaryEntries/product")
    suspend fun insertProductDiaryEntry(
        @Body productDiaryEntryPostRequest: ProductDiaryEntryPostRequest
    ): ProductDiaryEntry

    @POST("/diaryEntries/recipe")
    suspend fun insertRecipeDiaryEntry(
        @Body recipeDiaryEntryRequest: RecipeDiaryEntryRequest
    ): Boolean

    @DELETE("/diaryEntries/{diaryEntryId}")
    suspend fun deleteDiaryEntry(
        @Path("diaryEntryId") entryId: String
    ): Boolean

    @GET("/products/{searchText}")
    suspend fun searchForProducts(
        @Path("searchText") searchText: String
    ): List<Product>

    @GET("/diaryEntries/{barcode}")
    suspend fun searchForProductWithBarcode(
        @Path("barcode") barcode: String
    ): Product?

    @GET("/recipes/{searchText}")
    suspend fun searchForRecipes(
        @Path("searchText") searchText: String
    ): List<Recipe>

    @POST("/products")
    suspend fun insertProduct(
        @Body product: Product
    ): Product

    @GET("/products/history")
    suspend fun getProductHistory(): List<Product>

    @GET("/diaryEntries/calories")
    suspend fun getCaloriesSum(
        @Query("date") date: String
    ): CaloriesSumResponse

    @POST("/products/{productId}/prices")
    suspend fun addNewPriceForProduct(
        @Body price: Price,
        @Path("productId") productId: String
    ): Price

    @POST("/recipes")
    suspend fun addNewRecipe(
        @Body recipe: Recipe,
        @Query("timestamp") timestamp: Long,
    ): Recipe
}