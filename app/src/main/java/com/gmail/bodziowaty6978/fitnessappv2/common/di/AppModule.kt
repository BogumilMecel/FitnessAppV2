package com.gmail.bodziowaty6978.fitnessappv2.common.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.gmail.bodziowaty6978.fitnessappv2.common.data.navigation.ComposeCustomNavigator
import com.gmail.bodziowaty6978.fitnessappv2.common.data.repository.TokenRepositoryImp
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.UserInformation
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.navigation.Navigator
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.repository.TokenRepository
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.use_case.GetToken
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.use_case.SaveToken
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.datastoreInformation
import com.gmail.bodziowaty6978.fitnessappv2.datastoreNutrition
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.data.api.AuthApi
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.data.repository.AuthRepositoryImp
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.repository.AuthRepository
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.use_case.AuthUseCases
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.use_case.LogInUser
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.use_case.RegisterUser
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.use_case.ResetPasswordWithEmail
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.data.api.ProductApi
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.data.repository.remote.DiaryRepositoryImp
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.diary.*
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.new_product.CalculateNutritionValuesIn100G
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.new_product.SaveNewProduct
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.product.*
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.search.GetDiaryHistory
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.search.SearchDiaryUseCases
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.search.SearchForProductWithBarcode
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.search.SearchForProducts
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.data.repository.IntroductionRepositoryImp
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.repository.IntroductionRepository
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.use_cases.CalculateNutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.use_cases.SaveIntroductionInformation
import com.gmail.bodziowaty6978.fitnessappv2.feature_log.data.api.LogApi
import com.gmail.bodziowaty6978.fitnessappv2.feature_log.data.repository.LogRepositoryImp
import com.gmail.bodziowaty6978.fitnessappv2.feature_log.domain.repository.LogRepository
import com.gmail.bodziowaty6978.fitnessappv2.feature_log.domain.use_case.*
import com.gmail.bodziowaty6978.fitnessappv2.feature_splash.data.api.LoadingApi
import com.gmail.bodziowaty6978.fitnessappv2.feature_splash.data.repository.LoadingRepositoryImp
import com.gmail.bodziowaty6978.fitnessappv2.feature_splash.domain.repository.LoadingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
    ):MasterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    @Singleton
    @Provides
    fun provideEncryptedSharedPreferences(
        @ApplicationContext context: Context,
        masterKey: MasterKey
    ):SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "PreferencesFilename",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    @Singleton
    @Provides
    fun provideRetrofitInstance():Retrofit{
        return Retrofit.Builder()
            .baseUrl("http://192.168.0.171:8080/products/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideProductApi(retrofit: Retrofit):ProductApi{
        return retrofit.create(ProductApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthApi(
        retrofit: Retrofit
    ):AuthApi = retrofit.create(AuthApi::class.java)

    @Singleton
    @Provides
    fun provideLoadingApi(retrofit: Retrofit):LoadingApi = retrofit.create(LoadingApi::class.java)


    @Singleton
    @Provides
    fun provideNutritionDatastore(
        @ApplicationContext context: Context
    ): DataStore<NutritionValues> = context.datastoreNutrition

    @Singleton
    @Provides
    fun provideTokenRepository(
        sharedPreferences: SharedPreferences
    ):TokenRepository = TokenRepositoryImp(
        sharedPreferences = sharedPreferences
    )

    @Singleton
    @Provides
    fun provideGetTokenUseCase(
        tokenRepository:TokenRepository
    ):GetToken = GetToken(
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
    ):SaveToken = SaveToken(
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
    fun provideIntroductionRepository(
        informationDatastore: DataStore<UserInformation>,
        nutritionDatastore: DataStore<NutritionValues>,
    ): IntroductionRepository = IntroductionRepositoryImp(
        informationDatastore = informationDatastore,
        nutritionDatastore = nutritionDatastore
    )

    @Provides
    @Singleton
    fun provideCalculateNutritionValuesUseCase(): CalculateNutritionValues =
        CalculateNutritionValues()

    @Provides
    @Singleton
    fun provideSaveInformationUseCase(
        introductionRepository: IntroductionRepository,
        resourceProvider: ResourceProvider,
        calculateNutritionValues: CalculateNutritionValues
    ): SaveIntroductionInformation = SaveIntroductionInformation(
        introductionRepository = introductionRepository,
        resourceProvider = resourceProvider,
        calculateNutritionValues = calculateNutritionValues
    )

    @Singleton
    @Provides
    fun provideDiaryRepository(
        productApi: ProductApi,
        resourceProvider: ResourceProvider
    ): DiaryRepository =
        DiaryRepositoryImp(
            resourceProvider = resourceProvider,
            productApi = productApi
        )

    @Singleton
    @Provides
    fun provideGetDiaryEntriesUseCase(
        diaryRepository: DiaryRepository,
        calculateDiaryEntriesNutritionValues: CalculateDiaryEntriesNutritionValues,
        getToken: GetToken,
        resourceProvider: ResourceProvider
    ): GetDiaryEntries = GetDiaryEntries(
        diaryRepository = diaryRepository,
        sortDiaryEntries = SortDiaryEntries(
            calculateDiaryEntriesNutritionValues = calculateDiaryEntriesNutritionValues
        ),
        getToken = getToken,
        resourceProvider = resourceProvider
    )

    @Singleton
    @Provides
    fun provideDeleteDiaryEntryUseCase(
        diaryRepository: DiaryRepository,
        getToken: GetToken,
        resourceProvider: ResourceProvider
    ):DeleteDiaryEntry = DeleteDiaryEntry(
        diaryRepository = diaryRepository,
        getToken = getToken,
        resourceProvider = resourceProvider
    )

    @Singleton
    @Provides
    fun provideLogApi(
        retrofit: Retrofit
    ) : LogApi = retrofit.create(LogApi::class.java)

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
    fun provideInsertLogUseCase(
        logRepository: LogRepository,
        getToken: GetToken,
        resourceProvider: ResourceProvider
    ): InsertLogEntry = InsertLogEntry(
        logRepository = logRepository,
        getToken = getToken,
        resourceProvider = resourceProvider
    )

    @Singleton
    @Provides
    fun provideSummaryUseCases(
        logRepository: LogRepository,
        insertLogEntry: InsertLogEntry,
        getToken: GetToken,
        resourceProvider: ResourceProvider,
        diaryRepository: DiaryRepository
    ): SummaryUseCases = SummaryUseCases(
        getLatestLogEntry = GetLatestLogEntry(
            logRepository = logRepository,
            getToken = getToken,
            resourceProvider = resourceProvider
        ),
        checkLatestLogEntry = CheckLatestLogEntry(
            insertLogEntry = insertLogEntry
        ),
        getCaloriesSum = GetCaloriesSum(
            diaryRepository = diaryRepository,
            getToken = getToken,
            resourceProvider = resourceProvider
        ),
        insertLogEntry = insertLogEntry
    )

    @Singleton
    @Provides
    fun provideUpdateDiaryEntriesListAfterDelete():UpdateDiaryEntriesListAfterDelete = UpdateDiaryEntriesListAfterDelete()

    @Singleton
    @Provides
    fun provideSearchForProductsUseCase(
        diaryRepository: DiaryRepository,
        getDiaryHistory: GetDiaryHistory
    ): SearchForProducts = SearchForProducts(
        diaryRepository = diaryRepository,
        getDiaryHistory = getDiaryHistory
    )

    @Provides
    @Singleton
    fun provideCalculateDiaryEntriesNutritionValuesUseCase():CalculateDiaryEntriesNutritionValues = CalculateDiaryEntriesNutritionValues()

    @Singleton
    @Provides
    fun provideLoadingRepository(
        loadingApi: LoadingApi,
        resourceProvider: ResourceProvider
    ):LoadingRepository = LoadingRepositoryImp(
        loadingApi = loadingApi,
        resourceProvider = resourceProvider
    )

    @Singleton
    @Provides
    fun provideGetDiaryHistoryUseCase(
        diaryRepository: DiaryRepository,
        getToken: GetToken
    ): GetDiaryHistory = GetDiaryHistory(
        repository = diaryRepository,
        getToken = getToken
    )

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
    fun provideProductUseCases(
        diaryRepository: DiaryRepository,
        resourceProvider: ResourceProvider,
        getToken:GetToken
    ): ProductUseCases =
        ProductUseCases(
            calculateNutritionValues = com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.product.CalculateNutritionValues(),
            createPieChartData = CreatePieChartData(),
            addDiaryEntry = AddDiaryEntry(
                diaryRepository,
                resourceProvider = resourceProvider,
                getToken = getToken
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