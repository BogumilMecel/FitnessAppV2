package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.Currency
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.DeleteDiaryEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.DiaryEntriesResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.ProductPrice
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.RecipePriceResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.product.NewPriceRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.RecipePriceRequest
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

interface DiaryRepository {
    suspend fun getDiaryEntries(date: LocalDate): Resource<DiaryEntriesResponse>
    suspend fun getProductDiaryEntries(latestDateTime: LocalDateTime?): Resource<List<ProductDiaryEntry>>
    suspend fun getRecipeDiaryEntries(latestDateTime: LocalDateTime?): Resource<List<RecipeDiaryEntry>>
    suspend fun searchForProducts(searchText: String, page: Int): Resource<List<Product>>
    suspend fun searchForProductWithBarcode(barcode: String): Resource<Product?>
    suspend fun searchForRecipes(searchText: String): Resource<List<Recipe>>
    suspend fun insertProductDiaryEntry(productDiaryEntry: ProductDiaryEntry): Resource<ProductDiaryEntry>
    suspend fun insertRecipeDiaryEntry(recipeDiaryEntry: RecipeDiaryEntry): Resource<RecipeDiaryEntry>
    suspend fun getRecipe(recipeId: String): Resource<Recipe?>
    suspend fun deleteDiaryEntry(deleteDiaryEntryRequest: DeleteDiaryEntryRequest): Resource<Unit>
    suspend fun editProductDiaryEntry(productDiaryEntry: ProductDiaryEntry): Resource<ProductDiaryEntry>
    suspend fun editRecipeDiaryEntry(recipeDiaryEntry: RecipeDiaryEntry): Resource<RecipeDiaryEntry>
    suspend fun saveNewProduct(product: Product): Resource<Product>
    suspend fun getProduct(productId: String): Resource<Product?>
    suspend fun getPrice(productId: String, currency: Currency): Resource<ProductPrice?>
    suspend fun getRecipePriceFromIngredients(
        recipePriceRequest: RecipePriceRequest,
        currency: Currency
    ): Resource<RecipePriceResponse?>

    suspend fun submitNewPrice(
        newPriceRequest: NewPriceRequest,
        productId: String,
        currency: Currency
    ): Resource<ProductPrice>

    suspend fun addNewRecipe(recipe: Recipe): Resource<Recipe>
    suspend fun getUserProducts(latestDateTime: LocalDateTime?): Resource<List<Product>>
    suspend fun getUserRecipes(latestDateTime: LocalDateTime?): Resource<List<Recipe>>
}