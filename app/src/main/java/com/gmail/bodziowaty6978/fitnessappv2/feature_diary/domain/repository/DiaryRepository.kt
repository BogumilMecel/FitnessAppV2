package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.repository

import com.gmail.bodziowaty6978.fitnessappv2.common.util.CustomResult
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Price
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.diary_entry.DiaryEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.Recipe

interface DiaryRepository {
    suspend fun getDiaryEntries(timestamp: Long): Resource<List<DiaryEntry>>
    suspend fun searchForProducts(searchText: String?): Resource<List<Product>>
    suspend fun searchForProductWithBarcode(barcode: String): Resource<Product>
    suspend fun searchForRecipes(searchText: String?): Resource<List<Recipe>>
    suspend fun getProductHistory(): Resource<List<Product>>
    suspend fun addDiaryEntry(diaryEntry: DiaryEntry): Resource<DiaryEntry>
    suspend fun deleteDiaryEntry(diaryEntryId: String): CustomResult
    suspend fun editDiaryEntry(diaryEntry: DiaryEntry): CustomResult
    suspend fun saveNewProduct(product: Product): Resource<Product>
    suspend fun getCaloriesSum(date: String): Resource<Int>
    suspend fun addNewPrice(price: Price, productId: String): Resource<Price>
    suspend fun addNewRecipe(recipe: Recipe, timestamp: Long): Resource<Recipe>
}