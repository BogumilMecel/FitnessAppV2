package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe

@Dao
interface UserDiaryItemsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserProducts(userProducts: List<Product>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserRecipes(userRecipes: List<Recipe>)
}