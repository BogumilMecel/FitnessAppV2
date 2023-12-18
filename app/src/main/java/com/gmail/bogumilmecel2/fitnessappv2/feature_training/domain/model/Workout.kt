package com.gmail.bogumilmecel2.fitnessappv2.feature_training.domain.model

interface Workout {
    val exerciseName: String
    val exerciseId: String
    val exerciseType: ExerciseType
    val utcTimestamp: Long
}