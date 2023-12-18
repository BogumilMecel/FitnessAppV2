package com.gmail.bogumilmecel2.fitnessappv2.common.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.gmail.bogumilmecel2.fitnessappv2.common.data.navigation.ComposeCustomNavigator
import com.gmail.bogumilmecel2.fitnessappv2.common.data.repository.TokenRepositoryImp
import com.gmail.bogumilmecel2.fitnessappv2.common.data.utils.CustomSharedPreferencesUtils
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.navigation.Navigator
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.repository.TokenRepository
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case.*
import com.gmail.bogumilmecel2.fitnessappv2.common.util.DefaultInterceptor
import com.gmail.bogumilmecel2.fitnessappv2.common.util.RealResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.feature_account.domain.use_case.DeleteToken
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.data.api.AuthApi
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.data.repository.AuthRepositoryImp
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.repository.AuthRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.use_case.*
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.data.api.DiaryApi
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.data.repository.remote.DiaryRepositoryImp
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CalculateSelectedServingPriceUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CalculateServingPrice
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GenerateDiaryItemDialogTitleUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GetPriceUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GetRecipePriceFromIngredientsUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary.*
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.new_recipe.AddNewRecipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.new_recipe.CalculateRecipeNutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.new_recipe.NewRecipeUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.*
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.recipe.EditRecipeDiaryEntryUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.recipe.PostRecipeDiaryEntryUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search.GetDiaryHistory
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search.SearchDiaryUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search.SearchForProductWithBarcode
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search.SearchForProductsUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search.SearchForRecipes
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.data.api.UserDataApi
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.data.repository.UserDataRepositoryImp
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.repository.UserDataRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.use_cases.SaveIntroductionInformation
import com.gmail.bogumilmecel2.fitnessappv2.feature_splash.data.api.LoadingApi
import com.gmail.bogumilmecel2.fitnessappv2.feature_splash.data.repository.LoadingRepositoryImp
import com.gmail.bogumilmecel2.fitnessappv2.feature_splash.domain.repository.LoadingRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.use_case.*
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.data.api.WeightApi
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.data.repository.WeighRepositoryImp
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.repository.WeightRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.use_case.AddWeightEntry
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
    fun provideResourceProvider(app: Application): RealResourceProvider = RealResourceProvider(app)

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
    fun provideDefaultInterceptor(sharedPreferences: SharedPreferences): DefaultInterceptor =
        DefaultInterceptor(sharedPreferences = sharedPreferences)

    @Singleton
    @Provides
    fun provideHttpClient(defaultInterceptor: DefaultInterceptor): OkHttpClient {
        val client = OkHttpClient.Builder().addInterceptor(defaultInterceptor)
        return client.build()
    }

    @Singleton
    @Provides
    fun provideGenerateDiaryItemDialogTitleUseCase(
        realResourceProvider: RealResourceProvider
    ): GenerateDiaryItemDialogTitleUseCase = GenerateDiaryItemDialogTitleUseCase(
        realResourceProvider = realResourceProvider
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
        realResourceProvider: RealResourceProvider
    ): EditProductDiaryEntryUseCase = EditProductDiaryEntryUseCase(
        diaryRepository = diaryRepository,
        realResourceProvider = realResourceProvider
    )

    @Singleton
    @Provides
    fun provideEditRecipeDiaryEntryUseCase(
        diaryRepository: DiaryRepository,
        realResourceProvider: RealResourceProvider
    ): EditRecipeDiaryEntryUseCase = EditRecipeDiaryEntryUseCase(
        diaryRepository = diaryRepository,
        realResourceProvider = realResourceProvider
    )

    @Singleton
    @Provides
    fun provideCustomSharedPreferencesUtils(
        @ApplicationContext context: Context,
        gson: Gson
    ): CustomSharedPreferencesUtils = CustomSharedPreferencesUtils(
        context = context,
        gson = gson
    )

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        httpClient: OkHttpClient
    ): Retrofit {
        val retrofitBuilder = Retrofit.Builder()
            .client(httpClient)
            .baseUrl("http://192.168.0.216:8080/products/")
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
        sharedPreferences: SharedPreferences,
        realResourceProvider: RealResourceProvider
    ): TokenRepository = TokenRepositoryImp(
        sharedPreferences = sharedPreferences,
        realResourceProvider = realResourceProvider
    )

    @Singleton
    @Provides
    fun providePostRecipeDiaryEntryUseCase(
        diaryRepository: DiaryRepository,
        realResourceProvider: RealResourceProvider
    ): PostRecipeDiaryEntryUseCase = PostRecipeDiaryEntryUseCase(
        diaryRepository = diaryRepository,
        realResourceProvider = realResourceProvider
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
        authApi: AuthApi,
        realResourceProvider: RealResourceProvider
    ): AuthRepository =
        AuthRepositoryImp(
            authApi = authApi,
            realResourceProvider = realResourceProvider
        )

    @Provides
    @Singleton
    fun provideSaveTokenUseCase(
        tokenRepository: TokenRepository
    ): SaveToken = SaveToken(
        tokenRepository = tokenRepository
    )

    @Provides
    @Singleton
    fun provideCalculateNutritionValuesFromDiaryEntriesUseCase(): CalculateNutritionValuesFromDiaryEntriesUseCase =
        CalculateNutritionValuesFromDiaryEntriesUseCase()

    @Provides
    @Singleton
    fun provideCalculateNutritionValuesFromNutritionValuesUseCase(): CalculateNutritionValuesFromNutritionValuesUseCase =
        CalculateNutritionValuesFromNutritionValuesUseCase()

    @Provides
    @Singleton
    fun provideAuthUseCases(
        authRepository: AuthRepository,
        realResourceProvider: RealResourceProvider,
        saveToken: SaveToken
    ): AuthUseCases = AuthUseCases(
        logInUser = LogInUser(
            repository = authRepository,
            realResourceProvider = realResourceProvider,
            saveToken = saveToken
        ),

        registerUser = RegisterUser(
            repository = authRepository,
            realResourceProvider = realResourceProvider,
        ),

        resetPasswordWithEmail = ResetPasswordWithEmail(
            repository = authRepository,
            realResourceProvider = realResourceProvider
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
        realResourceProvider: RealResourceProvider,
    ): SaveIntroductionInformation = SaveIntroductionInformation(
        userDataRepository = userDataRepository,
        realResourceProvider = realResourceProvider
    )

    @Singleton
    @Provides
    fun provideDiaryRepository(
        diaryApi: DiaryApi,
        realResourceProvider: RealResourceProvider
    ): DiaryRepository =
        DiaryRepositoryImp(
            realResourceProvider = realResourceProvider,
            diaryApi = diaryApi
        )

    @Singleton
    @Provides
    fun provideDeleteDiaryEntryUseCase(
        diaryRepository: DiaryRepository
    ): DeleteDiaryEntry = DeleteDiaryEntry(
        diaryRepository = diaryRepository,
    )

    @Singleton
    @Provides
    fun provideAddNewRecipe(
        diaryRepository: DiaryRepository,
        realResourceProvider: RealResourceProvider
    ): AddNewRecipe = AddNewRecipe(
        diaryRepository = diaryRepository,
        realResourceProvider = realResourceProvider
    )

    @Singleton
    @Provides
    fun provideGetUserCurrencyUseCase(
        sharedPreferences: SharedPreferences
    ): GetUserCurrencyUseCase = GetUserCurrencyUseCase(
        sharedPreferences = sharedPreferences
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
    fun provideNewRecipeUseCases(
        addNewRecipe: AddNewRecipe,
        calculateRecipeNutritionValues: CalculateRecipeNutritionValues,
        createPieChartData: CreatePieChartData,
        searchForProductsUseCase: SearchForProductsUseCase,
        calculateProductNutritionValuesUseCase: CalculateProductNutritionValuesUseCase,
        getRecipePriceFromIngredientsUseCase: GetRecipePriceFromIngredientsUseCase,
        calculateServingPrice: CalculateServingPrice
    ): NewRecipeUseCases = NewRecipeUseCases(
        addNewRecipe = addNewRecipe,
        calculateRecipeNutritionValues = calculateRecipeNutritionValues,
        createPieChartData = createPieChartData,
        searchForProductsUseCase = searchForProductsUseCase,
        calculateProductNutritionValuesUseCase = calculateProductNutritionValuesUseCase,
        getRecipePriceFromIngredientsUseCase = getRecipePriceFromIngredientsUseCase,
        calculateServingPrice = calculateServingPrice
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
        realResourceProvider: RealResourceProvider,
        customSharedPreferencesUtils: CustomSharedPreferencesUtils
    ): WeightRepository = WeighRepositoryImp(
        weightApi = weightApi,
        realResourceProvider = realResourceProvider,
        customSharedPreferencesUtils = customSharedPreferencesUtils
    )

    @Singleton
    @Provides
    fun provideSummaryUseCases(
        diaryRepository: DiaryRepository,
        weightRepository: WeightRepository,
    ): SummaryUseCases = SummaryUseCases(
        getCaloriesSum = GetCaloriesSum(diaryRepository = diaryRepository),
        addWeightEntry = AddWeightEntry(weightRepository = weightRepository),
    )

    @Singleton
    @Provides
    fun provideUpdateDiaryEntriesListAfterDelete(): UpdateDiaryEntriesListAfterDelete =
        UpdateDiaryEntriesListAfterDelete()

    @Singleton
    @Provides
    fun provideSearchForProductsUseCase(
        diaryRepository: DiaryRepository,
        getDiaryHistory: GetDiaryHistory
    ): SearchForProductsUseCase = SearchForProductsUseCase(
        diaryRepository = diaryRepository,
        getDiaryHistory = getDiaryHistory
    )

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
        realResourceProvider: RealResourceProvider,
        customSharedPreferencesUtils: CustomSharedPreferencesUtils
    ): UserDataRepository = UserDataRepositoryImp(
        userDataApi = userDataApi,
        realResourceProvider = realResourceProvider,
        customSharedPreferencesUtils = customSharedPreferencesUtils
    )

    @Singleton
    @Provides
    fun provideLoadingRepository(
        loadingApi: LoadingApi,
        realResourceProvider: RealResourceProvider,
        customSharedPreferencesUtils: CustomSharedPreferencesUtils
    ): LoadingRepository = LoadingRepositoryImp(
        loadingApi = loadingApi,
        realResourceProvider = realResourceProvider,
        customSharedPreferencesUtils = customSharedPreferencesUtils
    )

    @Singleton
    @Provides
    fun provideGetDiaryHistoryUseCase(
        diaryRepository: DiaryRepository,
    ): GetDiaryHistory = GetDiaryHistory(repository = diaryRepository)

    @Singleton
    @Provides
    fun provideCalculateSelectedServingPriceUseCase(): CalculateSelectedServingPriceUseCase =
        CalculateSelectedServingPriceUseCase()

    @Singleton
    @Provides
    fun provideDiarySearchUseCases(
        diaryRepository: DiaryRepository,
        searchForProductsUseCase: SearchForProductsUseCase,
        getDiaryHistory: GetDiaryHistory
    ): SearchDiaryUseCases =
        SearchDiaryUseCases(
            searchForProductsUseCase = searchForProductsUseCase,
            getDiaryHistory = getDiaryHistory,
            searchForProductWithBarcode = SearchForProductWithBarcode(diaryRepository),
            searchForRecipes = SearchForRecipes(diaryRepository)
        )

    @Singleton
    @Provides
    fun provideCalculateNutritionValuesPercentagesUseCase(): CalculateNutritionValuesPercentages =
        CalculateNutritionValuesPercentages()

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
        realResourceProvider: RealResourceProvider,
        createPieChartData: CreatePieChartData,
        getUserCurrencyUseCase: GetUserCurrencyUseCase
    ): ProductUseCases =
        ProductUseCases(
            calculateProductNutritionValuesUseCase = CalculateProductNutritionValuesUseCase(),
            createPieChartData = createPieChartData,
            addDiaryEntry = AddDiaryEntry(
                diaryRepository = diaryRepository,
                realResourceProvider = realResourceProvider
            ),
            submitNewPriceUseCase = SubmitNewPriceUseCase(
                diaryRepository = diaryRepository,
                realResourceProvider = realResourceProvider,
                getUserCurrencyUseCase = getUserCurrencyUseCase
            ),
            getPriceUseCase = GetPriceUseCase(
                diaryRepository = diaryRepository,
                getUserCurrency = getUserCurrencyUseCase
            )
        )
}