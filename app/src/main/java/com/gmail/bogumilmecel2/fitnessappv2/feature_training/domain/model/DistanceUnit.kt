package com.gmail.bogumilmecel2.fitnessappv2.feature_training.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
enum class DistanceUnit {
    @SerializedName("meters")
    METERS,

    @SerializedName("kilometers")
    KILOMETERS,

    @SerializedName("feet")
    FEET,

    @SerializedName("miles")
    MILES
}