package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.data.repository.remote

import com.gmail.bodziowaty6978.fitnessappv2.common.util.BaseRepository
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.data.api.DiaryApi
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Price
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntryPostRequest
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.product.NewPriceRequest
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.product.NewProductRequest
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.NewRecipeRequest
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntryRequest
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class DiaryRepositoryImp(
    private val diaryApi: DiaryApi, private val resourceProvider: ResourceProvider
) : DiaryRepository, BaseRepository(resourceProvider) {

    override suspend fun getDiaryEntries(date: String): Resource<List<ProductDiaryEntry>> {
        return handleRequest {
            diaryApi.getDiaryEntries(date = date)
        }
    }

    override suspend fun getProductHistory(): Resource<List<Product>> {
        return handleRequest {
            diaryApi.getProductHistory()
        }
    }

    override suspend fun searchForProducts(searchText: String): Resource<List<Product>> {
        return handleRequest {
            diaryApi.searchForProducts(searchText = searchText)
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

    override suspend fun addProductDiaryEntry(productDiaryEntryPostRequest: ProductDiaryEntryPostRequest): Resource<ProductDiaryEntry> {
        return handleRequest {
            diaryApi.insertProductDiaryEntry(productDiaryEntryPostRequest = productDiaryEntryPostRequest)
        }
    }

    override suspend fun addRecipeDiaryEntry(recipeDiaryEntryRequest: RecipeDiaryEntryRequest): Resource<Unit> {
        return handleRequest {
            diaryApi.insertRecipeDiaryEntry(recipeDiaryEntryRequest = recipeDiaryEntryRequest)
        }
    }

    override suspend fun saveNewProduct(newProductRequest: NewProductRequest): Resource<Product> {
        return handleRequest {
            diaryApi.insertProduct(newProductRequest = newProductRequest)
        }
    }

    override suspend fun deleteDiaryEntry(
        diaryEntryId: String
    ): Resource<Unit> {
        return handleRequest {
            diaryApi.deleteDiaryEntry(entryId = diaryEntryId)
        }
    }

    override suspend fun editDiaryEntry(productDiaryEntry: ProductDiaryEntry): Resource<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getCaloriesSum(date: String): Resource<Int> {
        return handleRequest {
            diaryApi.getCaloriesSum(date = date).caloriesSum
        }
    }

    override suspend fun addNewPrice(newPriceRequest: NewPriceRequest): Resource<Price> {
        return handleRequest {
            diaryApi.addNewPriceForProduct(newPriceRequest = newPriceRequest)
        }
    }

    override suspend fun addNewRecipe(newRecipeRequest: NewRecipeRequest): Resource<Recipe> {
        return handleRequest {
            diaryApi.addNewRecipe(newRecipeRequest = newRecipeRequest)
        }
    }
}