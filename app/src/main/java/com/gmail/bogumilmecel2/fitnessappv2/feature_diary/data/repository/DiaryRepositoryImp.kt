package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.data.repository

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.Currency
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseRepository
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.data.api.DiaryApi
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.DeleteDiaryEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.DiaryEntriesResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.ProductPrice
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.RecipePriceResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.product.NewPriceRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.RecipePriceRequest
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

class DiaryRepositoryImp(private val diaryApi: DiaryApi) : DiaryRepository, BaseRepository() {

    override suspend fun getDiaryEntries(date: LocalDate): Resource<DiaryEntriesResponse> {
        return handleRequest {
            diaryApi.getDiaryEntries(date = date)
        }
    }

    override suspend fun getProductDiaryEntries(latestDateTime: LocalDateTime?): Resource<List<ProductDiaryEntry>> {
        return handleRequest {
            diaryApi.getUserProductDiaryEntries(latestDateTime)
        }
    }

    override suspend fun getRecipeDiaryEntries(latestDateTime: LocalDateTime?): Resource<List<RecipeDiaryEntry>> {
        return handleRequest {
            diaryApi.getUserRecipeDiaryEntries(latestDateTime)
        }
    }

    override suspend fun searchForProducts(
        searchText: String,
        page: Int
    ): Resource<List<Product>> {
        return handleRequest {
            diaryApi.searchForProducts(
                searchText = searchText,
                page = page
            )
        }
    }

    override suspend fun searchForProductWithBarcode(barcode: String): Resource<Product?> {
        return handleRequest {
            diaryApi.searchForProductWithBarcode(barcode = barcode)
        }
    }

    override suspend fun searchForRecipes(searchText: String): Resource<List<Recipe>> {
        return handleRequest {
            diaryApi.searchForRecipes(searchText)
        }
    }

    override suspend fun insertProductDiaryEntry(productDiaryEntry: ProductDiaryEntry): Resource<ProductDiaryEntry> {
        return handleRequest {
            diaryApi.insertProductDiaryEntry(productDiaryEntry = productDiaryEntry)
        }
    }

    override suspend fun insertRecipeDiaryEntry(recipeDiaryEntry: RecipeDiaryEntry): Resource<RecipeDiaryEntry> {
        return handleRequest {
            diaryApi.insertRecipeDiaryEntry(recipeDiaryEntry = recipeDiaryEntry)
        }
    }

    override suspend fun saveNewProduct(product: Product): Resource<Product> {
        return handleRequest {
            diaryApi.insertProduct(product = product)
        }
    }

    override suspend fun getProduct(productId: String): Resource<Product?> {
        return handleRequest {
            diaryApi.getProduct(productId)
        }
    }

    override suspend fun deleteDiaryEntry(
        deleteDiaryEntryRequest: DeleteDiaryEntryRequest
    ): Resource<Unit> {
        return handleRequest {
            diaryApi.deleteDiaryEntry(deleteDiaryEntryRequest = deleteDiaryEntryRequest)
        }
    }

    override suspend fun editProductDiaryEntry(productDiaryEntry: ProductDiaryEntry): Resource<ProductDiaryEntry> {
        return handleRequest {
            diaryApi.editProductDiaryEntry(productDiaryEntry = productDiaryEntry)
        }
    }

    override suspend fun editRecipeDiaryEntry(recipeDiaryEntry: RecipeDiaryEntry): Resource<RecipeDiaryEntry> {
        return handleRequest {
            diaryApi.editRecipeDiaryEntry(recipeDiaryEntry = recipeDiaryEntry)
        }
    }

    override suspend fun getPrice(
        productId: String,
        currency: Currency
    ): Resource<ProductPrice?> {
        return handleRequest {
            diaryApi.getProductPrice(
                productId = productId,
                currency = currency
            ).productPrice
        }
    }

    override suspend fun submitNewPrice(
        newPriceRequest: NewPriceRequest,
        productId: String,
        currency: Currency
    ): Resource<ProductPrice> {
        return handleRequest {
            diaryApi.addNewPriceForProduct(
                newPriceRequest = newPriceRequest,
                productId = productId,
                currency = currency
            )
        }
    }

    override suspend fun getRecipePriceFromIngredients(
        recipePriceRequest: RecipePriceRequest,
        currency: Currency
    ): Resource<RecipePriceResponse?> {
        return handleRequest(shouldHandleException = false) {
            diaryApi.getRecipePriceFromIngredients(
                recipePriceRequest = recipePriceRequest,
                currency = currency
            )
        }
    }

    override suspend fun addNewRecipe(recipe: Recipe): Resource<Recipe> {
        return handleRequest {
            diaryApi.addNewRecipe(recipe = recipe)
        }
    }

    override suspend fun getRecipe(recipeId: String): Resource<Recipe?> {
        return handleRequest {
            diaryApi.getRecipe(recipeId)
        }
    }

    override suspend fun getUserProducts(latestDateTime: LocalDateTime?): Resource<List<Product>> {
        return handleRequest {
            diaryApi.getUserProducts(latestDateTime)
        }
    }

    override suspend fun getUserRecipes(latestDateTime: LocalDateTime?): Resource<List<Recipe>> {
        return handleRequest {
            diaryApi.getUserRecipes(latestDateTime)
        }
    }
}