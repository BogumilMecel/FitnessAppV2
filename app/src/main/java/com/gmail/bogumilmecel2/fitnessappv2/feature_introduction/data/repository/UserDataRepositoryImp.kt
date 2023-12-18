package com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.data.repository

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseRepository
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.data.api.UserDataApi
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.IntroductionRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.IntroductionResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.repository.UserDataRepository


class UserDataRepositoryImp(
    private val userDataApi: UserDataApi,
    private val cachedValuesProvider: CachedValuesProvider
) : UserDataRepository, BaseRepository() {

    override suspend fun saveNutritionValues(nutritionValues: NutritionValues): Resource<Unit> {
        return handleRequest {
            userDataApi.saveNutritionValues(nutritionValues = nutritionValues).let {
                if (it) {
                    cachedValuesProvider.saveWantedNutritionValues(nutritionValues)
                }
            }
        }
    }

    override suspend fun saveUserInformation(introductionRequest: IntroductionRequest): Resource<IntroductionResponse> {
        return handleRequest {
            userDataApi.saveUserInformation(introductionRequest = introductionRequest)
        }
    }
}