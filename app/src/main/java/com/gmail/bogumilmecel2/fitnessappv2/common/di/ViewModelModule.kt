package com.gmail.bogumilmecel2.fitnessappv2.common.di

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.repository.TokenRepository
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case.CalculateNutritionValuesPercentages
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case.CheckIfWeightIsValidUseCase
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case.GetToken
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case.GetUserCurrencyUseCase
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case.SaveNutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.feature_account.domain.use_case.AccountUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_account.domain.use_case.DeleteTokenUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_account.domain.use_case.EditNutritionGoalUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CalculateSelectedServingPriceUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CalculateServingPrice
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CalculateSkipUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CreateSearchItemParamsFromIngredientUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CreateSearchItemParamsFromProductDiaryEntryUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CreateSearchItemParamsFromProductUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CreateSearchItemParamsFromRecipeUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GetDiaryHistoryUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GetPriceUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GetRecipePriceFromIngredientsUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GetUserDiaryAndSaveItLocallyUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GetUserDiaryEntriesExperimentalUseCase
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
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search.GetLocalUserProductsUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search.GetLocalUserRecipesUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search.SearchDiaryUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search.SearchForProductWithBarcode
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search.SearchForProductsUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search.SearchForRecipes
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.repository.UserDataRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.use_cases.SaveIntroductionInformationUseCase
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
    fun provideGetUserDiaryEntriesExperimentalUseCase(
        diaryRepository: DiaryRepository
    ): GetUserDiaryEntriesExperimentalUseCase =
        GetUserDiaryEntriesExperimentalUseCase(diaryRepository)

    @ViewModelScoped
    @Provides
    fun provideGetUserDiaryAndSaveItLocallyUseCase(
        diaryRepository: DiaryRepository
    ): GetUserDiaryAndSaveItLocallyUseCase =
        GetUserDiaryAndSaveItLocallyUseCase(diaryRepository)

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
        searchForProductsUseCase: SearchForProductsUseCase,
        resourceProvider: ResourceProvider,
        createSearchItemParamsFromProductUseCase: CreateSearchItemParamsFromProductUseCase,
        getProductUseCase: GetProductUseCase,
        getRecipeUseCase: GetRecipeUseCase,
        cachedValuesProvider: CachedValuesProvider,
        calculateSkipUseCase: CalculateSkipUseCase
    ): SearchDiaryUseCases =
        SearchDiaryUseCases(
            searchForProductsUseCase = searchForProductsUseCase,
            searchForProductWithBarcode = SearchForProductWithBarcode(diaryRepository),
            searchForRecipes = SearchForRecipes(diaryRepository),
            createSearchItemParamsFromRecipeUseCase = CreateSearchItemParamsFromRecipeUseCase(resourceProvider),
            createSearchItemParamsFromProductUseCase = createSearchItemParamsFromProductUseCase,
            createSearchItemParamsFromProductDiaryEntryUseCase = CreateSearchItemParamsFromProductDiaryEntryUseCase(resourceProvider),
            getDiaryHistoryUseCase = GetDiaryHistoryUseCase(diaryRepository),
            getProductUseCase = getProductUseCase,
            getRecipeUseCase = getRecipeUseCase,
            shouldDisplayNextPageUseCase = ShouldDisplayNextPageUseCase(),
            getLocalUserRecipesUseCase = GetLocalUserRecipesUseCase(
                cachedValuesProvider = cachedValuesProvider,
                diaryRepository = diaryRepository,
                calculateSkipUseCase = calculateSkipUseCase
            ),
            getLocalUserProductsUseCase = GetLocalUserProductsUseCase(
                cachedValuesProvider = cachedValuesProvider,
                diaryRepository = diaryRepository,
                calculateSkipUseCase = calculateSkipUseCase
            )
        )

    @ViewModelScoped
    @Provides
    fun provideDiaryUseCases(
        diaryRepository: DiaryRepository,
        resourceProvider: ResourceProvider,
        getProductUseCase: GetProductUseCase,
        getRecipeUseCase: GetRecipeUseCase
    ): DiaryUseCases = DiaryUseCases(
        getOfflineDiaryEntriesUseCase = GetOfflineDiaryEntriesUseCase(diaryRepository = diaryRepository),
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
        calculateProductNutritionValuesUseCase: CalculateProductNutritionValuesUseCase
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
            )
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
    fun provideGetTokenUseCase(tokenRepository: TokenRepository): GetToken = GetToken(
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
        weightRepository: WeightRepository,
        cachedValuesProvider: CachedValuesProvider,
        saveAskForWeightDailyUseCase: SaveAskForWeightDailyUseCase
    ): SummaryUseCases = SummaryUseCases(
        getCaloriesSum = GetCaloriesSum(diaryRepository = diaryRepository),
        addWeightEntryUseCase = AddWeightEntryUseCase(
            weightRepository = weightRepository,
            checkIfWeightIsValidUseCase = CheckIfWeightIsValidUseCase(),
        ),
        checkIfShouldAskForWeightDialogsUseCase = CheckIfShouldAskForWeightDialogsUseCase(),
        handleWeightDialogsQuestionUseCase = HandleWeightDialogsQuestionUseCase(
            cachedValuesProvider = cachedValuesProvider,
            saveAskForWeightDaily = saveAskForWeightDailyUseCase
        ),
        checkIfShouldShowWeightPickerUseCase = CheckIfShouldShowWeightPickerUseCase(cachedValuesProvider)
    )
}