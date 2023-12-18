package com.gmail.bogumilmecel2.fitnessappv2.common.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.dao.UserDiaryItemsDao
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.utils.DiaryConverters

@Database(
    entities = [
        Recipe::class,
        Product::class,
        ProductDiaryEntry::class,
        RecipeDiaryEntry::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(DiaryConverters::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "fitness_app_database"
    }

    abstract fun productDiaryHistoryDao(): UserDiaryItemsDao
}