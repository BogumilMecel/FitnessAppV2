package com.gmail.bogumilmecel2.fitnessappv2.common.di

import android.app.Application
import com.gmail.bogumilmecel2.fitnessappv2.common.MockCachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.Currency
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.DateHolder
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.repository.TokenRepository
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case.CalculateNutritionValuesPercentages
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case.GetUserCurrencyUseCase
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BarcodeScanner
import com.gmail.bogumilmecel2.fitnessappv2.common.util.RealDateHolder
import com.gmail.bogumilmecel2.fitnessappv2.common.util.RealResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.data.repository.MockAuthRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.User
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.repository.AuthRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.use_case.LogInUserUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.DeleteDiaryEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.DiaryEntriesResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.ProductPrice
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.RecipePriceResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.UserDiaryItemsResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntryPostRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.product.NewPriceRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.product.NewProductRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.NewRecipeRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CalculateSkipUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CreateSearchItemParamsFromIngredientUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CreateSearchItemParamsFromProductUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GenerateNewRecipeSearchTitleUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GetRecipePriceFromIngredientsUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.RecipePriceRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.CalculateProductNutritionValuesUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.CreatePieChartDataUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.GetProductUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.recipe.CalculateRecipeNutritionValuesForServingsUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.recipe.GetRecipeUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search.SearchForProductsUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.IntroductionRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.IntroductionResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.repository.UserDataRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_splash.domain.repository.LoadingRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.model.WeightDialogsRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.use_case.SaveAskForWeightDailyUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.NewWeightEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.NewWeightEntryResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.repository.WeightRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
object TestAppModule {

    @Provides
    @Singleton
    fun provideScanner(): BarcodeScanner = object : BarcodeScanner {
        override suspend fun startScan(onBarcodeScanned: (String?) -> Unit) {
            onBarcodeScanned("")
        }
    }

    @Provides
    @Singleton
    fun provideResourceProvider(app: Application): ResourceProvider = RealResourceProvider(app)

    @Singleton
    @Provides
    fun provideDateProvider(): DateHolder = RealDateHolder()

    @Singleton
    @Provides
    fun provideCalculateRecipeNutritionValuesForServingsUseCase(): CalculateRecipeNutritionValuesForServingsUseCase =
        CalculateRecipeNutritionValuesForServingsUseCase()

    @Singleton
    @Provides
    fun provideCachedValuesProvider(): CachedValuesProvider = MockCachedValuesProvider()

    @Singleton
    @Provides
    fun provideTokenRepository(): TokenRepository = object : TokenRepository {
        override suspend fun getToken(): Resource<String> {
            TODO("Not yet implemented")
        }

        override suspend fun saveToken(token: String): Resource<Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun deleteToken(): Resource<Unit> {
            TODO("Not yet implemented")
        }
    }

    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository = MockAuthRepository()

