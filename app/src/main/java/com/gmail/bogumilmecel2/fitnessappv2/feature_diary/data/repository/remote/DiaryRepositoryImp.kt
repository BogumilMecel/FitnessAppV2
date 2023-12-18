package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.data.repository.remote

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.Currency
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseRepository
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.data.api.DiaryApi
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.dao.UserDiaryItemsDao
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.DeleteDiaryEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.DiaryEntriesResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.EditProductDiaryEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.EditRecipeDiaryEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.ProductDiaryHistoryItem
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.ProductPrice
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.RecipePriceResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.UserDiaryItemsResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntryPostRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.product.NewPriceRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.product.NewProductRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.NewRecipeRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.RecipePriceRequest

class DiaryRepositoryImp(
    private val diaryApi: DiaryApi,
    private val userDiaryItemsDao: UserDiaryItemsDao
) : DiaryRepository, BaseRepository() {

    override suspend fun getDiaryEntries(date: String): Resource<DiaryEntriesResponse> {
        return handleRequest {
            diaryApi.getDiaryEntries(date = date)
        }
    }

    override suspend fun getDiaryEntriesExperimental(): Resource<DiaryEntriesResponse> {
        return handleRequest {
            diaryApi.getDiaryEntriesExperimental()
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

    override suspend fun insertProductDiaryEntry(productDiaryEntryPostRequest: ProductDiaryEntryPostRequest): Resource<Unit> {
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

    override suspend fun editProductDiaryEntry(editProductDiaryEntryRequest: EditProductDiaryEntryRequest): Resource<Unit> {
        return handleRequest {
            diaryApi.editProductDiaryEntry(editProductDiaryEntryRequest = editProductDiaryEntryRequest)
        }
    }

    override suspend fun editRecipeDiaryEntry(editRecipeDiaryEntryRequest: EditRecipeDiaryEntryRequest): Resource<Unit> {
        return handleRequest {
            diaryApi.editRecipeDiaryEntry(editRecipeDiaryEntryRequest = editRecipeDiaryEntryRequest)
        }
    }

    override suspend fun getCaloriesSum(date: String): Resource<Int> {
        return handleRequest {
            diaryApi.getCaloriesSum(date = date).caloriesSum
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

    override suspend fun addNewRecipe(newRecipeRequest: NewRecipeRequest): Resource<Recipe> {
        return handleRequest {
            diaryApi.addNewRecipe(newRecipeRequest = newRecipeRequest)
        }
    }

    override suspend fun getRecipe(recipeId: String): Resource<Recipe?> {
        return handleRequest {
            diaryApi.getRecipe(recipeId)
        }
    }

    override suspend fun getUserDiaryItems(): Resource<UserDiaryItemsResponse> {
        return handleRequest {
            diaryApi.getUserDiaryItems()
        }
    }

    override suspend fun insertUserProductsLocally(userProducts: List<Product>): Resource<Unit> {
        return handleRequest {
            userDiaryItemsDao.insertUserProducts(userProducts)
        }
    }

    override suspend fun insertUserRecipesLocally(userRecipes: List<Recipe>): Resource<Unit> {
        return handleRequest {
            userDiaryItemsDao.insertUserRecipes(userRecipes)
        }
    }

    override suspend fun getLocalUserRecipes(): Resource<List<Recipe>> {
        return handleRequest {
            userDiaryItemsDao.getUserRecipes()
        }
    }

    override suspend fun getLocalUserProducts(): Resource<List<Product>> {
        return handleRequest {
            userDiaryItemsDao.getUserProducts()
        }
    }

    override suspend fun getOnlineDiaryHistory(
        page: Int,
        searchText: String?
    ): Resource<List<ProductDiaryHistoryItem>> {
        return handleRequest {
            diaryApi.getProductDiaryHistory(
                page = page,
                searchText = searchText
            )
        }
    }

    override suspend fun clearLocalData(): Resource<Unit> {
        return handleRequest {
            userDiaryItemsDao.deleteUserProducts()
            userDiaryItemsDao.deleteUserRecipes()
        }
    }

    override suspend fun getOfflineDiaryEntries(date: String): Resource<DiaryEntriesResponse> {
        return handleRequest {
            DiaryEntriesResponse(
                productDiaryEntries = userDiaryItemsDao.getProductDiaryEntries(date),
                recipeDiaryEntries = userDiaryItemsDao.getRecipeDiaryEntries(date)
            )
        }
    }

    override suspend fun getDiaryEntriesCount(): Resource<Int> {
        return handleRequest {
            userDiaryItemsDao.getRecipeDiaryEntryCount() + userDiaryItemsDao.getProductDiaryEntryCount()
        }
    }

    override suspend fun getDiaryEntriesComplete(): Resource<DiaryEntriesResponse> {
        return handleRequest {
            diaryApi.getDiaryEntriesComplete()
        }
    }
}