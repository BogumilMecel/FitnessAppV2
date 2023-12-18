package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.repository

import com.gmail.bodziowaty6978.fitnessappv2.common.util.CustomResult
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.diary_entry.DiaryEntry

interface DiaryRepository {
    suspend fun getDiaryEntries(timestamp:Long, token:String): Resource<List<DiaryEntry>>
    suspend fun searchForProducts(searchText:String) : Resource<List<Product>>
    suspend fun searchForProductWithBarcode(barcode:String):Resource<Product>
    suspend fun getProductHistory(token: String):Resource<List<Product>>
    suspend fun addDiaryEntry(diaryEntry: DiaryEntry, token: String):Resource<DiaryEntry>
    suspend fun deleteDiaryEntry(diaryEntryId: Int, token:String):CustomResult
    suspend fun editDiaryEntry(diaryEntry: DiaryEntry):CustomResult
    suspend fun saveNewProduct(product: Product):Resource<Product>
}