    @Singleton
    @Provides
    fun provideDiaryRepository(): DiaryRepository = object : DiaryRepository {
        override suspend fun getDiaryEntries(date: String): Resource<DiaryEntriesResponse> {
            TODO("Not yet implemented")
        }

        override suspend fun searchForProducts(
            searchText: String,
            page: Int
        ): Resource<List<Product>> {
            TODO("Not yet implemented")
        }

        override suspend fun getLocalUserRecipes(
            userId: String,
            searchText: String?,
            limit: Int,
            skip: Int
        ): Resource<List<Recipe>> {
            TODO("Not yet implemented")
        }

        override suspend fun getLocalUserProducts(
            userId: String,
            searchText: String?,
            limit: Int,
            skip: Int
        ): Resource<List<Product>> {
            TODO("Not yet implemented")
        }

        override suspend fun getDiaryEntriesComplete(): Resource<DiaryEntriesResponse> {
            TODO("Not yet implemented")
        }

        override suspend fun getOfflineDiaryEntries(date: String): Resource<DiaryEntriesResponse> {
            TODO("Not yet implemented")
        }

        override suspend fun insertOfflineDiaryEntries(diaryEntriesResponse: DiaryEntriesResponse): Resource<Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun getDiaryEntriesCount(): Resource<Int> {
            TODO("Not yet implemented")
        }

        override suspend fun searchForProductWithBarcode(barcode: String): Resource<Product?> {
            TODO("Not yet implemented")
        }

        override suspend fun searchForRecipes(searchText: String): Resource<List<Recipe>> {
            TODO("Not yet implemented")
        }

        override suspend fun insertProductDiaryEntry(productDiaryEntryPostRequest: ProductDiaryEntryPostRequest): Resource<ProductDiaryEntry> {
            TODO("Not yet implemented")
        }

        override suspend fun insertRecipeDiaryEntry(recipeDiaryEntryRequest: RecipeDiaryEntryRequest): Resource<RecipeDiaryEntry> {
            TODO("Not yet implemented")
        }

        override suspend fun getRecipe(recipeId: String): Resource<Recipe?> {
            TODO("Not yet implemented")
        }

        override suspend fun deleteDiaryEntry(deleteDiaryEntryRequest: DeleteDiaryEntryRequest): Resource<Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun deleteOfflineDiaryEntry(diaryItem: DiaryItem): Resource<Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun deleteOfflineDiaryEntries(
            date: String,
            productDiaryEntriesIds: List<String>,
            recipeDiaryEntriesIds: List<String>
        ): Resource<Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun editProductDiaryEntry(productDiaryEntry: ProductDiaryEntry): Resource<ProductDiaryEntry> {
            TODO("Not yet implemented")
        }

        override suspend fun editRecipeDiaryEntry(recipeDiaryEntry: RecipeDiaryEntry): Resource<RecipeDiaryEntry> {
            TODO("Not yet implemented")
        }

        override suspend fun saveNewProduct(newProductRequest: NewProductRequest): Resource<Product> {
            TODO("Not yet implemented")
        }

        override suspend fun getProduct(productId: String): Resource<Product?> {
            TODO("Not yet implemented")
        }

        override suspend fun getDiaryEntriesNutritionValues(date: String): Resource<List<NutritionValues>> {
            TODO("Not yet implemented")
        }

        override suspend fun getPrice(
            productId: String,
            currency: Currency
        ): Resource<ProductPrice?> {
            TODO("Not yet implemented")
        }

        override suspend fun getRecipePriceFromIngredients(
            recipePriceRequest: RecipePriceRequest,
            currency: Currency
        ): Resource<RecipePriceResponse?> {
            TODO("Not yet implemented")
        }

        override suspend fun submitNewPrice(
            newPriceRequest: NewPriceRequest,
            productId: String,
            currency: Currency
        ): Resource<ProductPrice> {
            TODO("Not yet implemented")
        }

        override suspend fun addNewRecipe(newRecipeRequest: NewRecipeRequest): Resource<Recipe> {
            TODO("Not yet implemented")
        }

        override suspend fun getOfflineDiaryEntries(
            limit: Int,
            offset: Int,
            searchText: String
        ): Resource<List<ProductDiaryEntry>> {
            TODO("Not yet implemented")
        }

        override suspend fun getUserDiaryItems(): Resource<UserDiaryItemsResponse> {
            TODO("Not yet implemented")
        }

        override suspend fun insertUserProductsLocally(userProducts: List<Product>): Resource<Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun insertUserRecipesLocally(userRecipes: List<Recipe>): Resource<Unit> {
            TODO("Not yet implemented")
        }


        override suspend fun clearLocalData(userId: String): Resource<Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun insertOfflineDiaryEntry(diaryItem: DiaryItem): Resource<Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun cacheProduct(product: Product): Resource<Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun cacheRecipe(recipe: Recipe): Resource<Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun getOfflineProduct(productId: String): Resource<Product?> {
            TODO("Not yet implemented")
        }

        override suspend fun getOfflineRecipe(recipeId: String): Resource<Recipe?> {
            TODO("Not yet implemented")
        }
    }

    @Singleton
    @Provides
    fun provideGetUserCurrencyUseCase(
        cachedValuesProvider: CachedValuesProvider
    ): GetUserCurrencyUseCase = GetUserCurrencyUseCase(
        cachedValuesProvider = cachedValuesProvider
    )

