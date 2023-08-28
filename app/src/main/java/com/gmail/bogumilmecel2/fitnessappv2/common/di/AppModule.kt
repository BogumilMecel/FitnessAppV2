package com.gmail.bogumilmecel2.fitnessappv2.common.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.gmail.bogumilmecel2.fitnessappv2.common.data.AppDatabase
import com.gmail.bogumilmecel2.fitnessappv2.common.data.navigation.ComposeCustomNavigator
import com.gmail.bogumilmecel2.fitnessappv2.common.data.navigation.repository.TokenRepositoryImp
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.navigation.Navigator
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.DateHolder
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.repository.TokenRepository
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case.*
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BottomBarStatusProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.DefaultInterceptor
import com.gmail.bogumilmecel2.fitnessappv2.common.util.RealCachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.RealDateHolder
import com.gmail.bogumilmecel2.fitnessappv2.common.util.RealResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.feature_account.domain.use_case.DeleteToken
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.data.api.AuthApi
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.data.repository.AuthRepositoryImp
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.repository.AuthRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.use_case.*
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.data.api.DiaryApi
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.data.repository.remote.DiaryRepositoryImp
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.dao.UserDiaryItemsDao
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CalculateSelectedServingPriceUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CalculateServingPrice
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CreateSearchItemParamsFromIngredientUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CreateSearchItemParamsFromProductUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GenerateDiaryItemDialogTitleUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GetPriceUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GetRecipePriceFromIngredientsUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary.*
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.new_recipe.AddNewRecipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.new_recipe.CalculateRecipeNutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.*
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.recipe.EditRecipeDiaryEntryUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.recipe.PostRecipeDiaryEntryUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search.SearchForProductsUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.data.api.UserDataApi
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.data.repository.UserDataRepositoryImp
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.repository.UserDataRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.use_cases.SaveIntroductionInformationUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_splash.data.api.LoadingApi
import com.gmail.bogumilmecel2.fitnessappv2.feature_splash.data.repository.LoadingRepositoryImp
import com.gmail.bogumilmecel2.fitnessappv2.feature_splash.domain.repository.LoadingRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.use_case.*
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.data.api.WeightApi
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.data.repository.WeighRepositoryImp
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.repository.WeightRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.use_case.AddWeightEntryUseCase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideNavigator(): Navigator = ComposeCustomNavigator()

    @Provides
    @Singleton
    fun provideResourceProvider(app: Application): ResourceProvider = RealResourceProvider(app)

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase = Room
        .databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = AppDatabase.DATABASE_NAME
        ).build()

    @Singleton
    @Provides
    fun provideProductDiaryHistoryDao(
        database: AppDatabase
    ): UserDiaryItemsDao = database.productDiaryHistoryDao()

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
    fun provideGenerateDiaryItemDialogTitleUseCase(
        resourceProvider: ResourceProvider
    ): GenerateDiaryItemDialogTitleUseCase = GenerateDiaryItemDialogTitleUseCase(
        resourceProvider = resourceProvider
    )

    @Singleton
    @Provides
    fun provideCreateLongClickedDiaryItemParamsUseCase(
        generateDiaryItemDialogTitleUseCase: GenerateDiaryItemDialogTitleUseCase
    ): CreateLongClickedDiaryItemParamsUseCase = CreateLongClickedDiaryItemParamsUseCase(
        generateDiaryItemDialogTitleUseCase = generateDiaryItemDialogTitleUseCase
    )

    @Singleton
    @Provides
    fun provideEditProductDiaryEntryUseCase(
        diaryRepository: DiaryRepository,
    ): EditProductDiaryEntryUseCase = EditProductDiaryEntryUseCase(
        diaryRepository = diaryRepository
    )

    @Singleton
    @Provides
    fun provideEditRecipeDiaryEntryUseCase(diaryRepository: DiaryRepository): EditRecipeDiaryEntryUseCase =
        EditRecipeDiaryEntryUseCase(diaryRepository = diaryRepository)

    @Singleton
    @Provides
    fun provideDateProvider(): DateHolder = RealDateHolder()

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
            .baseUrl("http://192.168.1.157:8080/products/")
            .addConverterFactory(GsonConverterFactory.create())
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

    @Singleton
    @Provides
    fun providePostRecipeDiaryEntryUseCase(
        diaryRepository: DiaryRepository,
        resourceProvider: ResourceProvider
    ): PostRecipeDiaryEntryUseCase = PostRecipeDiaryEntryUseCase(
        diaryRepository = diaryRepository,
        resourceProvider = resourceProvider
    )

    @Singleton
    @Provides
    fun provideDeleteToken(
        tokenRepository: TokenRepository
    ): DeleteToken = DeleteToken(tokenRepository = tokenRepository)

    @Singleton
    @Provides
    fun provideGetTokenUseCase(
        tokenRepository: TokenRepository
    ): GetToken = GetToken(
        tokenRepository = tokenRepository
    )

    @Provides
    @Singleton
    fun provideAuthRepository(
        authApi: AuthApi
    ): AuthRepository = AuthRepositoryImp(authApi = authApi)

    @Provides
    @Singleton
    fun provideSaveTokenUseCase(
        tokenRepository: TokenRepository
    ): SaveToken = SaveToken(
        tokenRepository = tokenRepository
    )

    @Provides
    @Singleton
    fun provideCalculateNutritionValuesFromNutritionValuesUseCase(): SumNutritionValuesUseCase =
        SumNutritionValuesUseCase()

    @Provides
    @Singleton
    fun provideAuthUseCases(
        authRepository: AuthRepository,
        resourceProvider: ResourceProvider,
        saveToken: SaveToken
    ): AuthUseCases = AuthUseCases(
        logInUser = LogInUser(
            repository = authRepository,
            resourceProvider = resourceProvider,
            saveToken = saveToken
        ),

        registerUser = RegisterUser(
            repository = authRepository,
            resourceProvider = resourceProvider,
        ),

        resetPasswordWithEmail = ResetPasswordWithEmail(
            repository = authRepository,
            resourceProvider = resourceProvider
        ),

        )

    @Singleton
    @Provides
    fun provideSaveNutritionValues(userDataRepository: UserDataRepository): SaveNutritionValues =
        SaveNutritionValues(userDataRepository)

    @Provides
    @Singleton
    fun provideSaveInformationUseCase(
        userDataRepository: UserDataRepository,
        resourceProvider: ResourceProvider,
    ): SaveIntroductionInformationUseCase = SaveIntroductionInformationUseCase(
        userDataRepository = userDataRepository,
        resourceProvider = resourceProvider
    )

    @Singleton
    @Provides
    fun provideDiaryRepository(
        diaryApi: DiaryApi,
        userDiaryItemsDao: UserDiaryItemsDao
    ): DiaryRepository = DiaryRepositoryImp(
        diaryApi = diaryApi,
        userDiaryItemsDao = userDiaryItemsDao
    )

    @Singleton
    @Provides
    fun provideDeleteDiaryEntryUseCase(
        diaryRepository: DiaryRepository
    ): DeleteDiaryEntryUseCase = DeleteDiaryEntryUseCase(
        diaryRepository = diaryRepository
    )

    @Singleton
    @Provides
    fun provideAddNewRecipe(
        diaryRepository: DiaryRepository,
        resourceProvider: ResourceProvider
    ): AddNewRecipe = AddNewRecipe(
        diaryRepository = diaryRepository,
        resourceProvider = resourceProvider
    )

    @Singleton
    @Provides
    fun provideGetUserCurrencyUseCase(
        cachedValuesProvider: CachedValuesProvider
    ): GetUserCurrencyUseCase = GetUserCurrencyUseCase(
        cachedValuesProvider = cachedValuesProvider
    )

    @Singleton
    @Provides
    fun provideGetPriceUseCase(
        diaryRepository: DiaryRepository,
        getUserCurrencyUseCase: GetUserCurrencyUseCase
    ): GetPriceUseCase = GetPriceUseCase(
        diaryRepository = diaryRepository,
        getUserCurrency = getUserCurrencyUseCase
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
    fun provideCalculateRecipeNutritionValues(): CalculateRecipeNutritionValues =
        CalculateRecipeNutritionValues()

    @Singleton
    @Provides
    fun provideWeightApi(
        retrofit: Retrofit
    ): WeightApi = retrofit.create(WeightApi::class.java)

    @Singleton
    @Provides
    fun provideWeightRepository(
        weightApi: WeightApi,
        cachedValuesProvider: CachedValuesProvider
    ): WeightRepository = WeighRepositoryImp(
        weightApi = weightApi,
        cachedValuesProvider = cachedValuesProvider
    )

    @Singleton
    @Provides
    fun provideCheckIfWeightIsValidUseCase(): CheckIfWeightIsValidUseCase =
        CheckIfWeightIsValidUseCase()

    @Singleton
    @Provides
    fun provideBottomBarStatusProvider(): BottomBarStatusProvider = BottomBarStatusProvider()

    @Singleton
    @Provides
    fun provideSummaryUseCases(
        diaryRepository: DiaryRepository,
        weightRepository: WeightRepository,
        checkIfWeightIsValidUseCase: CheckIfWeightIsValidUseCase,
        cachedValuesProvider: CachedValuesProvider
    ): SummaryUseCases = SummaryUseCases(
        getCaloriesSum = GetCaloriesSum(diaryRepository = diaryRepository),
        addWeightEntryUseCase = AddWeightEntryUseCase(
            weightRepository = weightRepository,
            checkIfWeightIsValidUseCase = checkIfWeightIsValidUseCase,
        ),
        checkIfShouldAskForWeightDialogsUseCase = CheckIfShouldAskForWeightDialogsUseCase(weightRepository),
        handleWeightDialogsQuestionUseCase = HandleWeightDialogsQuestionUseCase(
            weightRepository = weightRepository,
            cachedValuesProvider = cachedValuesProvider
        ),
        checkIfShouldShowWeightPickerUseCase = CheckIfShouldShowWeightPickerUseCase(cachedValuesProvider)
    )

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
    fun provideLoadingRepository(loadingApi: LoadingApi): LoadingRepository =
        LoadingRepositoryImp(loadingApi = loadingApi)

    @Singleton
    @Provides
    fun provideCalculateSelectedServingPriceUseCase(): CalculateSelectedServingPriceUseCase =
        CalculateSelectedServingPriceUseCase()

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
    ): CreatePieChartData = CreatePieChartData(calculateNutritionValuesPercentages)

    @Singleton
    @Provides
    fun provideCalculateServingPriceUseCase(): CalculateServingPrice = CalculateServingPrice()

    @Singleton
    @Provides
    fun provideProductUseCases(
        diaryRepository: DiaryRepository,
        resourceProvider: ResourceProvider,
        createPieChartData: CreatePieChartData,
        getUserCurrencyUseCase: GetUserCurrencyUseCase
    ): ProductUseCases =
        ProductUseCases(
            calculateProductNutritionValuesUseCase = CalculateProductNutritionValuesUseCase(),
            createPieChartData = createPieChartData,
            insertProductDiaryEntryUseCase = InsertProductDiaryEntryUseCase(
                diaryRepository = diaryRepository,
                resourceProvider = resourceProvider
            ),
            submitNewPriceUseCase = SubmitNewPriceUseCase(
                diaryRepository = diaryRepository,
                resourceProvider = resourceProvider,
                getUserCurrencyUseCase = getUserCurrencyUseCase
            ),
            getPriceUseCase = GetPriceUseCase(
                diaryRepository = diaryRepository,
                getUserCurrency = getUserCurrencyUseCase
            )
        )
}