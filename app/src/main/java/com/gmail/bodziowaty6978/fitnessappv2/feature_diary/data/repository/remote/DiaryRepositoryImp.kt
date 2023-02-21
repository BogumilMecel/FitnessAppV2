package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.data.repository.remote

import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.util.BaseRepository
import com.gmail.bodziowaty6978.fitnessappv2.common.util.CustomResult
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.common.util.extensions.formatToString
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
import java.util.Date

class DiaryRepositoryImp(
    private val diaryApi: DiaryApi, private val resourceProvider: ResourceProvider
) : DiaryRepository, BaseRepository(resourceProvider) {

    override suspend fun getDiaryEntries(timestamp: Long): Resource<List<ProductDiaryEntry>> {
        return try {
            val entries = diaryApi.getDiaryEntries(date = Date(timestamp).formatToString())
            Resource.Success(entries)
        } catch (e: Exception) {
            handleExceptionWithResource(exception = e)
        }
    }

    override suspend fun getProductHistory(): Resource<List<Product>> {
        return try {
            val result = diaryApi.getProductHistory()
            Resource.Success(data = result)
        } catch (e: Exception) {
            handleExceptionWithResource(exception = e, data = emptyList())
        }
    }

    override suspend fun searchForProducts(searchText: String): Resource<List<Product>> {
        return try {
            val items = diaryApi.searchForProducts(searchText = searchText)
            return Resource.Success(data = items)
        } catch (e: Exception) {
            handleExceptionWithResource(exception = e, data = emptyList())
        }
    }

    override suspend fun searchForProductWithBarcode(barcode: String): Resource<Product> {
        return try {
            val product = diaryApi.searchForProductWithBarcode(barcode = barcode)
            if (product == null) Resource.Error(uiText = resourceProvider.getString(R.string.there_is_no_product_with_provided_barcode_do_you_want_to_add_it))
            else Resource.Success(data = product)
        } catch (e: Exception) {
            handleExceptionWithResource(exception = e)
        }
    }

    override suspend fun searchForRecipes(searchText: String): Resource<List<Recipe>> {
        return try {
            Resource.Success(data = diaryApi.searchForRecipes(searchText))
        } catch (e: Exception) {
            handleExceptionWithResource(exception = e)
        }
    }

    override suspend fun addProductDiaryEntry(productDiaryEntryPostRequest: ProductDiaryEntryPostRequest): Resource<ProductDiaryEntry> {
        return try {
            val newDiaryEntry =
                diaryApi.insertProductDiaryEntry(productDiaryEntryPostRequest = productDiaryEntryPostRequest)
            Resource.Success(data = newDiaryEntry)
        } catch (e: Exception) {
            handleExceptionWithResource(exception = e)
        }
    }

    override suspend fun addRecipeDiaryEntry(recipeDiaryEntryRequest: RecipeDiaryEntryRequest): Resource<Boolean> {
        return try {
            Resource.Success(data = diaryApi.insertRecipeDiaryEntry(recipeDiaryEntryRequest = recipeDiaryEntryRequest))
        } catch (e: Exception) {
            handleExceptionWithResource(exception = e)
        }
    }

    override suspend fun saveNewProduct(newProductRequest: NewProductRequest): Resource<Product> {
        return try {
            Resource.Success(data = diaryApi.insertProduct(newProductRequest = newProductRequest))
        } catch (e: Exception) {
            handleExceptionWithResource(exception = e)
        }
    }

    override suspend fun deleteDiaryEntry(
        diaryEntryId: String
    ): CustomResult {
        return try {
            val wasDeleted = diaryApi.deleteDiaryEntry(entryId = diaryEntryId)
            if (wasDeleted) CustomResult.Success else CustomResult.Error(
                message = resourceProvider.getString(
                    R.string.unknown_error
                )
            )
        } catch (e: Exception) {
            handleExceptionWithCustomResult(exception = e)
        }
    }

    override suspend fun editDiaryEntry(productDiaryEntry: ProductDiaryEntry): CustomResult {
        TODO("Not yet implemented")
    }

    override suspend fun getCaloriesSum(date: String): Resource<Int> {
        return try {
            Resource.Success(
                data = diaryApi.getCaloriesSum(date = date).caloriesSum
            )
        } catch (e: Exception) {
            handleExceptionWithResource(exception = e)
        }
    }

    override suspend fun addNewPrice(newPriceRequest: NewPriceRequest): Resource<Price> {
        return try {
            Resource.Success(
                data = diaryApi.addNewPriceForProduct(
                    newPriceRequest = newPriceRequest
                )
            )
        } catch (e: Exception) {
            handleExceptionWithResource(exception = e)
        }
    }

    override suspend fun addNewRecipe(newRecipeRequest: NewRecipeRequest): Resource<Recipe> {
        return try {
            Resource.Success(data = diaryApi.addNewRecipe(newRecipeRequest = newRecipeRequest))
        } catch (e: Exception) {
            handleExceptionWithResource(exception = e)
        }
    }
}