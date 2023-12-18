package com.gmail.bogumilmecel2.fitnessappv2.common.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValuesConverter
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.dao.ProductDiaryHistoryDao
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.ProductDiaryHistoryItem

@Database(entities = [ProductDiaryHistoryItem::class], version = 1)
@TypeConverters(NutritionValuesConverter::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "fitness_app_database"
    }

    abstract fun productDiaryHistoryDao(): ProductDiaryHistoryDao
}