    @Singleton
    @Provides
    fun provideGetRecipePriceUseCase(
        diaryRepository: DiaryRepository,
        getUserCurrencyUseCase: GetUserCurrencyUseCase
    ): GetRecipePriceFromIngredientsUseCase = GetRecipePriceFromIngredientsUseCase(
        diaryRepository = diaryRepository,
        getUserCurrencyUseCase = getUserCurrencyUseCase
    )

    @Singleton
    @Provides
    fun provideCalculateProductNutritionValues(): CalculateProductNutritionValuesUseCase =
        CalculateProductNutritionValuesUseCase()

    @Singleton
    @Provides
    fun provideWeightRepository(): WeightRepository = object : WeightRepository {
        override suspend fun addWeightEntry(
            newWeightEntryRequest: NewWeightEntryRequest,
            timezone: String
        ): Resource<NewWeightEntryResponse> {
            TODO("Not yet implemented")
        }

        override suspend fun handleWeightDialogsQuestion(weightDialogsRequest: WeightDialogsRequest): Resource<Unit> {
            TODO("Not yet implemented")
        }
    }

    @Singleton
    @Provides
    fun provideSearchForProductsUseCase(diaryRepository: DiaryRepository): SearchForProductsUseCase =
        SearchForProductsUseCase(diaryRepository = diaryRepository)

    @Singleton
    @Provides
    fun provideGson(): Gson = Gson()

    @Singleton
    @Provides
    fun provideIntroductionRepository(): UserDataRepository = object : UserDataRepository {
        override suspend fun saveNutritionValues(nutritionValues: NutritionValues): Resource<Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun saveUserInformation(introductionRequest: IntroductionRequest): Resource<IntroductionResponse> {
            TODO("Not yet implemented")
        }
    }

    @Singleton
    @Provides
    fun provideGetProductUseCase(diaryRepository: DiaryRepository): GetProductUseCase =
        GetProductUseCase(diaryRepository)

    @Singleton
    @Provides
    fun provideGetRecipeUseCase(diaryRepository: DiaryRepository): GetRecipeUseCase =
        GetRecipeUseCase(diaryRepository)

    @Singleton
    @Provides
    fun provideLoadingRepository(): LoadingRepository = object : LoadingRepository {
        override suspend fun authenticateUser(): Resource<User?> {
            TODO("Not yet implemented")
        }
    }

    @Singleton
    @Provides
    fun provideCalculateNutritionValuesPercentagesUseCase(): CalculateNutritionValuesPercentages =
        CalculateNutritionValuesPercentages()

    @Singleton
    @Provides
    fun provideCreateSearchItemParamsFromProductUseCase(resourceProvider: ResourceProvider): CreateSearchItemParamsFromProductUseCase =
        CreateSearchItemParamsFromProductUseCase(resourceProvider = resourceProvider)

    @Singleton
    @Provides
    fun provideCreateSearchItemParamsFromIngredientUseCase(resourceProvider: ResourceProvider): CreateSearchItemParamsFromIngredientUseCase =
        CreateSearchItemParamsFromIngredientUseCase(resourceProvider = resourceProvider)

    @Singleton
    @Provides
    fun provideCreatePieChartData(
        calculateNutritionValuesPercentages: CalculateNutritionValuesPercentages
    ): CreatePieChartDataUseCase =
        CreatePieChartDataUseCase(calculateNutritionValuesPercentages)

    @Singleton
    @Provides
    fun provideSaveAskForWeightDailyUseCase(
        weightRepository: WeightRepository
    ): SaveAskForWeightDailyUseCase = SaveAskForWeightDailyUseCase(
        weightRepository = weightRepository
    )

    @Singleton
    @Provides
    fun provideCalculateSkipUseCase() = CalculateSkipUseCase()

    @Singleton
    @Provides
    fun provideGenerateNewRecipeSearchTitleUseCase(
        resourceProvider: ResourceProvider
    ) = GenerateNewRecipeSearchTitleUseCase(resourceProvider = resourceProvider)

    @Singleton
    @Provides
    fun provideLogInUserUseCase(
        authRepository: AuthRepository,
        resourceProvider: ResourceProvider,
        tokenRepository: TokenRepository
    ): LogInUserUseCase = LogInUserUseCase(
        authRepository = authRepository,
        resourceProvider = resourceProvider,
        tokenRepository = tokenRepository
    )
}