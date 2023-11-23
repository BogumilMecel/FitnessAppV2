package com.gmail.bogumilmecel2.fitnessappv2.common.di

import com.gmail.bogumilmecel2.auth.ValidateAuthDataUseCase
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.repository.TokenRepository
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case.CalculateNutritionValuesPercentages
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case.CheckIfWeightIsValidUseCase
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case.GetTokenUseCase
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case.GetUserCurrencyUseCase
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case.SaveNutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.feature_account.domain.use_case.AccountUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_account.domain.use_case.DeleteTokenUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_account.domain.use_case.EditNutritionGoalUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.repository.AuthRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.use_case.LogInUserUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.use_case.RegisterUserUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.use_case.ResetPasswordWithEmail
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.presentation.register.RegisterUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.OfflineDiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CalculateSelectedServingPriceUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CalculateServingPrice
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CalculateSkipUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CreateSearchItemParamsFromIngredientUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CreateSearchItemParamsFromProductDiaryEntryUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CreateSearchItemParamsFromProductUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CreateSearchItemParamsFromRecipeUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GenerateNewRecipeSearchTitleUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GetDiaryHistoryUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GetPriceUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GetProductDiaryAndSaveOfflineUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GetProductsAndSaveOfflineUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GetRecipeDiaryAndSaveOfflineUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GetRecipePriceFromIngredientsUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GetRecipesAndSaveOfflineUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.ShouldDisplayNextPageUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary.CreateLongClickedDiaryItemParamsUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary.DeleteDiaryEntryUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary.DiaryUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary.GetOfflineDiaryEntriesUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary.GetOnlineDiaryEntriesUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary.SumNutritionValuesUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.new_product.SaveNewProductUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.new_recipe.AddNewRecipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.new_recipe.CalculateRecipeNutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.new_recipe.NewRecipeUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.CalculateProductNutritionValuesUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.CreatePieChartDataUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.EditProductDiaryEntryUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.GetProductUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.InsertProductDiaryEntryUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.ProductUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.SubmitNewPriceUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.recipe.CalculateRecipeNutritionValuesForServingsUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.recipe.EditRecipeDiaryEntryUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.recipe.GetRecipeUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.recipe.PostRecipeDiaryEntryUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.recipe.RecipeUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search.GetOfflineUserProductsUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search.GetOfflineUserRecipesUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search.SearchDiaryUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search.SearchForProductWithBarcode
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search.SearchForProductsUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search.SearchForRecipes
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.repository.UserDataRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.use_cases.SaveIntroductionInformationUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_splash.domain.LoadingUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_splash.domain.repository.LoadingRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_splash.domain.use_cases.AuthenticateUserUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.use_case.CheckIfShouldAskForWeightDialogsUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.use_case.CheckIfShouldShowWeightPickerUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.use_case.GetCaloriesSum
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.use_case.HandleWeightDialogsQuestionUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.use_case.SaveAskForWeightDailyUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.use_case.SummaryUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.repository.WeightRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.use_case.AddWeightEntryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @ViewModelScoped
    @Provides
    fun provideSaveNewProductUseCase(
        diaryRepository: DiaryRepository,
        resourceProvider: ResourceProvider
    ): SaveNewProductUseCase = SaveNewProductUseCase(
        diaryRepository = diaryRepository,
        resourceProvider = resourceProvider
    )

    @ViewModelScoped
    @Provides
    fun provideNewRecipeUseCases(
        createPieChartDataUseCase: CreatePieChartDataUseCase,
        searchForProductsUseCase: SearchForProductsUseCase,
        calculateProductNutritionValuesUseCase: CalculateProductNutritionValuesUseCase,
        getRecipePriceFromIngredientsUseCase: GetRecipePriceFromIngredientsUseCase,
        createSearchItemParamsFromIngredientUseCase: CreateSearchItemParamsFromIngredientUseCase,
        createSearchItemParamsFromProductUseCase: CreateSearchItemParamsFromProductUseCase,
        diaryRepository: DiaryRepository,
        resourceProvider: ResourceProvider
    ): NewRecipeUseCases = NewRecipeUseCases(
        addNewRecipe = AddNewRecipe(
            diaryRepository = diaryRepository,
            resourceProvider = resourceProvider
        ),
        calculateRecipeNutritionValues = CalculateRecipeNutritionValues(),
        createPieChartDataUseCase = createPieChartDataUseCase,
        searchForProductsUseCase = searchForProductsUseCase,
        calculateProductNutritionValuesUseCase = calculateProductNutritionValuesUseCase,
        getRecipePriceFromIngredientsUseCase = getRecipePriceFromIngredientsUseCase,
        calculateServingPrice = CalculateServingPrice(),
        createSearchItemParamsFromIngredientUseCase = createSearchItemParamsFromIngredientUseCase,
        createSearchItemParamsFromProductUseCase = createSearchItemParamsFromProductUseCase
    )

    @ViewModelScoped
    @Provides
    fun provideRecipeUseCases(
        createSearchItemParamsFromIngredientUseCase: CreateSearchItemParamsFromIngredientUseCase,
        createPieChartDataUseCase: CreatePieChartDataUseCase,
        diaryRepository: DiaryRepository,
        resourceProvider: ResourceProvider,
        getRecipePriceFromIngredientsUseCase: GetRecipePriceFromIngredientsUseCase,
        calculateRecipeNutritionValuesForServingsUseCase: CalculateRecipeNutritionValuesForServingsUseCase
    ): RecipeUseCases = RecipeUseCases(
        createPieChartDataUseCase = createPieChartDataUseCase,
        postRecipeDiaryEntryUseCase = PostRecipeDiaryEntryUseCase(
            diaryRepository = diaryRepository,
            resourceProvider = resourceProvider,
            calculateRecipeNutritionValuesForServingsUseCase = calculateRecipeNutritionValuesForServingsUseCase
        ),
        getRecipePriceFromIngredientsUseCase = getRecipePriceFromIngredientsUseCase,
        calculateSelectedServingPriceUseCase = CalculateSelectedServingPriceUseCase(),
        editRecipeDiaryEntryUseCase = EditRecipeDiaryEntryUseCase(
            diaryRepository = diaryRepository,
            calculateRecipeNutritionValuesForServingsUseCase = calculateRecipeNutritionValuesForServingsUseCase
        ),
        createSearchItemParamsFromIngredientUseCase = createSearchItemParamsFromIngredientUseCase
    )

    @ViewModelScoped
    @Provides
    fun provideDiarySearchUseCases(
        diaryRepository: DiaryRepository,
        offlineDiaryRepository: OfflineDiaryRepository,
        searchForProductsUseCase: SearchForProductsUseCase,
        resourceProvider: ResourceProvider,
        createSearchItemParamsFromProductUseCase: CreateSearchItemParamsFromProductUseCase,
        getProductUseCase: GetProductUseCase,
        getRecipeUseCase: GetRecipeUseCase,
        cachedValuesProvider: CachedValuesProvider,
        calculateSkipUseCase: CalculateSkipUseCase,
        generateNewRecipeSearchTitleUseCase: GenerateNewRecipeSearchTitleUseCase
    ): SearchDiaryUseCases =
        SearchDiaryUseCases(
            searchForProductsUseCase = searchForProductsUseCase,
            searchForProductWithBarcode = SearchForProductWithBarcode(diaryRepository),
            searchForRecipes = SearchForRecipes(diaryRepository),
            createSearchItemParamsFromRecipeUseCase = CreateSearchItemParamsFromRecipeUseCase(resourceProvider),
            createSearchItemParamsFromProductUseCase = createSearchItemParamsFromProductUseCase,
            createSearchItemParamsFromProductDiaryEntryUseCase = CreateSearchItemParamsFromProductDiaryEntryUseCase(resourceProvider),
            getDiaryHistoryUseCase = GetDiaryHistoryUseCase(offlineDiaryRepository),
            getProductUseCase = getProductUseCase,
            getRecipeUseCase = getRecipeUseCase,
            shouldDisplayNextPageUseCase = ShouldDisplayNextPageUseCase(),
            getOfflineUserRecipesUseCase = GetOfflineUserRecipesUseCase(
                cachedValuesProvider = cachedValuesProvider,
                offlineDiaryRepository = offlineDiaryRepository,
                calculateSkipUseCase = calculateSkipUseCase
            ),
            getOfflineUserProductsUseCase = GetOfflineUserProductsUseCase(
                cachedValuesProvider = cachedValuesProvider,
                diaryRepository = offlineDiaryRepository,
                calculateSkipUseCase = calculateSkipUseCase
            ),
            generateNewRecipeSearchTitleUseCase = generateNewRecipeSearchTitleUseCase
        )

    @ViewModelScoped
    @Provides
    fun provideDiaryUseCases(
        diaryRepository: DiaryRepository,
        offlineDiaryRepository: OfflineDiaryRepository,
        resourceProvider: ResourceProvider,
        getProductUseCase: GetProductUseCase,
        getRecipeUseCase: GetRecipeUseCase
    ): DiaryUseCases = DiaryUseCases(
        getOfflineDiaryEntriesUseCase = GetOfflineDiaryEntriesUseCase(offlineDiaryRepository = offlineDiaryRepository),
        getOnlineDiaryEntriesUseCase = GetOnlineDiaryEntriesUseCase(diaryRepository = diaryRepository),
        deleteDiaryEntryUseCase = DeleteDiaryEntryUseCase(diaryRepository = diaryRepository),
        sumNutritionValuesUseCase = SumNutritionValuesUseCase(),
        createLongClickedDiaryItemParamsUseCase = CreateLongClickedDiaryItemParamsUseCase(
            resourceProvider = resourceProvider
        ),
        getProductUseCase = getProductUseCase,
        getRecipeUseCase = getRecipeUseCase
    )

    @ViewModelScoped
    @Provides
    fun provideProductUseCases(
        diaryRepository: DiaryRepository,
        resourceProvider: ResourceProvider,
        createPieChartDataUseCase: CreatePieChartDataUseCase,
        getUserCurrencyUseCase: GetUserCurrencyUseCase,
        calculateProductNutritionValuesUseCase: CalculateProductNutritionValuesUseCase,
        generateNewRecipeSearchTitleUseCase: GenerateNewRecipeSearchTitleUseCase
    ): ProductUseCases =
        ProductUseCases(
            calculateProductNutritionValuesUseCase = CalculateProductNutritionValuesUseCase(),
            createPieChartDataUseCase = createPieChartDataUseCase,
            insertProductDiaryEntryUseCase = InsertProductDiaryEntryUseCase(
                diaryRepository = diaryRepository,
                resourceProvider = resourceProvider,
                calculateProductNutritionValuesUseCase = calculateProductNutritionValuesUseCase
            ),
            submitNewPriceUseCase = SubmitNewPriceUseCase(
                diaryRepository = diaryRepository,
                resourceProvider = resourceProvider,
                getUserCurrencyUseCase = getUserCurrencyUseCase
            ),
            getPriceUseCase = GetPriceUseCase(
                diaryRepository = diaryRepository,
                getUserCurrency = getUserCurrencyUseCase
            ),
            editProductDiaryEntryUseCase = EditProductDiaryEntryUseCase(
                diaryRepository = diaryRepository,
                calculateProductNutritionValuesUseCase = calculateProductNutritionValuesUseCase
            ),
            generateNewRecipeSearchTitleUseCase = generateNewRecipeSearchTitleUseCase
        )

    @ViewModelScoped
    @Provides
    fun provideAccountUseCases(
        tokenRepository: TokenRepository,
        createPieChartDataUseCase: CreatePieChartDataUseCase,
        saveAskForWeightDailyUseCase: SaveAskForWeightDailyUseCase
    ): AccountUseCases = AccountUseCases(
        deleteTokenUseCase = DeleteTokenUseCase(tokenRepository = tokenRepository),
        createPieChartDataUseCase = createPieChartDataUseCase,
        saveAskForWeightDailyUseCase = saveAskForWeightDailyUseCase
    )

    @ViewModelScoped
    @Provides
    fun provideGetTokenUseCase(tokenRepository: TokenRepository): GetTokenUseCase = GetTokenUseCase(
        tokenRepository = tokenRepository
    )

    @ViewModelScoped
    @Provides
    fun provideEditNutritionGoalsUseCases(userDataRepository: UserDataRepository): EditNutritionGoalUseCases =
        EditNutritionGoalUseCases(
            calculateNutritionValuesPercentages = CalculateNutritionValuesPercentages(),
            saveNutritionValues = SaveNutritionValues(userDataRepository = userDataRepository),
        )

    @ViewModelScoped
    @Provides
    fun provideSaveInformationUseCase(
        userDataRepository: UserDataRepository,
        resourceProvider: ResourceProvider,
    ): SaveIntroductionInformationUseCase = SaveIntroductionInformationUseCase(
        userDataRepository = userDataRepository,
        resourceProvider = resourceProvider
    )

    @ViewModelScoped
    @Provides
    fun provideSummaryUseCases(
        diaryRepository: DiaryRepository,
        cachedValuesProvider: CachedValuesProvider,
        saveAskForWeightDailyUseCase: SaveAskForWeightDailyUseCase
    ): SummaryUseCases = SummaryUseCases(
        getCaloriesSum = GetCaloriesSum(diaryRepository = diaryRepository),
        checkIfShouldAskForWeightDialogsUseCase = CheckIfShouldAskForWeightDialogsUseCase(),
        handleWeightDialogsQuestionUseCase = HandleWeightDialogsQuestionUseCase(
            cachedValuesProvider = cachedValuesProvider,
            saveAskForWeightDaily = saveAskForWeightDailyUseCase
        ),
        checkIfShouldShowWeightPickerUseCase = CheckIfShouldShowWeightPickerUseCase(cachedValuesProvider)
    )

    @ViewModelScoped
    @Provides
    fun provideRegisterUseCases(
        authRepository: AuthRepository,
        resourceProvider: ResourceProvider,
        logInUserUseCase: LogInUserUseCase,
        validateAuthDataUseCase: ValidateAuthDataUseCase
    ): RegisterUseCases = RegisterUseCases(
        registerUserUseCase = RegisterUserUseCase(
            authRepository = authRepository,
            resourceProvider = resourceProvider,
            validateAuthDataUseCase = validateAuthDataUseCase
        ),
        logInUserUseCase = logInUserUseCase
    )

    @ViewModelScoped
    @Provides
    fun provideResetPasswordWithEmailUseCase(
        authRepository: AuthRepository,
        resourceProvider: ResourceProvider
    ): ResetPasswordWithEmail = ResetPasswordWithEmail(
        repository = authRepository,
        resourceProvider = resourceProvider
    )

    @ViewModelScoped
    @Provides
    fun provideAuthenticateUserUseCase(
        cachedValuesProvider: CachedValuesProvider,
        loadingRepository: LoadingRepository,
        tokenRepository: TokenRepository,
    ): AuthenticateUserUseCase = AuthenticateUserUseCase(
        cachedValuesProvider = cachedValuesProvider,
        loadingRepository = loadingRepository,
        tokenRepository = tokenRepository,
    )

    @ViewModelScoped
    @Provides
    fun provideAddWeightEntryUseCase(
        weightRepository: WeightRepository,
        cachedValuesProvider: CachedValuesProvider,
    ): AddWeightEntryUseCase = AddWeightEntryUseCase(
        weightRepository = weightRepository,
        cachedValuesProvider = cachedValuesProvider,
        checkIfWeightIsValidUseCase = CheckIfWeightIsValidUseCase()
    )

    @ViewModelScoped
    @Provides
    fun provideLoadingUseCases(
        cachedValuesProvider: CachedValuesProvider,
        loadingRepository: LoadingRepository,
        tokenRepository: TokenRepository,
        diaryRepository: DiaryRepository,
        offlineDiaryRepository: OfflineDiaryRepository
    ): LoadingUseCases = LoadingUseCases(
        authenticateUserUseCase = AuthenticateUserUseCase(
            cachedValuesProvider = cachedValuesProvider,
            loadingRepository = loadingRepository,
            tokenRepository = tokenRepository
        ),
        getProductsAndSaveOfflineUseCase = GetProductsAndSaveOfflineUseCase(
            diaryRepository = diaryRepository,
            cachedValuesProvider = cachedValuesProvider,
            offlineDiaryRepository = offlineDiaryRepository
        ),
        getRecipesAndSaveOfflineUseCase = GetRecipesAndSaveOfflineUseCase(
            cachedValuesProvider = cachedValuesProvider,
            offlineDiaryRepository = offlineDiaryRepository,
            diaryRepository = diaryRepository
        ),
        getProductDiaryAndSaveOfflineUseCase = GetProductDiaryAndSaveOfflineUseCase(
            diaryRepository = diaryRepository,
            cachedValuesProvider = cachedValuesProvider,
            offlineDiaryRepository = offlineDiaryRepository
        ),
        getRecipeDiaryAndSaveOfflineUseCase = GetRecipeDiaryAndSaveOfflineUseCase(
            cachedValuesProvider = cachedValuesProvider,
            offlineDiaryRepository = offlineDiaryRepository,
            diaryRepository = diaryRepository
        )
    )
}