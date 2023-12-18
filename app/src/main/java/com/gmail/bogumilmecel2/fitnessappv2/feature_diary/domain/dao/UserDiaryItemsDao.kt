package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntry

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

    @Query("SELECT * FROM productdiaryentry WHERE date = :date")
    fun getProductDiaryEntries(date: String): List<ProductDiaryEntry>

    @Query("SELECT * FROM recipediaryentry WHERE date = :date")
    fun getRecipeDiaryEntries(date: String): List<RecipeDiaryEntry>

    @Query("SELECT COUNT(*) FROM recipediaryentry")
    fun getRecipeDiaryEntryCount(): Int

    @Query("SELECT COUNT(*) FROM productdiaryentry")
    fun getProductDiaryEntryCount(): Int

    @Query("DELETE FROM product")
    fun deleteUserProducts()

    @Query("DELETE FROM recipe")
    fun deleteUserRecipes()
}