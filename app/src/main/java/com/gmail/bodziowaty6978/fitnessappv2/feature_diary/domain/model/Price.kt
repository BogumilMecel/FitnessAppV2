package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Price(
    val value:Double = 0.0,
    val currency:String = "zł"
)