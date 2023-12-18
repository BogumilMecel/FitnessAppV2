package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.ProductDiaryHistoryItem
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe

@Dao
interface UserDiaryItemsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserProducts(userProducts: List<Product>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserRecipes(userRecipes: List<Recipe>)

    @Query("SELECT * from recipe")
    fun getUserRecipes(): List<Recipe>

    @Query("SELECT * from product")
    fun getUserProducts(): List<Product>

    @Query("SELECT * FROM ProductDiaryHistoryItem ORDER BY ProductDiaryHistoryItem.utc_timestamp DESC LIMIT :limit")
    fun getDiaryHistory(limit: Int): List<ProductDiaryHistoryItem>

    @Query("SELECT * FROM ProductDiaryHistoryItem ORDER BY ProductDiaryHistoryItem.utc_timestamp DESC")
    fun getDiaryHistory(): List<ProductDiaryHistoryItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDiaryHistoryItems(productDiaryHistoryItems: List<ProductDiaryHistoryItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDiaryHistoryItem(productDiaryHistoryItem: ProductDiaryHistoryItem)
}