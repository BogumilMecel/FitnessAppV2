package com.gmail.bogumilmecel2.fitnessappv2.common.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.gmail.bogumilmecel2.auth.ValidateAuthDataUseCase
import com.gmail.bogumilmecel2.fitnessappv2.FitnessAppDatabase
import com.gmail.bogumilmecel2.fitnessappv2.common.data.connectivity.ConnectivityObserverService
import com.gmail.bogumilmecel2.fitnessappv2.common.data.navigation.repository.TokenRepositoryImp
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.connectivity.ConnectivityObserver
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DateAdapter
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DateTimeAdapter
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.repository.TokenRepository
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case.CalculateNutritionValuesPercentages
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case.CheckConnectionStateUseCase
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case.GetUserCurrencyUseCase
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BarcodeScanner
import com.gmail.bogumilmecel2.fitnessappv2.common.util.DefaultInterceptor
import com.gmail.bogumilmecel2.fitnessappv2.common.util.RealBarcodeScanner
import com.gmail.bogumilmecel2.fitnessappv2.common.util.RealCachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.RealResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.data.api.AuthApi
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.data.repository.AuthRepositoryImp
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.repository.AuthRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.use_case.LogInUserUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.data.api.DiaryApi
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.data.repository.DiaryRepositoryImp
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.data.repository.OfflineDiaryRepositoryImp
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.LongToIntAdapter
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.NutritionValuesAdapter
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.ProductAdapter
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.ProductDiaryEntryAdapter
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.RecipeAdapter
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.RecipeDiaryEntryAdapter
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.OfflineDiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CalculateSkipUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CreateSearchItemParamsFromIngredientUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CreateSearchItemParamsFromProductUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GenerateNewRecipeSearchTitleUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GetRecipePriceFromIngredientsUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.CalculateProductNutritionValuesUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.CreatePieChartDataUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.GetProductUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.recipe.CalculateRecipeNutritionValuesForServingsUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.recipe.GetRecipeUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search.SearchForProductsUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.data.api.UserDataApi
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.data.repository.UserDataRepositoryImp
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.repository.UserDataRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_splash.data.api.LoadingApi
import com.gmail.bogumilmecel2.fitnessappv2.feature_splash.data.repository.LoadingRepositoryImp
import com.gmail.bogumilmecel2.fitnessappv2.feature_splash.domain.repository.LoadingRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.use_case.SaveAskForWeightDailyUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.data.api.WeightApi
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.data.repository.WeighRepositoryImp
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.repository.WeightRepository
import com.google.gson.Gson
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideScanner(@ApplicationContext context: Context): BarcodeScanner =
        RealBarcodeScanner(context)

    @Provides
    @Singleton
    fun provideResourceProvider(app: Application): ResourceProvider = RealResourceProvider(app)

    @Singleton
    @Provides
    fun provideDateTimeAdapter(): DateTimeAdapter = DateTimeAdapter()

    @Singleton
    @Provides
    fun provideDateAdapter(): DateAdapter = DateAdapter()

    @Singleton
    @Provides
    fun provideFitnessAppDatabase(
        gson: Gson,
        @ApplicationContext context: Context,
        nutritionValuesAdapter: NutritionValuesAdapter,
        longToIntAdapter: LongToIntAdapter,
        dateTimeAdapter: DateTimeAdapter,
        dateAdapter: DateAdapter
    ): FitnessAppDatabase =
        FitnessAppDatabase(
            driver = AndroidSqliteDriver(
                schema = FitnessAppDatabase.Schema,
                context = context,
                name = "fitness.app.db"
            ),
            SqlProductAdapter = ProductAdapter(
                longToIntAdapter = longToIntAdapter,
                nutritionValuesAdapter = nutritionValuesAdapter,
                dateTimeAdapter = dateTimeAdapter
            )(),
            SqlRecipeDiaryEntryAdapter = RecipeDiaryEntryAdapter(
                nutritionValuesAdapter = nutritionValuesAdapter,
                longToIntAdapter = longToIntAdapter,
                dateTimeAdapter = dateTimeAdapter,
                dateAdapter = dateAdapter
            )(),
            SqlProductDiaryEntryAdapter = ProductDiaryEntryAdapter(
                nutritionValuesAdapter = nutritionValuesAdapter,
                longToIntAdapter = longToIntAdapter,
                dateTimeAdapter = dateTimeAdapter,
                dateAdapter = dateAdapter
            )(),
            SqlRecipeAdapter = RecipeAdapter(
                gson = gson,
                nutritionValuesAdapter = nutritionValuesAdapter,
                longToIntAdapter = longToIntAdapter,
                dateTimeAdapter = dateTimeAdapter
            )()
        )

    @Singleton
    @Provides
    fun provideLongToIntAdapter(): LongToIntAdapter = LongToIntAdapter()

    @Provides
    @Singleton
    fun provideMasterKeyAlias(
        @ApplicationContext context: Context
    ): MasterKey = MasterKey.Builder(
        context,
        MasterKey.DEFAULT_MASTER_KEY_ALIAS
    )
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    @Singleton
    @Provides
    fun provideEncryptedSharedPreferences(
        @ApplicationContext context: Context,
        masterKey: MasterKey
    ): SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "PreferencesFilename",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    @Singleton
    @Provides
    fun provideDefaultInterceptor(
        sharedPreferences: SharedPreferences,
        @ApplicationContext context: Context
    ): DefaultInterceptor = DefaultInterceptor(
        sharedPreferences = sharedPreferences,
        applicationContext = context
    )

    @Singleton
    @Provides
    fun provideHttpClient(defaultInterceptor: DefaultInterceptor): OkHttpClient {
        val client = OkHttpClient.Builder().addInterceptor(defaultInterceptor)
        return client.build()
    }

    @Singleton
    @Provides
    fun provideConnectivityObserver(
        @ApplicationContext context: Context,
        loadingApi: LoadingApi
    ): ConnectivityObserver = ConnectivityObserverService(
        context = context,
        loadingApi = loadingApi
    )

    @Singleton
    @Provides
    fun provideCalculateRecipeNutritionValuesForServingsUseCase(): CalculateRecipeNutritionValuesForServingsUseCase =
        CalculateRecipeNutritionValuesForServingsUseCase()

    @Singleton
    @Provides
    fun provideCachedValuesProvider(
        @ApplicationContext context: Context,
        gson: Gson
    ): CachedValuesProvider = RealCachedValuesProvider(
        sharedPreferences = context.getSharedPreferences(
            "fitness_app",
            Context.MODE_PRIVATE
        ),
        gson = gson
    )

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        httpClient: OkHttpClient
    ): Retrofit {
        val retrofitBuilder = Retrofit.Builder()
            .client(httpClient)
            .baseUrl("http://192.168.0.216:8080/")
            .addConverterFactory(Json.asConverterFactory(contentType = "application/json".toMediaType()))
        return retrofitBuilder.build()
    }

    @Singleton
    @Provides
    fun provideProductApi(retrofit: Retrofit): DiaryApi {
        return retrofit.create(DiaryApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthApi(
        retrofit: Retrofit
    ): AuthApi = retrofit.create(AuthApi::class.java)

    @Singleton
    @Provides
    fun provideLoadingApi(retrofit: Retrofit): LoadingApi = retrofit.create(LoadingApi::class.java)

    @Singleton
    @Provides
    fun provideTokenRepository(
        sharedPreferences: SharedPreferences
    ): TokenRepository = TokenRepositoryImp(sharedPreferences = sharedPreferences)

    @Provides
    @Singleton
    fun provideAuthRepository(
        authApi: AuthApi
    ): AuthRepository = AuthRepositoryImp(authApi = authApi)

    @Singleton
    @Provides
    fun provideDiaryRepository(diaryApi: DiaryApi): DiaryRepository = DiaryRepositoryImp(diaryApi = diaryApi)

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
    fun provideWeightApi(retrofit: Retrofit): WeightApi = retrofit.create(WeightApi::class.java)

    @Singleton
    @Provides
    fun provideWeightRepository(weightApi: WeightApi): WeightRepository = WeighRepositoryImp(weightApi = weightApi)

    @Singleton
    @Provides
    fun provideSearchForProductsUseCase(diaryRepository: DiaryRepository): SearchForProductsUseCase =
        SearchForProductsUseCase(diaryRepository = diaryRepository)

    @Singleton
    @Provides
    fun provideGson(): Gson = Gson()

    @Singleton
    @Provides
    fun provideIntroductionApi(retrofit: Retrofit): UserDataApi =
        retrofit.create(UserDataApi::class.java)

    @Singleton
    @Provides
    fun provideIntroductionRepository(
        userDataApi: UserDataApi,
        cachedValuesProvider: CachedValuesProvider
    ): UserDataRepository = UserDataRepositoryImp(
        userDataApi = userDataApi,
        cachedValuesProvider = cachedValuesProvider
    )

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
    fun provideLoadingRepository(loadingApi: LoadingApi): LoadingRepository =
        LoadingRepositoryImp(loadingApi = loadingApi)

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
    ): CreatePieChartDataUseCase = CreatePieChartDataUseCase(calculateNutritionValuesPercentages)

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

    @Singleton
    @Provides
    fun provideCheckConnectionStateUseCase(
        cachedValuesProvider: CachedValuesProvider,
        connectivityObserver: ConnectivityObserver
    ): CheckConnectionStateUseCase = CheckConnectionStateUseCase(
        cachedValuesProvider = cachedValuesProvider,
        connectivityObserver = connectivityObserver
    )

    @Singleton
    @Provides
    fun provideOfflineDiaryRepository(
        fitnessAppDatabase: FitnessAppDatabase
    ): OfflineDiaryRepository = OfflineDiaryRepositoryImp(
        productQueries = fitnessAppDatabase.productQueries,
        recipeDiaryEntryQueries = fitnessAppDatabase.recipeDiaryEntryQueries,
        productDiaryEntryQueries = fitnessAppDatabase.productDiaryEntryQueries,
        recipeQueries = fitnessAppDatabase.recipeQueries
    )

    @Singleton
    @Provides
    fun provideNutritionValuesAdapter(gson: Gson): NutritionValuesAdapter = NutritionValuesAdapter(gson)
}