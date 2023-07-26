package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.ProductDiaryHistoryItem

@Dao
interface ProductDiaryHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProductDiaryHistory(productDiaryHistoryItems: List<ProductDiaryHistoryItem>)
}