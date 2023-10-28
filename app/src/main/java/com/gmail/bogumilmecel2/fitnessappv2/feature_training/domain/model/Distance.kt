package com.gmail.bogumilmecel2.fitnessappv2.feature_training.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Distance(
    @SerializedName("distance")
    val distance: Int,

    @SerializedName("distance_unit")
    val distanceUnit: DistanceUnit
)