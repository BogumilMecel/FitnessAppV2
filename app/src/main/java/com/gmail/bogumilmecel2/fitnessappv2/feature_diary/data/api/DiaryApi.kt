package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.data.api

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.Currency
import com.gmail.bogumilmecel2.fitnessappv2.common.util.ApiConstants
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.DeleteDiaryEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.DiaryEntriesResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.EditProductDiaryEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.EditRecipeDiaryEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.ProductDiaryHistoryItem
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.ProductPrice
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.ProductPriceResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.RecipePriceResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.UserDiaryItemsResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntryPostRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.product.NewPriceRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.product.NewProductRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.NewRecipeRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.RecipePriceRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.model.CaloriesSumResponse
import retrofit2.http.*

interface DiaryApi {

    @GET("/diaryEntries")
    suspend fun getDiaryEntries(
        @Query("date") date: String
    ): DiaryEntriesResponse

    @POST("/diaryEntries/product")
    suspend fun insertProductDiaryEntry(
        @Body productDiaryEntryPostRequest: ProductDiaryEntryPostRequest
    )

    @POST("/diaryEntries/recipe")
    suspend fun insertRecipeDiaryEntry(
        @Body recipeDiaryEntryRequest: RecipeDiaryEntryRequest
    ): Boolean

    @HTTP(
        method = "DELETE",
        path = "/diaryEntries",
        hasBody = true
    )
    suspend fun deleteDiaryEntry(
        @Body deleteDiaryEntryRequest: DeleteDiaryEntryRequest
    )

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

    @PUT("/diaryEntries/product")
    suspend fun editProductDiaryEntry(
        @Body editProductDiaryEntryRequest: EditProductDiaryEntryRequest
    )

    @PUT("/diaryEntries/recipe")
    suspend fun editRecipeDiaryEntry(
        @Body editRecipeDiaryEntryRequest: EditRecipeDiaryEntryRequest
    )

    @POST("/products")
    suspend fun insertProduct(
        @Body newProductRequest: NewProductRequest
    ): Product

    @GET("/products/product/{productId}")
    suspend fun getProduct(
        @Path("productId") productId: String
    ): Product?

    @GET("/recipes/recipe/{recipeId}")
    suspend fun getRecipe(
        @Path("recipeId") recipeId: String
    ) : Recipe?

    @GET("/diaryEntries/history/product")
    suspend fun getProductDiaryHistory(
        @Query("page") page: Int,
        @Query("search_text") searchText: String?,
    ): List<ProductDiaryHistoryItem>

    @GET("/diaryEntries/calories")
    suspend fun getCaloriesSum(
        @Query("date") date: String
    ): CaloriesSumResponse

    @POST("/products/{productId}/price")
    suspend fun addNewPriceForProduct(
        @Body newPriceRequest: NewPriceRequest,
        @Path("productId") productId: String,
        @Header(ApiConstants.Headers.CURRENCY) currency: Currency
    ): ProductPrice

    @POST("/recipes")
    suspend fun addNewRecipe(
        @Body newRecipeRequest: NewRecipeRequest
    ): Recipe

    @GET("/products/{productId}/price")
    suspend fun getProductPrice(
        @Path("productId") productId: String,
        @Header(ApiConstants.Headers.CURRENCY) currency: Currency
    ): ProductPriceResponse

    @POST("/recipes/price")
    suspend fun getRecipePriceFromIngredients(
        @Body recipePriceRequest: RecipePriceRequest,
        @Header(ApiConstants.Headers.CURRENCY) currency: Currency
    ): RecipePriceResponse?

    @GET("/userData/diaryItems")
    suspend fun getUserDiaryItems(): UserDiaryItemsResponse
}