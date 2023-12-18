package com.gmail.bogumilmecel2.fitnessappv2.common.di

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CalculateSelectedServingPriceUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CalculateServingPrice
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CreateSearchItemParamsFromHistoryItemUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CreateSearchItemParamsFromIngredientUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CreateSearchItemParamsFromProductUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CreateSearchItemParamsFromRecipeUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GetRecipePriceFromIngredientsUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GetUserDiaryAndSaveItLocallyUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary.GetDiaryEntriesListFromResponseUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary.GetDiaryEntriesUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary.SortDiaryEntriesUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.new_product.SaveNewProductUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.new_recipe.AddNewRecipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.new_recipe.CalculateRecipeNutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.new_recipe.NewRecipeUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.CalculateProductNutritionValuesUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.CreatePieChartData
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.GetProductUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.recipe.EditRecipeDiaryEntryUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.recipe.GetRecipeUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.recipe.PostRecipeDiaryEntryUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.recipe.RecipeUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search.SearchDiaryUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search.SearchForProductWithBarcode
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search.SearchForProductsUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search.SearchForRecipes
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
    fun provideGetDiaryEntriesListFromResponseUseCase():
            GetDiaryEntriesListFromResponseUseCase = GetDiaryEntriesListFromResponseUseCase()

    @ViewModelScoped
    @Provides
    fun provideGetDiaryEntriesUseCase(
        diaryRepository: DiaryRepository,
        getDiaryEntriesListFromResponseUseCase: GetDiaryEntriesListFromResponseUseCase
    ): GetDiaryEntriesUseCase = GetDiaryEntriesUseCase(
        diaryRepository = diaryRepository,
        sortDiaryEntriesUseCase = SortDiaryEntriesUseCase(),
        getDiaryEntriesListFromResponseUseCase = getDiaryEntriesListFromResponseUseCase
    )

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
    fun provideGetRecipeUseCase(diaryRepository: DiaryRepository): GetRecipeUseCase =
        GetRecipeUseCase(diaryRepository)

    @ViewModelScoped
    @Provides
    fun provideGetProductUseCase(
        diaryRepository: DiaryRepository
    ): GetProductUseCase = GetProductUseCase(diaryRepository)

    @ViewModelScoped
    @Provides
    fun provideGetUserDiaryAndSaveItLocallyUseCase(
        diaryRepository: DiaryRepository
    ): GetUserDiaryAndSaveItLocallyUseCase =
        GetUserDiaryAndSaveItLocallyUseCase(diaryRepository)

    @ViewModelScoped
    @Provides
    fun provideNewRecipeUseCases(
        addNewRecipe: AddNewRecipe,
        calculateRecipeNutritionValues: CalculateRecipeNutritionValues,
        createPieChartData: CreatePieChartData,
        searchForProductsUseCase: SearchForProductsUseCase,
        calculateProductNutritionValuesUseCase: CalculateProductNutritionValuesUseCase,
        getRecipePriceFromIngredientsUseCase: GetRecipePriceFromIngredientsUseCase,
        calculateServingPrice: CalculateServingPrice,
        createSearchItemParamsFromIngredientUseCase: CreateSearchItemParamsFromIngredientUseCase,
        createSearchItemParamsFromProductUseCase: CreateSearchItemParamsFromProductUseCase
    ): NewRecipeUseCases = NewRecipeUseCases(
        addNewRecipe = addNewRecipe,
        calculateRecipeNutritionValues = calculateRecipeNutritionValues,
        createPieChartData = createPieChartData,
        searchForProductsUseCase = searchForProductsUseCase,
        calculateProductNutritionValuesUseCase = calculateProductNutritionValuesUseCase,
        getRecipePriceFromIngredientsUseCase = getRecipePriceFromIngredientsUseCase,
        calculateServingPrice = calculateServingPrice,
        createSearchItemParamsFromIngredientUseCase = createSearchItemParamsFromIngredientUseCase,
        createSearchItemParamsFromProductUseCase = createSearchItemParamsFromProductUseCase
    )

    @ViewModelScoped
    @Provides
    fun provideRecipeUseCases(
        createSearchItemParamsFromIngredientUseCase: CreateSearchItemParamsFromIngredientUseCase,
        createPieChartData: CreatePieChartData,
        diaryRepository: DiaryRepository,
        resourceProvider: ResourceProvider,
        getRecipePriceFromIngredientsUseCase: GetRecipePriceFromIngredientsUseCase,
        calculateSelectedServingPriceUseCase: CalculateSelectedServingPriceUseCase
    ): RecipeUseCases = RecipeUseCases(
        createPieChartData = createPieChartData,
        postRecipeDiaryEntryUseCase = PostRecipeDiaryEntryUseCase(
            diaryRepository = diaryRepository,
            resourceProvider = resourceProvider
        ),
        getRecipePriceFromIngredientsUseCase = getRecipePriceFromIngredientsUseCase,
        calculateSelectedServingPriceUseCase = calculateSelectedServingPriceUseCase,
        editRecipeDiaryEntryUseCase = EditRecipeDiaryEntryUseCase(diaryRepository = diaryRepository),
        createSearchItemParamsFromIngredientUseCase = createSearchItemParamsFromIngredientUseCase
    )

    @ViewModelScoped
    @Provides
    fun provideDiarySearchUseCases(
        diaryRepository: DiaryRepository,
        searchForProductsUseCase: SearchForProductsUseCase,
        resourceProvider: ResourceProvider,
        createSearchItemParamsFromProductUseCase: CreateSearchItemParamsFromProductUseCase
    ): SearchDiaryUseCases =
        SearchDiaryUseCases(
            searchForProductsUseCase = searchForProductsUseCase,
            searchForProductWithBarcode = SearchForProductWithBarcode(diaryRepository),
            searchForRecipes = SearchForRecipes(diaryRepository),
            createSearchItemParamsFromRecipeUseCase = CreateSearchItemParamsFromRecipeUseCase(resourceProvider),
            createSearchItemParamsFromProductUseCase = createSearchItemParamsFromProductUseCase,
            createSearchItemParamsFromHistoryItemUseCase = CreateSearchItemParamsFromHistoryItemUseCase(resourceProvider)
        )
}