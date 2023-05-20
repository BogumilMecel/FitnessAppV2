package com.gmail.bogumilmecel2.fitnessappv2.common.di

import android.app.Application
import com.gmail.bogumilmecel2.fitnessappv2.common.data.navigation.ComposeCustomNavigator
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.UserInformation
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.navigation.Navigator
import com.gmail.bogumilmecel2.fitnessappv2.common.util.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.repository.AuthRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.use_case.AuthUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.use_case.LogInUser
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.use_case.RegisterUser
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.use_case.ResetPasswordWithEmail
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.data.repository.remote.FakeDiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary.GetDiaryEntriesUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary.SortDiaryEntriesUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.new_product.CalculateNutritionValuesIn100G
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.new_product.SaveNewProductUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.AddDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.CreatePieChartData
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.ProductUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search.GetDiaryHistory
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search.SearchDiaryUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search.SearchForProductWithBarcode
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search.SearchForProductsUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.repository.UserDataRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.use_cases.CalculateNutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.use_cases.SaveIntroductionInformationUseCase
import com.google.firebase.auth.FirebaseAuth
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

    @Singleton
    @Provides
    fun provideNavigator(): Navigator = ComposeCustomNavigator()

    @Provides
    @Singleton
    fun provideResourceProvider(app: Application): ResourceProvider = ResourceProvider(app)

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()


    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository = object : AuthRepository {
        override suspend fun logInUser(email: String, password: String): CustomResult {
            return CustomResult.Success
        }

        override suspend fun registerUser(email: String, password: String): CustomResult {
            return CustomResult.Success
        }

        override suspend fun sendPasswordResetEmail(email: String): CustomResult {
            return CustomResult.Success
        }
    }

    @Provides
    @Singleton
    fun provideAuthUseCases(
        authRepository: AuthRepository,
        resourceProvider: ResourceProvider
    ): AuthUseCases = AuthUseCases(
        logInUser = LogInUser(
            repository = authRepository,
            resourceProvider = resourceProvider
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
    fun provideIntroductionRepository(): UserDataRepository = object : UserDataRepository {
        override suspend fun saveNutritionValues(
            userInformation: UserInformation,
            nutritionValues: NutritionValues
        ): CustomResult {
            return CustomResult.Success
        }
    }

    @Provides
    @Singleton
    fun provideCalculateNutritionValuesUseCase(): CalculateNutritionValues =
        CalculateNutritionValues()

    @Provides
    @Singleton
    fun provideSaveInformationUseCase(
        userDataRepository: UserDataRepository,
        resourceProvider: ResourceProvider,
        calculateNutritionValues: CalculateNutritionValues
    ): SaveIntroductionInformationUseCase = SaveIntroductionInformationUseCase(
        userDataRepository = userDataRepository,
        resourceProvider = resourceProvider,
        calculateNutritionValues = calculateNutritionValues
    )

    @Singleton
    @Provides
    fun provideDiaryRepository(): DiaryRepository = FakeDiaryRepository()

    @Singleton
    @Provides
    fun provideGetDiaryEntriesUseCase(
        diaryRepository: DiaryRepository
    ): GetDiaryEntriesUseCase = GetDiaryEntriesUseCase(
        diaryRepository = diaryRepository,
        sortDiaryEntriesUseCase = SortDiaryEntriesUseCase()
    )

    @Singleton
    @Provides
    fun provideSearchForProductsUseCase(
        diaryRepository: DiaryRepository,
        resourceProvider: ResourceProvider,
        getDiaryHistory: GetDiaryHistory
    ): SearchForProductsUseCase = SearchForProductsUseCase(
        diaryRepository = diaryRepository,
        resourceProvider = resourceProvider,
        getDiaryHistory = getDiaryHistory
    )

    @Singleton
    @Provides
    fun provideGetDiaryHistoryUseCase(diaryRepository: DiaryRepository): GetDiaryHistory =
        GetDiaryHistory(diaryRepository)

    @Singleton
    @Provides
    fun provideDiarySearchUseCases(
        diaryRepository: DiaryRepository,
        searchForProductsUseCase: SearchForProductsUseCase
    ): SearchDiaryUseCases =
        SearchDiaryUseCases(
            searchForProductsUseCase = searchForProductsUseCase,
            getDiaryHistory = GetDiaryHistory(diaryRepository),
            searchForProductWithBarcode = SearchForProductWithBarcode(diaryRepository)
        )

    @Singleton
    @Provides
    fun provideProductUseCases(
        diaryRepository: DiaryRepository,
        resourceProvider: ResourceProvider
    ): ProductUseCases =
        ProductUseCases(
            calculateProductNutritionValuesUseCase = com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.CalculateProductNutritionValuesUseCase(),
            createPieChartData = CreatePieChartData(),
            addDiaryEntry = AddDiaryEntry(diaryRepository, resourceProvider = resourceProvider),
            saveProductToHistory = SaveProductToHistory(diaryRepository)

        )

    @Singleton
    @Provides
    fun provideSaveNewProductUseCase(
        diaryRepository: DiaryRepository,
        resourceProvider: ResourceProvider
    ): SaveNewProductUseCase = SaveNewProductUseCase(
        diaryRepository = diaryRepository,
        resourceProvider = resourceProvider,
        calculateNutritionValuesIn100G = CalculateNutritionValuesIn100G()
    )
}