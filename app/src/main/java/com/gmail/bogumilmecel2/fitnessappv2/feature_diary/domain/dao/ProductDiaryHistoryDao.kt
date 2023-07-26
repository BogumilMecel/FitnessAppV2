package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.ProductDiaryHistoryItem

@Dao
interface ProductDiaryHistoryDao {
    @Insert
    fun insertProductDiaryHistory(productDiaryHistoryItems: List<ProductDiaryHistoryItem>)
}