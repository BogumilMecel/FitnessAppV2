package com.gmail.bodziowaty6978.fitnessappv2.common.domain.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
@Entity
data class NutritionValues(
    val id:Int = -1,
    val calories:Int = 2850,
    val carbohydrates:Double = 1000.0,
    val protein:Double = 500.0,
    val fat:Double = 100.0
):Parcelable