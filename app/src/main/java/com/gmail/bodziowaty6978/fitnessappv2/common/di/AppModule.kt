package com.gmail.bodziowaty6978.fitnessappv2.common.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.gmail.bodziowaty6978.fitnessappv2.common.data.navigation.ComposeCustomNavigator
import com.gmail.bodziowaty6978.fitnessappv2.common.data.repository.TokenRepositoryImp
import com.gmail.bodziowaty6978.fitnessappv2.common.data.utils.CustomSharedPreferencesUtils
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.UserInformation
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.navigation.Navigator
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.repository.TokenRepository
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.use_case.*
import com.gmail.bodziowaty6978.fitnessappv2.common.util.DefaultInterceptor
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.datastoreInformation
import com.gmail.bodziowaty6978.fitnessappv2.datastoreNutrition
import com.gmail.bodziowaty6978.fitnessappv2.feature_account.domain.use_case.DeleteToken
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.data.api.AuthApi
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.data.repository.AuthRepositoryImp
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.repository.AuthRepository
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.use_case.AuthUseCases
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.use_case.LogInUser
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.use_case.RegisterUser
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.use_case.ResetPasswordWithEmail
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.data.api.DiaryApi
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.data.repository.remote.DiaryRepositoryImp
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.diary.*
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.new_product.CalculateNutritionValuesIn100G
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.new_product.SaveNewProduct
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.new_recipe.AddNewRecipe
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.new_recipe.CalculateRecipeNutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.new_recipe.CalculateRecipePrice
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.new_recipe.NewRecipeUseCases
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.product.*
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.search.GetDiaryHistory
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.search.SearchDiaryUseCases
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.search.SearchForProductWithBarcode
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.search.SearchForProducts
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.data.api.UserDataApi
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.data.repository.UserDataRepositoryImp
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.repository.UserDataRepository
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.use_cases.CalculateNutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.use_cases.SaveIntroductionInformation
import com.gmail.bodziowaty6978.fitnessappv2.feature_splash.data.api.LoadingApi
import com.gmail.bodziowaty6978.fitnessappv2.feature_splash.data.repository.LoadingRepositoryImp
import com.gmail.bodziowaty6978.fitnessappv2.feature_splash.domain.repository.LoadingRepository
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.data.api.LogApi
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.data.repository.LogRepositoryImp
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.domain.repository.LogRepository
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.domain.use_case.*
import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.data.api.WeightApi
import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.data.repository.WeighRepositoryImp
import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.repository.WeightRepository
import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.use_case.AddWeightEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.use_case.CalculateWeightProgress
import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.use_case.GetLatestWeightEntries
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
    fun provideResourceProvider(app: Application): ResourceProvider = ResourceProvider(app)

    @Provides
    @Singleton
    fun provideMasterKeyAlias(
        @ApplicationContext context: Context
    ): MasterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
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
    fun provideDefaultInterceptor(getToken: GetToken): DefaultInterceptor =
        DefaultInterceptor(getToken = getToken)

    @Singleton
    @Provides
    fun provideHttpClient(defaultInterceptor: DefaultInterceptor): OkHttpClient {
        val client = OkHttpClient.Builder().addInterceptor(defaultInterceptor)
        return client.build()
    }

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
    fun provideNutritionDatastore(
        @ApplicationContext context: Context
    ): DataStore<NutritionValues> = context.datastoreNutrition

    @Singleton
    @Provides
    fun provideTokenRepository(
        sharedPreferences: SharedPreferences,
        resourceProvider: ResourceProvider
    ): TokenRepository = TokenRepositoryImp(
        sharedPreferences = sharedPreferences,
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


    @Singleton
    @Provides
    fun provideUserInformationDatastore(
        @ApplicationContext context: Context
    ): DataStore<UserInformation> = context.datastoreInformation


    @Provides
    @Singleton
    fun provideAuthRepository(
        authApi: AuthApi,
        resourceProvider: ResourceProvider
    ): AuthRepository =
        AuthRepositoryImp(
            authApi = authApi,
            resourceProvider = resourceProvider
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
            resourceProvider = resourceProvider
        ),

        resetPasswordWithEmail = ResetPasswordWithEmail(
            repository = authRepository,
            resourceProvider = resourceProvider
        ),

        )

    @Provides
    @Singleton
    fun provideCalculateNutritionValuesUseCase(): CalculateNutritionValues =
        CalculateNutritionValues()

    @Singleton
    @Provides
    fun provideSaveNutritionValues(userDataRepository: UserDataRepository): SaveNutritionValues =
        SaveNutritionValues(userDataRepository)

    @Provides
    @Singleton
    fun provideSaveInformationUseCase(
        userDataRepository: UserDataRepository,
        resourceProvider: ResourceProvider,
        calculateNutritionValues: CalculateNutritionValues,
        saveNutritionValues: SaveNutritionValues
    ): SaveIntroductionInformation = SaveIntroductionInformation(
        userDataRepository = userDataRepository,
        resourceProvider = resourceProvider,
        calculateNutritionValues = calculateNutritionValues,
        saveNutritionValues = saveNutritionValues
    )

    @Singleton
    @Provides
    fun provideDiaryRepository(
        diaryApi: DiaryApi,
        resourceProvider: ResourceProvider
    ): DiaryRepository =
        DiaryRepositoryImp(
            resourceProvider = resourceProvider,
            diaryApi = diaryApi
        )

    @Singleton
    @Provides
    fun provideGetDiaryEntriesUseCase(
        diaryRepository: DiaryRepository,
        resourceProvider: ResourceProvider
    ): GetDiaryEntries = GetDiaryEntries(
        diaryRepository = diaryRepository,
        sortDiaryEntries = SortDiaryEntries(),
        resourceProvider = resourceProvider
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
    fun provideLogApi(
        retrofit: Retrofit
    ): LogApi = retrofit.create(LogApi::class.java)

    @Singleton
    @Provides
    fun provideLogRepository(
        logApi: LogApi,
        resourceProvider: ResourceProvider
    ): LogRepository = LogRepositoryImp(
        logApi = logApi,
        resourceProvider = resourceProvider
    )

    @Singleton
    @Provides
    fun provideCalculateRecipePriceUseCase(): CalculateRecipePrice = CalculateRecipePrice()

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
    fun provideNewRecipeUseCases(
        addNewRecipe: AddNewRecipe,
        calculateRecipePrice: CalculateRecipePrice,
        calculateRecipeNutritionValues: CalculateRecipeNutritionValues,
        createPieChartData: CreatePieChartData,
        searchForProducts: SearchForProducts,
        calculateProductNutritionValues: CalculateProductNutritionValues
    ): NewRecipeUseCases = NewRecipeUseCases(
        addNewRecipe = addNewRecipe,
        calculateRecipePrice = calculateRecipePrice,
        calculateRecipeNutritionValues = calculateRecipeNutritionValues,
        createPieChartData = createPieChartData,
        searchForProducts = searchForProducts,
        calculateProductNutritionValues = calculateProductNutritionValues
    )

    @Singleton
    @Provides
    fun provideCalculateProductNutritionValues(): CalculateProductNutritionValues =
        CalculateProductNutritionValues()

    @Singleton
    @Provides
    fun provideCalculateRecipeNutritionValues(
        calculateProductNutritionValues: CalculateProductNutritionValues
    ): CalculateRecipeNutritionValues =
        CalculateRecipeNutritionValues(calculateProductNutritionValues = calculateProductNutritionValues)

    @Singleton
    @Provides
    fun provideWeightApi(
        retrofit: Retrofit
    ): WeightApi = retrofit.create(WeightApi::class.java)

    @Singleton
    @Provides
    fun provideWeightRepository(
        weightApi: WeightApi,
        resourceProvider: ResourceProvider
    ): WeightRepository = WeighRepositoryImp(
        weightApi = weightApi,
        resourceProvider = resourceProvider
    )


    @Singleton
    @Provides
    fun provideSummaryUseCases(
        logRepository: LogRepository,
        diaryRepository: DiaryRepository,
        weightRepository: WeightRepository,
    ): SummaryUseCases = SummaryUseCases(
        getLatestLogEntry = GetLatestLogEntry(logRepository = logRepository),
        getCaloriesSum = GetCaloriesSum(diaryRepository = diaryRepository),
        addWeightEntry = AddWeightEntry(weightRepository = weightRepository),
        getLatestWeightEntries = GetLatestWeightEntries(weightRepository = weightRepository),
        calculateWeightProgress = CalculateWeightProgress()
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
    ): SearchForProducts = SearchForProducts(
        diaryRepository = diaryRepository,
        getDiaryHistory = getDiaryHistory
    )

    @Singleton
    @Provides
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideSharedPreferencesUtils(
        @ApplicationContext context: Context,
        gson: Gson
    ): CustomSharedPreferencesUtils = CustomSharedPreferencesUtils(
        sharedPreferences = context.getSharedPreferences("fitness_app", Context.MODE_PRIVATE),
        gson = gson
    )

    @Singleton
    @Provides
    fun provideIntroductionApi(retrofit: Retrofit): UserDataApi =
        retrofit.create(UserDataApi::class.java)

    @Singleton
    @Provides
    fun provideGetWantedNutritionValuesUseCase(
        customSharedPreferencesUtils: CustomSharedPreferencesUtils
    ): GetWantedNutritionValues = GetWantedNutritionValues(customSharedPreferencesUtils)

    @Singleton
    @Provides
    fun provideIntroductionRepository(
        userDataApi: UserDataApi,
        customSharedPreferencesUtils: CustomSharedPreferencesUtils,
        resourceProvider: ResourceProvider
    ): UserDataRepository = UserDataRepositoryImp(
        userDataApi = userDataApi,
        customSharedPreferencesUtils = customSharedPreferencesUtils,
        resourceProvider = resourceProvider
    )

    @Singleton
    @Provides
    fun provideLoadingRepository(
        loadingApi: LoadingApi,
        resourceProvider: ResourceProvider,
        customSharedPreferencesUtils: CustomSharedPreferencesUtils
    ): LoadingRepository = LoadingRepositoryImp(
        loadingApi = loadingApi,
        resourceProvider = resourceProvider,
        customSharedPreferencesUtils = customSharedPreferencesUtils
    )

    @Singleton
    @Provides
    fun provideGetDiaryHistoryUseCase(
        diaryRepository: DiaryRepository,
    ): GetDiaryHistory = GetDiaryHistory(repository = diaryRepository)

    @Singleton
    @Provides
    fun provideDiarySearchUseCases(
        diaryRepository: DiaryRepository,
        searchForProducts: SearchForProducts,
        getDiaryHistory: GetDiaryHistory
    ): SearchDiaryUseCases =
        SearchDiaryUseCases(
            searchForProducts = searchForProducts,
            getDiaryHistory = getDiaryHistory,
            searchForProductWithBarcode = SearchForProductWithBarcode(diaryRepository)
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
    fun provideProductUseCases(
        diaryRepository: DiaryRepository,
        resourceProvider: ResourceProvider,
        createPieChartData: CreatePieChartData
    ): ProductUseCases =
        ProductUseCases(
            calculateProductNutritionValues = CalculateProductNutritionValues(),
            createPieChartData = createPieChartData,
            addDiaryEntry = AddDiaryEntry(
                diaryRepository,
                resourceProvider = resourceProvider
            ),
            calculatePriceFor100g = CalculatePriceFor100g(),
            addNewPrice = AddNewPrice(diaryRepository = diaryRepository)
        )

    @Singleton
    @Provides
    fun provideSaveNewProductUseCase(
        diaryRepository: DiaryRepository,
        resourceProvider: ResourceProvider
    ): SaveNewProduct = SaveNewProduct(
        diaryRepository = diaryRepository,
        resourceProvider = resourceProvider,
        calculateNutritionValuesIn100G = CalculateNutritionValuesIn100G()
    )

}