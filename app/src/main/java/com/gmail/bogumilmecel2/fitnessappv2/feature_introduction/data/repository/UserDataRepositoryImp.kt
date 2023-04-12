package com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.data.repository

import com.gmail.bogumilmecel2.fitnessappv2.common.data.utils.CustomSharedPreferencesUtils
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseRepository
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.common.util.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.User
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.data.api.UserDataApi
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.IntroductionRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.repository.UserDataRepository


class UserDataRepositoryImp(
    private val userDataApi: UserDataApi,
    private val resourceProvider: ResourceProvider,
    private val customSharedPreferencesUtils: CustomSharedPreferencesUtils
) : UserDataRepository, BaseRepository(resourceProvider) {

    override suspend fun saveNutritionValues(nutritionValues: NutritionValues): Resource<Unit> {
        return handleRequest {
            userDataApi.saveNutritionValues(nutritionValues = nutritionValues).let {
                if (it) {
                    customSharedPreferencesUtils.saveWantedNutritionValues(nutritionValues)
                }
            }
        }
    }

    override suspend fun saveUserInformation(introductionRequest: IntroductionRequest): Resource<User> {
        return handleRequest {
            val user = userDataApi.saveUserInformation(
                introductionRequest = introductionRequest
            )
            customSharedPreferencesUtils.saveUser(user)
            user
        }
    }
}