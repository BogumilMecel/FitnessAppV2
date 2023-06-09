package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.data

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.Currency
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.DeleteDiaryEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.DiaryEntriesResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.EditProductDiaryEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.EditRecipeDiaryEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.ProductPrice
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.RecipePriceResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntryPostRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.product.NewPriceRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.product.NewProductRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.NewRecipeRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.RecipePriceRequest

class MockDiaryRepository: DiaryRepository {
    override suspend fun getDiaryEntries(date: String) = Resource.Success(
        data = DiaryEntriesResponse(
            productDiaryEntries = emptyList(),
            recipeDiaryEntries = emptyList()
        )
    )

    override suspend fun searchForProducts(searchText: String) = Resource.Success(
        data = emptyList<Product>()
    )

    override suspend fun searchForProductWithBarcode(barcode: String) = Resource.Success<Product?>(
        data = null
    )

    override suspend fun searchForRecipes(searchText: String) = Resource.Success(
        data = emptyList<Recipe>()
    )

    override suspend fun getProductHistory() = Resource.Success(
        data = emptyList<Product>()
    )

    override suspend fun addProductDiaryEntry(productDiaryEntryPostRequest: ProductDiaryEntryPostRequest) = Resource.Success(
        data = ProductDiaryEntry(id = "")
    )

    override suspend fun addRecipeDiaryEntry(recipeDiaryEntryRequest: RecipeDiaryEntryRequest) = Resource.Success(Unit)

    override suspend fun deleteDiaryEntry(deleteDiaryEntryRequest: DeleteDiaryEntryRequest) = Resource.Success(Unit)

    override suspend fun editProductDiaryEntry(editProductDiaryEntryRequest: EditProductDiaryEntryRequest) = Resource.Success(Unit)

    override suspend fun editRecipeDiaryEntry(editRecipeDiaryEntryRequest: EditRecipeDiaryEntryRequest) = Resource.Success(Unit)

    override suspend fun saveNewProduct(newProductRequest: NewProductRequest) = Resource.Success(
        data = Product()
    )

    override suspend fun getCaloriesSum(date: String) = Resource.Success(data = 0)

    override suspend fun getPrice(productId: String, currency: Currency) = Resource.Success<ProductPrice?>(
        data = null
    )

    override suspend fun getRecipePriceFromIngredients(
        recipePriceRequest: RecipePriceRequest,
        currency: Currency
    ) = Resource.Success<RecipePriceResponse?>(
        data = null
    )

    override suspend fun submitNewPrice(
        newPriceRequest: NewPriceRequest,
        productId: String,
        currency: Currency
    ) = Resource.Success(
        data = ProductPrice(
            valueFor100g = 0.0,
            valueFor100Calories = null,
            valueFor100Carbohydrates = null,
            valueFor10Protein = null,
            valueFor10Fat = null
        )
    )

    override suspend fun addNewRecipe(newRecipeRequest: NewRecipeRequest) = Resource.Success(
        data = Recipe()
    )
}