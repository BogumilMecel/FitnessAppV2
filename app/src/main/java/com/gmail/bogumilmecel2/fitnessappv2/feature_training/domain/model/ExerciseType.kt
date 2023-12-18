package com.gmail.bogumilmecel2.fitnessappv2.feature_training.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ExerciseType {
    @SerialName("weight_and_reps")
    WeightAndReps,

    @SerialName("distance_and_time")
    DistanceAndTime,

    @SerialName("weight_and_distance")
    WeightAndDistance,

    @SerialName("weight_and_time")
    WeightAndTime,

    @SerialName("reps_and_distance")
    RepsAndDistance,

    @SerialName("reps_and_time")
    RepsAndTime,

    @SerialName("weight")
    Weight,

    @SerialName("reps")
    Reps,

    @SerialName("distance")
    Distance,

    @SerialName("time")
    Time
}