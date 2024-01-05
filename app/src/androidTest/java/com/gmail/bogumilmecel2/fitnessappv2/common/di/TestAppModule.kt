package com.gmail.bogumilmecel2.fitnessappv2.common.di

import android.app.Application
import com.gmail.bogumilmecel2.auth.ValidateAuthDataUseCase
import com.gmail.bogumilmecel2.fitnessappv2.common.MockCachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.connectivity.ConnectivityObserver
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.ConnectionState
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.Currency
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.repository.TokenRepository
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case.CalculateNutritionValuesPercentages
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case.GetUserCurrencyUseCase
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BarcodeScanner
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
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.product.NewPriceRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.OfflineDiaryRepository
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
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
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

        override suspend fun saveToken(token: String): Resource<Unit> = Resource.Success(Unit)

        override suspend fun deleteToken(): Resource<Unit> {
            TODO("Not yet implemented")
        }
    }

    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository = MockAuthRepository()

    @Singleton
    @Provides
    fun provideConnectivityObserver(): ConnectivityObserver = object : ConnectivityObserver {
        override fun observe(): Flow<ConnectionState> = callbackFlow {
            awaitClose { }
        }

        override suspend fun isOnline(): Boolean {
            TODO("Not yet implemented")
        }
    }

    @Singleton
    @Provides
    fun provideOfflineDiaryRepository(): OfflineDiaryRepository = object : OfflineDiaryRepository {
        override suspend fun getProducts(
            userId: String?,
            searchText: String?,
            limit: Long,
            skip: Long
        ): Resource<List<Product>> {
            TODO("Not yet implemented")
        }

        override suspend fun getProduct(productId: String): Resource<Product?> {
            TODO("Not yet implemented")
        }

        override suspend fun insertProducts(products: List<Product>): Resource<Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun insertProduct(product: Product): Resource<Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun getProductDiaryEntries(
            searchText: String?,
            limit: Long,
            skip: Long
        ): Resource<List<ProductDiaryEntry>> {
            TODO("Not yet implemented")
        }

        override suspend fun getProductDiaryEntries(limit: Long): Resource<List<ProductDiaryEntry>> {
            TODO("Not yet implemented")
        }

        override suspend fun getProductDiaryEntries(date: LocalDate): Resource<List<ProductDiaryEntry>> {
            TODO("Not yet implemented")
        }

        override suspend fun insertProductDiaryEntries(productDiaryEntries: List<ProductDiaryEntry>): Resource<Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun insertProductDiaryEntry(productDiaryEntry: ProductDiaryEntry): Resource<Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun deleteProductDiaryEntries(date: LocalDate): Resource<Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun deleteProductDiaryEntry(productDiaryEntryId: String): Resource<Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun getRecipes(
            userId: String?,
            searchText: String?,
            limit: Long,
            skip: Long
        ): Resource<List<Recipe>> {
            TODO("Not yet implemented")
        }

        override suspend fun getRecipe(recipeId: String): Resource<Recipe?> {
            TODO("Not yet implemented")
        }

        override suspend fun insertRecipes(recipes: List<Recipe>): Resource<Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun insertRecipe(recipe: Recipe): Resource<Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun getRecipeDiaryEntries(
            searchText: String?,
            limit: Long,
            skip: Long
        ): Resource<List<RecipeDiaryEntry>> {
            TODO("Not yet implemented")
        }

        override suspend fun getRecipeDiaryEntries(limit: Long): Resource<List<RecipeDiaryEntry>> {
            TODO("Not yet implemented")
        }

        override suspend fun getRecipeDiaryEntries(date: LocalDate): Resource<List<RecipeDiaryEntry>> {
            TODO("Not yet implemented")
        }

        override suspend fun insertRecipeDiaryEntries(recipeDiaryEntries: List<RecipeDiaryEntry>): Resource<Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun insertRecipeDiaryEntry(recipeDiaryEntry: RecipeDiaryEntry): Resource<Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun deleteRecipeDiaryEntries(date: LocalDate): Resource<Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun deleteRecipeDiaryEntry(recipeDiaryEntryId: String): Resource<Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun getDiaryEntriesNutritionValues(date: LocalDate): Resource<List<NutritionValues>> {
            TODO("Not yet implemented")
        }
    }

    @Singleton
    @Provides
    fun provideDiaryRepository(): DiaryRepository = object : DiaryRepository {
        override suspend fun getDiaryEntries(date: LocalDate): Resource<DiaryEntriesResponse> {
            TODO("Not yet implemented")
        }

        override suspend fun getProductDiaryEntries(latestDateTime: LocalDateTime?): Resource<List<ProductDiaryEntry>> {
            TODO("Not yet implemented")
        }

        override suspend fun getRecipeDiaryEntries(latestDateTime: LocalDateTime?): Resource<List<RecipeDiaryEntry>> {
            TODO("Not yet implemented")
        }

        override suspend fun searchForProducts(
            searchText: String,
            page: Int
        ): Resource<List<Product>> {
            TODO("Not yet implemented")
        }

        override suspend fun searchForProductWithBarcode(barcode: String): Resource<Product?> {
            TODO("Not yet implemented")
        }

        override suspend fun searchForRecipes(searchText: String): Resource<List<Recipe>> {
            TODO("Not yet implemented")
        }

        override suspend fun insertProductDiaryEntry(productDiaryEntry: ProductDiaryEntry): Resource<ProductDiaryEntry> {
            TODO("Not yet implemented")
        }

        override suspend fun insertRecipeDiaryEntry(recipeDiaryEntry: RecipeDiaryEntry): Resource<RecipeDiaryEntry> {
            TODO("Not yet implemented")
        }

        override suspend fun getRecipe(recipeId: String): Resource<Recipe?> {
            TODO("Not yet implemented")
        }

        override suspend fun deleteDiaryEntry(deleteDiaryEntryRequest: DeleteDiaryEntryRequest): Resource<Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun editProductDiaryEntry(productDiaryEntry: ProductDiaryEntry): Resource<ProductDiaryEntry> {
            TODO("Not yet implemented")
        }

        override suspend fun editRecipeDiaryEntry(recipeDiaryEntry: RecipeDiaryEntry): Resource<RecipeDiaryEntry> {
            TODO("Not yet implemented")
        }

        override suspend fun saveNewProduct(product: Product): Resource<Product> {
            TODO("Not yet implemented")
        }

        override suspend fun getProduct(productId: String): Resource<Product?> {
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

        override suspend fun addNewRecipe(recipe: Recipe): Resource<Recipe> {
            TODO("Not yet implemented")
        }

        override suspend fun getUserProducts(latestDateTime: LocalDateTime?): Resource<List<Product>> {
            TODO("Not yet implemented")
        }

        override suspend fun getUserRecipes(latestDateTime: LocalDateTime?): Resource<List<Recipe>> {
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
    fun provideGetProductUseCase(
        diaryRepository: DiaryRepository,
        offlineDiaryRepository: OfflineDiaryRepository
    ): GetProductUseCase =
        GetProductUseCase(
            diaryRepository = diaryRepository,
            offlineDiaryRepository = offlineDiaryRepository
        )

    @Singleton
    @Provides
    fun provideGetRecipeUseCase(
        diaryRepository: DiaryRepository,
        offlineDiaryRepository: OfflineDiaryRepository
    ): GetRecipeUseCase =
        GetRecipeUseCase(
            diaryRepository = diaryRepository,
            offlineDiaryRepository = offlineDiaryRepository
        )

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
    fun provideValidateAuthDataUseCase(): ValidateAuthDataUseCase = ValidateAuthDataUseCase()

    @Singleton
    @Provides
    fun provideLogInUserUseCase(
        authRepository: AuthRepository,
        resourceProvider: ResourceProvider,
        tokenRepository: TokenRepository,
        validateAuthDataUseCase: ValidateAuthDataUseCase
    ): LogInUserUseCase = LogInUserUseCase(
        authRepository = authRepository,
        resourceProvider = resourceProvider,
        tokenRepository = tokenRepository,
        validateAuthDataUseCase = validateAuthDataUseCase
    )
}