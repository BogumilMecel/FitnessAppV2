package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.Currency
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.DeleteDiaryEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.DiaryEntriesResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.EditProductDiaryEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.EditRecipeDiaryEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.ProductDiaryHistoryItem
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.ProductPrice
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.RecipePriceResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.UserDiaryItemsResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntryPostRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.product.NewPriceRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.product.NewProductRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.NewRecipeRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.RecipePriceRequest

interface DiaryRepository {
    suspend fun getDiaryEntries(date: String): Resource<DiaryEntriesResponse>
    suspend fun searchForProducts(searchText: String): Resource<List<Product>>
    suspend fun searchForProductWithBarcode(barcode: String): Resource<Product?>
    suspend fun searchForRecipes(searchText: String): Resource<List<Recipe>>
    suspend fun insertProductDiaryEntry(productDiaryEntryPostRequest: ProductDiaryEntryPostRequest): Resource<Unit>
    suspend fun addRecipeDiaryEntry(recipeDiaryEntryRequest: RecipeDiaryEntryRequest): Resource<Unit>
    suspend fun getRecipe(recipeId: String): Resource<Recipe?>
    suspend fun deleteDiaryEntry(deleteDiaryEntryRequest: DeleteDiaryEntryRequest): Resource<Unit>
    suspend fun editProductDiaryEntry(editProductDiaryEntryRequest: EditProductDiaryEntryRequest): Resource<Unit>
    suspend fun editRecipeDiaryEntry(editRecipeDiaryEntryRequest: EditRecipeDiaryEntryRequest): Resource<Unit>
    suspend fun saveNewProduct(newProductRequest: NewProductRequest): Resource<Product>
    suspend fun getProduct(productId: String): Resource<Product?>
    suspend fun getCaloriesSum(date: String): Resource<Int>
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

    suspend fun addNewRecipe(newRecipeRequest: NewRecipeRequest): Resource<Recipe>
    suspend fun getOnlineDiaryHistory(latestEntryTimestampValue: Long): Resource<List<ProductDiaryHistoryItem>>
    suspend fun getLocalDiaryHistory(limit: Int): Resource<List<ProductDiaryHistoryItem>>
    suspend fun getLocalDiaryHistory(): Resource<List<ProductDiaryHistoryItem>>
    suspend fun getUserDiaryItems(): Resource<UserDiaryItemsResponse>
    suspend fun insertUserProductsLocally(userProducts: List<Product>): Resource<Unit>
    suspend fun insertUserRecipesLocally(userRecipes: List<Recipe>): Resource<Unit>
    suspend fun getLocalUserRecipes(): Resource<List<Recipe>>
    suspend fun getLocalUserProducts(): Resource<List<Product>>
    suspend fun insertLocalHistoryItems(productDiaryHistoryItems: List<ProductDiaryHistoryItem>): Resource<Unit>
    suspend fun insertLocalHistoryItem(productDiaryHistoryItem: ProductDiaryHistoryItem): Resource<Unit>
}