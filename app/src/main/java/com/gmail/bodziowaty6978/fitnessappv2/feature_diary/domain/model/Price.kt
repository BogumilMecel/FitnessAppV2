package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
data class Price(
    val id:Int = -1,
    val value:Double = 0.0,
    val forHowMuch:Int = 0
)