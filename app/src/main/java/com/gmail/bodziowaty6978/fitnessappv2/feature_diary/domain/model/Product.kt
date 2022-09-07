package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
@Entity
data class Product(
    @PrimaryKey val id:Int = -1,
    val name: String = "White rice",
    val containerWeight: Int = 0,
    val position: Int = 0,
    val unit: String = "g",
    val nutritionValues: NutritionValues = NutritionValues(),
    val barcode: String? = "",
    val price: Price = Price()
):Parcelable




