package com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.repository

import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.User
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.model.WeightDialogsRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.WeightEntry

interface WeightRepository {
    suspend fun addWeightEntry(weightEntry: WeightEntry): Resource<User>
    suspend fun handleWeightDialogsQuestion(weightDialogsRequest: WeightDialogsRequest): Resource<Unit>
}