package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.repository

import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Price
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntryPostRequest
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.product.NewPriceRequest
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.product.NewProductRequest
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.NewRecipeRequest
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntryRequest

interface DiaryRepository {
    suspend fun getDiaryEntries(date: String): Resource<List<ProductDiaryEntry>>
    suspend fun searchForProducts(searchText: String): Resource<List<Product>>
    suspend fun searchForProductWithBarcode(barcode: String): Resource<Product?>
    suspend fun searchForRecipes(searchText: String): Resource<List<Recipe>>
    suspend fun getProductHistory(): Resource<List<Product>>
    suspend fun addProductDiaryEntry(productDiaryEntryPostRequest: ProductDiaryEntryPostRequest): Resource<ProductDiaryEntry>
    suspend fun addRecipeDiaryEntry(recipeDiaryEntryRequest: RecipeDiaryEntryRequest): Resource<Unit>
    suspend fun deleteDiaryEntry(diaryEntryId: String): Resource<Unit>
    suspend fun editDiaryEntry(productDiaryEntry: ProductDiaryEntry): Resource<Unit>
    suspend fun saveNewProduct(newProductRequest: NewProductRequest): Resource<Product>
    suspend fun getCaloriesSum(date: String): Resource<Int>
    suspend fun addNewPrice(newPriceRequest: NewPriceRequest): Resource<Price>
    suspend fun addNewRecipe(newRecipeRequest: NewRecipeRequest): Resource<Recipe>
}