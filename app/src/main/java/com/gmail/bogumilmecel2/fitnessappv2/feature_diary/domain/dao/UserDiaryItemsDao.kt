package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
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
    fun insertRecipeDiaryEntries(recipeDiaryEntries: List<RecipeDiaryEntry>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProductDiaryEntry(productDiaryEntry: ProductDiaryEntry)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipeDiaryEntry(recipeDiaryEntry: RecipeDiaryEntry)

    @Query("SELECT * from recipe WHERE user_id = :userId")
    fun getUserRecipes(userId: String): List<Recipe>

    @Query("SELECT * from product WHERE user_id = :userId")
    fun getUserProducts(userId: String): List<Product>

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

    @Query("DELETE FROM productdiaryentry WHERE id = :productDiaryEntryId")
    fun deleteProductDiaryEntry(productDiaryEntryId: String)

    @Query("DELETE FROM recipediaryentry WHERE id = :recipeDiaryEntryId")
    fun deleteRecipeDiaryEntry(recipeDiaryEntryId: String)

    @Query("DELETE FROM productdiaryentry WHERE date = :date AND id NOT IN (:diaryEntriesIds)")
    fun deleteProductDiaryEntries(
        date: String,
        diaryEntriesIds: List<String>
    )

    @Query("DELETE FROM recipediaryentry WHERE date = :date AND id NOT IN (:diaryEntriesIds)")
    fun deleteRecipeDiaryEntries(
        date: String,
        diaryEntriesIds: List<String>
    )

    @Query("SELECT nutrition_values FROM productdiaryentry WHERE date = :date")
    fun getProductDiaryEntriesNutritionValues(date: String): List<NutritionValues>

    @Query("SELECT nutrition_values FROM recipediaryentry WHERE date = :date")
    fun getRecipeDiaryEntriesNutritionValues(date: String): List<NutritionValues>
}