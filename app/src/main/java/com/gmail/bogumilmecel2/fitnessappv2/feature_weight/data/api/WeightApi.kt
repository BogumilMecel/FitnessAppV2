package com.gmail.bogumilmecel2.fitnessappv2.feature_weight.data.api

import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.NewWeightEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.NewWeightEntryResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface WeightApi {
    @POST("/weightEntries")
    suspend fun addWeightEntry(
        @Body newWeightEntryRequest: NewWeightEntryRequest
    ): NewWeightEntryResponse
}