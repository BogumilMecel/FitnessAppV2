package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.data.repository.remote

import android.util.Log
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.util.CustomResult
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.common.util.extensions.formatToString
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.data.api.ProductApi
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Price
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.diary_entry.DiaryEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bodziowaty6978.fitnessappv2.util.TAG
import java.util.*

class DiaryRepositoryImp(
    private val productApi:ProductApi,
    private val resourceProvider: ResourceProvider
): DiaryRepository {

    override suspend fun getDiaryEntries(timestamp: Long, token:String): Resource<List<DiaryEntry>> {
        return try {
            Log.e(TAG, Date(timestamp).formatToString())
            val entries = productApi.getDiaryEntries(
                date = Date(timestamp).formatToString(),
                token = token
            )
            Resource.Success(entries)
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Error(uiText = e.message.toString(), data = emptyList())
        }
    }
    override suspend fun getProductHistory(token: String): Resource<List<Product>> {
        return try {
            val result = productApi.getProductHistory(
                token = token
            )
            Resource.Success(data = result)
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Error(data = emptyList(), uiText = e.message.toString())
        }
    }

    override suspend fun searchForProducts(searchText: String): Resource<List<Product>> {
        return try {
            val items = productApi.searchForProducts(
                searchText = searchText
            )
            return Resource.Success(data = items)
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Error(uiText = e.message.toString(), data = emptyList())
        }
    }

    override suspend fun searchForProductWithBarcode(barcode: String): Resource<Product> {
        return try {
            val product = productApi.searchForProductWithBarcode(
                barcode = barcode
            )
            if (product==null) Resource.Error(uiText = resourceProvider.getString(R.string.there_is_no_product_with_provided_barcode_do_you_want_to_add_it)) else Resource.Success(data = product)
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Error(uiText = resourceProvider.getString(R.string.unknown_error))
        }
    }

    override suspend fun addDiaryEntry(diaryEntry: DiaryEntry, token: String): Resource<DiaryEntry> {
        return try {
            val newDiaryEntry = productApi.insertDiaryEntry(
                diaryEntry = diaryEntry,
                token = token
            )
            Resource.Success(data = newDiaryEntry)
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Error(resourceProvider.getString(R.string.unknown_error))
        }
    }

    override suspend fun saveNewProduct(product: Product): Resource<Product> {
        return try {
            val newProduct = productApi.insertProduct(
                product = product
            )
            Resource.Success(data = newProduct)
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Error(uiText = resourceProvider.getString(R.string.unknown_error))
        }
    }

    override suspend fun deleteDiaryEntry(
        diaryEntryId: Int,
        token:String
    ): CustomResult {
        return try {
            val wasDeleted = productApi.deleteDiaryEntry(
                entryId = diaryEntryId,
                token = token
            )
            if (wasDeleted) CustomResult.Success else CustomResult.Error(message = resourceProvider.getString(R.string.unknown_error))
        }catch (e:Exception){
            e.printStackTrace()
            CustomResult.Error(message = resourceProvider.getString(R.string.unknown_error))
        }
    }

    override suspend fun editDiaryEntry(diaryEntry: DiaryEntry): CustomResult {
        TODO("Not yet implemented")
    }

    override suspend fun getCaloriesSum(date: String, token: String): Resource<Int> {
        return try {
            val resource = productApi.getCaloriesSum(
                date = date,
                token = token
            ).sum()
            Resource.Success(
                data = resource
            )
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Error(resourceProvider.getString(R.string.unknown_error))
        }
    }

    override suspend fun addNewPrice(price: Price, productId: Int): Resource<Price> {
        return try {
            Resource.Success(
                data = productApi.addNewPriceForProduct(
                    price = price,
                    productId = productId
                )
            )
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Error(resourceProvider.getString(R.string.unknown_error))
        }
    }
}