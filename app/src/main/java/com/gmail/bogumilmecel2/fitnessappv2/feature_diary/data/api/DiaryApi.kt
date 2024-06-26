package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.data.api

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.Currency
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Constants
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Constants.Query.PAGE
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Constants.Query.SEARCH_TEXT
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.DeleteDiaryEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.DiaryEntriesResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.ProductPrice
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.ProductPriceResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.RecipePriceResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.product.NewPriceRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.RecipePriceRequest
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface DiaryApi {

    @GET("/diaryEntries")
    suspend fun getDiaryEntries(@Query("date") date: LocalDate): DiaryEntriesResponse

    @POST("/diaryEntries/product")
    suspend fun insertProductDiaryEntry(@Body productDiaryEntry: ProductDiaryEntry): ProductDiaryEntry

    @POST("/diaryEntries/recipe")
    suspend fun insertRecipeDiaryEntry(@Body recipeDiaryEntry: RecipeDiaryEntry): RecipeDiaryEntry

    @HTTP(
        method = "DELETE",
        path = "/diaryEntries",
        hasBody = true
    )
    suspend fun deleteDiaryEntry(@Body deleteDiaryEntryRequest: DeleteDiaryEntryRequest)

    @GET("/products/{$SEARCH_TEXT}")
    suspend fun searchForProducts(
        @Path(SEARCH_TEXT) searchText: String,
        @Query(PAGE) page: Int
    ): List<Product>

    @GET("/diaryEntries/{barcode}")
    suspend fun searchForProductWithBarcode(@Path("barcode") barcode: String): Product?

    @GET("/recipes/{searchText}")
    suspend fun searchForRecipes(@Path("searchText") searchText: String): List<Recipe>

    @PUT("/diaryEntries/product")
    suspend fun editProductDiaryEntry(@Body productDiaryEntry: ProductDiaryEntry): ProductDiaryEntry

    @PUT("/diaryEntries/recipe")
    suspend fun editRecipeDiaryEntry(@Body recipeDiaryEntry: RecipeDiaryEntry): RecipeDiaryEntry

    @POST("/products")
    suspend fun insertProduct(@Body product: Product): Product

    @GET("/products/product/{productId}")
    suspend fun getProduct(@Path("productId") productId: String): Product?

    @GET("/recipes/recipe/{recipeId}")
    suspend fun getRecipe(@Path("recipeId") recipeId: String) : Recipe?

    @POST("/products/{productId}/price")
    suspend fun addNewPriceForProduct(
        @Body newPriceRequest: NewPriceRequest,
        @Path("productId") productId: String,
        @Header(Constants.Headers.CURRENCY) currency: Currency
    ): ProductPrice

    @POST("/recipes")
    suspend fun addNewRecipe(@Body recipe: Recipe): Recipe

    @GET("/products/{productId}/price")
    suspend fun getProductPrice(
        @Path("productId") productId: String,
        @Header(Constants.Headers.CURRENCY) currency: Currency
    ): ProductPriceResponse

    @POST("/recipes/price")
    suspend fun getRecipePriceFromIngredients(
        @Body recipePriceRequest: RecipePriceRequest,
        @Header(Constants.Headers.CURRENCY) currency: Currency
    ): RecipePriceResponse?

    @GET("/userData/products")
    suspend fun getUserProducts(@Query(Constants.Query.LATEST_DATE_TIME) latestDateTime: LocalDateTime?): List<Product>

    @GET("/userData/recipes")
    suspend fun getUserRecipes(@Query(Constants.Query.LATEST_DATE_TIME) latestDateTime: LocalDateTime?): List<Recipe>

    @GET("/userData/product_diary")
    suspend fun getUserProductDiaryEntries(@Query(Constants.Query.LATEST_DATE_TIME) latestDateTime: LocalDateTime?): List<ProductDiaryEntry>

    @GET("/userData/recipe_diary")
    suspend fun getUserRecipeDiaryEntries(@Query(Constants.Query.LATEST_DATE_TIME) latestDateTime: LocalDateTime?): List<RecipeDiaryEntry>
}