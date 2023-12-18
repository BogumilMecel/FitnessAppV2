package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.data.api

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.diary_entry.DiaryEntry
import retrofit2.http.*

interface ProductApi {

    @GET("/diaryEntries")
    suspend fun getDiaryEntries(
        @Query("timestamp") timestamp:Long,
        @Query("Authentication") token:String
    ):List<DiaryEntry>

    @POST("/diaryEntries")
    suspend fun insertDiaryEntry(
        @Body diaryEntry: DiaryEntry,
        @Query("Authentication") token:String
    ):DiaryEntry

    @DELETE("/diaryEntries/{id}")
    suspend fun deleteDiaryEntry(
        @Path("id") entryId:Int
    ):Boolean

    @GET("/products")
    suspend fun searchForProducts(
        @Query("searchText") searchText:String
    ) : List<Product>

    @GET
    suspend fun searchForProductWithBarcode(
        @Query("barcode") barcode:String
    ): Product?

    @POST("/products")
    suspend fun insertProduct(
        @Body product: Product
    ):Product
}