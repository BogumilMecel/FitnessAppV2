package com.gmail.bogumilmecel2.fitnessappv2.common.di

import android.app.Application
import com.gmail.bogumilmecel2.fitnessappv2.common.data.navigation.ComposeCustomNavigator
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.Currency
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.UserInformation
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.navigation.Navigator
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.repository.TokenRepository
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case.CalculateNutritionValuesPercentages
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case.CheckIfWeightIsValidUseCase
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case.GetToken
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case.GetUserCurrencyUseCase
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case.SaveNutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case.SaveToken
import com.gmail.bogumilmecel2.fitnessappv2.common.util.RealResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_account.domain.use_case.DeleteToken
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.LoginRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.RegisterRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.TokenResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.User
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.repository.AuthRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.use_case.AuthUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.use_case.LogInUser
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.use_case.RegisterUser
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.use_case.ResetPasswordWithEmail
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.data.MockDiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CalculateSelectedServingPriceUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CalculateServingPrice
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CreateSearchItemParamsFromIngredientUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CreateSearchItemParamsFromProductUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CreateSearchItemParamsFromRecipeUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GenerateDiaryItemDialogTitleUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GetPriceUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GetRecipePriceFromIngredientsUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary.SumNutritionValuesUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary.CreateLongClickedDiaryItemParamsUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary.DeleteDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.new_recipe.AddNewRecipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.new_recipe.CalculateRecipeNutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.new_recipe.NewRecipeUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.InsertProductDiaryEntryUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.CalculateProductNutritionValuesUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.CreatePieChartData
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.EditProductDiaryEntryUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.ProductUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.SubmitNewPriceUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.recipe.EditRecipeDiaryEntryUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.recipe.PostRecipeDiaryEntryUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search.SearchDiaryUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search.SearchForProductWithBarcode
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search.SearchForProductsUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search.SearchForRecipes
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.IntroductionRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.repository.UserDataRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.use_cases.SaveIntroductionInformationUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_splash.domain.repository.LoadingRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.model.WeightDialogsRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.use_case.CheckIfShouldAskForWeightDialogsUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.use_case.GetCaloriesSum
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.use_case.SummaryUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.NewWeightEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.NewWeightEntryResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.WeightDialogs
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.WeightEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.repository.WeightRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.use_case.AddWeightEntryUseCase
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
    @Singleton
    @Provides
    fun provideNavigator(): Navigator = ComposeCustomNavigator()

    @Provides
    @Singleton
    fun provideResourceProvider(app: Application): ResourceProvider = RealResourceProvider(context = app)

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
    fun provideEditRecipeDiaryEntryUseCase(
        diaryRepository: DiaryRepository,
        resourceProvider: ResourceProvider
    ): EditRecipeDiaryEntryUseCase = EditRecipeDiaryEntryUseCase(diaryRepository = diaryRepository)

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
    fun provideAuthRepository(): AuthRepository = object : AuthRepository {
        override suspend fun logInUser(loginRequest: LoginRequest): Resource<TokenResponse> {
            TODO("Not yet implemented")
        }

        override suspend fun registerUser(registerRequest: RegisterRequest): Resource<Boolean> {
            TODO("Not yet implemented")
        }

        override suspend fun sendPasswordResetEmail(email: String): Resource<Boolean> {
            TODO("Not yet implemented")
        }
    }

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
    fun provideDiaryRepository(): DiaryRepository = MockDiaryRepository()

    @Singleton
    @Provides
    fun provideDeleteDiaryEntryUseCase(
        diaryRepository: DiaryRepository
    ): DeleteDiaryEntry = DeleteDiaryEntry(
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
    fun provideCachedValuesProvider(): CachedValuesProvider = object : CachedValuesProvider {
        override suspend fun getWantedNutritionValues() = NutritionValues()

        override suspend fun saveWantedNutritionValues(nutritionValues: NutritionValues) {
        }

        override suspend fun getWeightProgress() = ""

        override suspend fun updateWeightInfo(
            weightProgress: String?,
            latestWeightEntry: WeightEntry?
        ) {
        }

        override suspend fun getLogStreak(): Int = 1

        override suspend fun getLatestWeightEntry() = null

        override suspend fun getUser(): User = User()

        override suspend fun getUserCurrency() = Currency.PLN

        override suspend fun saveUser(user: User) {
        }

        override suspend fun updateWeightDialogs(weightDialogs: WeightDialogs) {
            TODO("Not yet implemented")
        }

        override suspend fun updateLocalLastTimeAskedForWeightDialogs(date: String) {
            TODO("Not yet implemented")
        }

        override suspend fun getLocalLastTimeAskedForWeightDialogs(): String? {
            TODO("Not yet implemented")
        }

        override suspend fun getLocalLastTimeShowedWeightPicker(): String? {
            TODO("Not yet implemented")
        }

        override suspend fun setLocalLastTimeShowedWeightPicker(date: String) {
            TODO("Not yet implemented")
        }

        override suspend fun updateUserInformation(userInformation: UserInformation) {
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
    fun provideWeightRepository(): WeightRepository = object : WeightRepository {
        override suspend fun addWeightEntry(
            newWeightEntryRequest: NewWeightEntryRequest,
            timezone: String
        ): Resource<NewWeightEntryResponse> {
            TODO("Not yet implemented")
        }

        override suspend fun checkIfShouldAskForWeightDialogs(): Resource<Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun handleWeightDialogsQuestion(weightDialogsRequest: WeightDialogsRequest): Resource<WeightDialogs> {
            TODO("Not yet implemented")
        }
    }

    @Singleton
    @Provides
    fun provideSummaryUseCases(
        diaryRepository: DiaryRepository,
        weightRepository: WeightRepository,
    ): SummaryUseCases = SummaryUseCases(
        getCaloriesSum = GetCaloriesSum(diaryRepository = diaryRepository),
        addWeightEntryUseCase = AddWeightEntryUseCase(
            weightRepository = weightRepository,
            checkIfWeightIsValidUseCase = CheckIfWeightIsValidUseCase()
        ),
        checkIfShouldAskForWeightDialogsUseCase = CheckIfShouldAskForWeightDialogsUseCase(weightRepository = weightRepository),
    )

    @Singleton
    @Provides
    fun provideSearchForProductsUseCase(diaryRepository: DiaryRepository): SearchForProductsUseCase
    = SearchForProductsUseCase(diaryRepository = diaryRepository)

    @Singleton
    @Provides
    fun provideGson(): Gson = Gson()

    @Singleton
    @Provides
    fun provideIntroductionRepository(): UserDataRepository = object : UserDataRepository {
        override suspend fun saveNutritionValues(nutritionValues: NutritionValues): Resource<Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun saveUserInformation(introductionRequest: IntroductionRequest): Resource<User> {
            TODO("Not yet implemented")
        }
    }

    @Singleton
    @Provides
    fun provideLoadingRepository(): LoadingRepository = object : LoadingRepository {
        override suspend fun authenticateUser(): Resource<User?> {
            TODO("Not yet implemented")
        }

    }

    @Singleton
    @Provides
    fun provideCalculateSelectedServingPriceUseCase(): CalculateSelectedServingPriceUseCase =
        CalculateSelectedServingPriceUseCase()

    @Singleton
    @Provides
    fun provideDiarySearchUseCases(
        diaryRepository: DiaryRepository,
        searchForProductsUseCase: SearchForProductsUseCase,
    ): SearchDiaryUseCases =
        SearchDiaryUseCases(
            searchForProductsUseCase = searchForProductsUseCase,
            searchForProductWithBarcode = SearchForProductWithBarcode(diaryRepository),
            searchForRecipes = SearchForRecipes(diaryRepository)
        )

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
    fun provideCreateSearchItemParamsFromRecipeUseCase(resourceProvider: ResourceProvider): CreateSearchItemParamsFromRecipeUseCase =
        CreateSearchItemParamsFromRecipeUseCase(resourceProvider = resourceProvider)

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