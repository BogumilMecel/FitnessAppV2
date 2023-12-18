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
    fun insertProducts(products: List<Product>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipes(recipes: List<Recipe>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProductDiaryEntries(productDiaryEntries: List<ProductDiaryEntry>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipeDiaryEntries(recipeDiaryEntries: List<RecipeDiaryEntry>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProductDiaryEntry(productDiaryEntry: ProductDiaryEntry)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipeDiaryEntry(recipeDiaryEntry: RecipeDiaryEntry)

    @Query("SELECT * from recipe WHERE (:userId IS NULL OR user_id = :userId) AND (:searchText IS NULL OR name LIKE '%' || :searchText || '%') ORDER BY utc_timestamp DESC LIMIT :limit OFFSET :offset")
    fun getUserRecipes(
        searchText: String?,
        userId: String?,
        limit: Int,
        offset: Int
    ): List<Recipe>

    @Query("SELECT * from product WHERE (:userId IS NULL OR user_id = :userId) AND (:searchText IS NULL OR name LIKE '%' || :searchText || '%') ORDER BY date_created DESC LIMIT :limit OFFSET :offset")
    fun getProducts(
        searchText: String?,
        userId: String?,
        limit: Int,
        offset: Int
    ): List<Product>

    @Query("SELECT * FROM productdiaryentry WHERE (:searchText IS NULL OR product_name LIKE '%' || :searchText || '%')  GROUP BY  product_id ORDER BY utc_timestamp DESC LIMIT :limit OFFSET :offset")
    fun getProductDiaryEntries(
        searchText: String? = null,
        limit: Int,
        offset: Int,
    ): List<ProductDiaryEntry>

    @Query("SELECT * FROM productdiaryentry ORDER BY utc_timestamp DESC LIMIT :limit")
    fun getProductDiaryEntries(limit: Int): List<ProductDiaryEntry>

    @Query("SELECT * FROM productdiaryentry WHERE date = :date ORDER BY utc_timestamp DESC")
    fun getProductDiaryEntries(date: String): List<ProductDiaryEntry>

    @Query("SELECT * FROM recipediaryentry WHERE (:searchText IS NULL OR recipe_name LIKE '%' || :searchText || '%') GROUP BY recipe_id ORDER BY utc_timestamp DESC LIMIT :limit OFFSET :offset")
    fun getRecipeDiaryEntries(
        searchText: String? = null,
        limit: Int,
        offset: Int,
    ): List<RecipeDiaryEntry>

    @Query("SELECT * FROM recipediaryentry ORDER BY utc_timestamp DESC LIMIT :limit")
    fun getRecipeDiaryEntries(limit: Int): List<RecipeDiaryEntry>

    @Query("SELECT * FROM recipediaryentry WHERE date = :date ORDER BY utc_timestamp DESC")
    fun getRecipeDiaryEntries(date: String): List<RecipeDiaryEntry>

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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipe(recipe: Recipe)

    @Query("SELECT * FROM product WHERE id = :productId LIMIT 1")
    fun getProduct(productId: String): List<Product>

    @Query("SELECT * FROM recipe WHERE id = :recipeId LIMIT 1")
    fun getRecipe(recipeId: String): List<Recipe>
}