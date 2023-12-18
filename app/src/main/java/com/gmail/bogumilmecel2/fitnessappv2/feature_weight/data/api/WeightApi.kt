package com.gmail.bogumilmecel2.fitnessappv2.feature_weight.data.api

import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.model.WeightDialogsRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.model.WeightDialogsResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.NewWeightEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.NewWeightEntryResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface WeightApi {
    @POST("/weightEntries")
    suspend fun addWeightEntry(
        @Body newWeightEntryRequest: NewWeightEntryRequest
    ): NewWeightEntryResponse

    @GET("/weightEntries/dialogs")
    suspend fun checkIfShouldAskForWeightDialogs()

    @POST("/weightEntries/dialogs")
    suspend fun handleWeightDialogAnswer(weightDialogsRequest: WeightDialogsRequest): WeightDialogsResponse
}