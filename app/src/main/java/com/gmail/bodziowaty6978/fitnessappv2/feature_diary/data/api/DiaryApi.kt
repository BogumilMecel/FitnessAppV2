package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.data.api

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Price
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.diary_entry.DiaryEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import retrofit2.http.*

interface DiaryApi {

    @GET("/diaryEntries")
    suspend fun getDiaryEntries(
        @Query("date") date:String,
        @Header("Authorization") token:String
    ):List<DiaryEntry>

    @POST("/diaryEntries")
    suspend fun insertDiaryEntry(
        @Body diaryEntry: DiaryEntry,
        @Header("Authorization") token:String
    ):DiaryEntry

    @DELETE("/diaryEntries/{diaryEntryId}")
    suspend fun deleteDiaryEntry(
        @Header("Authorization") token:String,
        @Path("diaryEntryId") entryId:Int
    ):Boolean

    @GET("/products/{searchText}")
    suspend fun searchForProducts(
        @Path("searchText") searchText:String
    ) : List<Product>

    @GET("/diaryEntries/{barcode}")
    suspend fun searchForProductWithBarcode(
        @Path("barcode") barcode:String
    ): Product?

    @POST("/products")
    suspend fun insertProduct(
        @Body product: Product
    ):Product

    @GET("/products/history")
    suspend fun getProductHistory(
        @Header("Authorization") token:String
    ): List<Product>

    @GET("/diaryEntries/calories")
    suspend fun getCaloriesSum(
        @Query("date") date:String,
        @Header("Authorization") token:String
    ):List<Int>

    @POST("/products/{productId}/prices")
    suspend fun addNewPriceForProduct(
        @Body price: Price,
        @Path("productId") productId:Int
    ): Price

    @POST("/recipes")
    suspend fun addNewRecipe(
        @Body recipe: Recipe,
        @Header("Authorization") token:String,
        @Query("timestamp") timestamp:Long,
    ) : Recipe
}