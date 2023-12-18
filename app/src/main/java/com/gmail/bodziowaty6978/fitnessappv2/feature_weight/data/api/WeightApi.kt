package com.gmail.bodziowaty6978.fitnessappv2.feature_weight.data.api

import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.model.WeightEntry
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface WeightApi {

    @GET("/weightEntries/latest")
    suspend fun getLatestWeightEntries(): List<WeightEntry>

    @POST("/weightEntries")
    suspend fun addWeightEntry(@Body weightEntry: WeightEntry): WeightEntry
}