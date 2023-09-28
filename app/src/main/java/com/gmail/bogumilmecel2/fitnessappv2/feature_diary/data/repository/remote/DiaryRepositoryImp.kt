package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.data.repository.remote

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.Currency
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseRepository
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.data.api.DiaryApi
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.dao.UserDiaryItemsDao
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.DeleteDiaryEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.DiaryEntriesResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.ProductPrice
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.RecipePriceResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.UserDiaryItemsResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntryPostRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.product.NewPriceRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.product.NewProductRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.NewRecipeRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntry
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

    override suspend fun insertProductDiaryEntry(productDiaryEntryPostRequest: ProductDiaryEntryPostRequest): Resource<ProductDiaryEntry> {
        return handleRequest {
            diaryApi.insertProductDiaryEntry(productDiaryEntryPostRequest = productDiaryEntryPostRequest)
        }
    }

    override suspend fun insertRecipeDiaryEntry(recipeDiaryEntryRequest: RecipeDiaryEntryRequest): Resource<RecipeDiaryEntry> {
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

    override suspend fun deleteOfflineDiaryEntries(
        date: String,
        productDiaryEntriesIds: List<String>,
        recipeDiaryEntriesIds: List<String>
    ): Resource<Unit> {
        return handleRequest {
            userDiaryItemsDao.deleteRecipeDiaryEntries(
                date = date,
                diaryEntriesIds = recipeDiaryEntriesIds
            )
            userDiaryItemsDao.deleteProductDiaryEntries(
                date = date,
                diaryEntriesIds = productDiaryEntriesIds
            )
        }
    }

    override suspend fun getOfflineDiaryEntries(limit: Int, offset: Int, searchText: String): Resource<List<ProductDiaryEntry>> {
        return handleRequest {
            userDiaryItemsDao.getProductDiaryEntriesForSearch(
                limit = limit,
                offset = offset,
                searchText = searchText
            )
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

    override suspend fun getDiaryEntriesNutritionValues(date: String): Resource<List<NutritionValues>> {
        return handleRequest {
            buildList {
                addAll(userDiaryItemsDao.getProductDiaryEntriesNutritionValues(date))
                addAll(userDiaryItemsDao.getRecipeDiaryEntriesNutritionValues(date))
            }
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

    override suspend fun getLocalUserRecipes(userId: String): Resource<List<Recipe>> {
        return handleRequest {
            userDiaryItemsDao.getUserRecipes(userId = userId)
        }
    }

    override suspend fun getLocalUserProducts(userId: String): Resource<List<Product>> {
        return handleRequest {
            userDiaryItemsDao.getUserProducts(userId = userId)
        }
    }

    override suspend fun clearLocalData(userId: String): Resource<Unit> {
        return handleRequest {
            userDiaryItemsDao.deleteUserProducts(userId)
            userDiaryItemsDao.deleteUserRecipes(userId)
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

    override suspend fun insertOfflineDiaryEntries(diaryEntriesResponse: DiaryEntriesResponse): Resource<Unit> {
        return handleRequest {
            with(diaryEntriesResponse) {
                userDiaryItemsDao.insertRecipeDiaryEntries(recipeDiaryEntries)
                userDiaryItemsDao.insertProductDiaryEntries(productDiaryEntries)
            }
        }
    }

    override suspend fun insertOfflineDiaryEntry(diaryItem: DiaryItem): Resource<Unit> {
        return handleRequest {
            when(diaryItem) {
                is ProductDiaryEntry -> {
                    userDiaryItemsDao.insertProductDiaryEntry(diaryItem)
                }

                is RecipeDiaryEntry -> {
                    userDiaryItemsDao.insertRecipeDiaryEntry(diaryItem)
                }
            }
        }
    }

    override suspend fun cacheProduct(product: Product): Resource<Unit> {
        return handleRequest {
            userDiaryItemsDao.insertProduct(product)
        }
    }

    override suspend fun cacheRecipe(recipe: Recipe): Resource<Unit> {
        return handleRequest {
            userDiaryItemsDao.insertRecipe(recipe)
        }
    }

    override suspend fun deleteOfflineDiaryEntry(diaryItem: DiaryItem): Resource<Unit> {
        return handleRequest {
            when(diaryItem) {
                is ProductDiaryEntry -> {
                    userDiaryItemsDao.deleteProductDiaryEntry(diaryItem.id)
                }

                is RecipeDiaryEntry -> {
                    userDiaryItemsDao.deleteRecipeDiaryEntry(diaryItem.id)
                }
            }
        }
    }

    override suspend fun getOfflineProduct(productId: String): Resource<Product?> {
        return handleRequest {
            userDiaryItemsDao.getProduct(productId).firstOrNull()
        }
    }

    override suspend fun getOfflineRecipe(recipeId: String): Resource<Recipe?> {
        return handleRequest {
            userDiaryItemsDao.getRecipe(recipeId).firstOrNull()
        }
    }
}