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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProductDiaryEntries(productDiaryEntries: List<ProductDiaryEntry>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipeDiaryEntries(recipeDiaryEntry: List<RecipeDiaryEntry>)

    @Query("SELECT * from recipe")
    fun getUserRecipes(): List<Recipe>

    @Query("SELECT * from product")
    fun getUserProducts(): List<Product>

    @Query("SELECT * FROM product_diary_entry")
    fun getProductDiaryEntries(): List<ProductDiaryEntry>

    @Query("SELECT * FROM recipe_diary_entry")
    fun getRecipeDiaryEntries(): List<RecipeDiaryEntry>

    @Query("SELECT * FROM recipe_diary_entry ORDER BY recipe_diary_entry.last_edited_utc_timestamp DESC LIMIT 1")
    fun getLatestRecipeDiaryEntry(): List<RecipeDiaryEntry>

    @Query("SELECT * FROM product_diary_entry ORDER BY product_diary_entry.last_edited_utc_timestamp DESC LIMIT 1")
    fun getLatestProductDiaryEntry(): List<ProductDiaryEntry>

    @Query("DELETE FROM product")
    fun deleteUserProducts()

    @Query("DELETE FROM recipe")
    fun deleteUserRecipes()
}