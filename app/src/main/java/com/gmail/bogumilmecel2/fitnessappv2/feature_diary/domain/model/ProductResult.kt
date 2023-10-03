package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ProductResult(
    @SerializedName("product")
    val product: Product,
    @SerializedName("weight")
    val weight: Int
): java.io.Serializable