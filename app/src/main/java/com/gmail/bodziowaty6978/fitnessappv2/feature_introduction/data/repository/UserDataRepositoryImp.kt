package com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.data.repository

import com.gmail.bodziowaty6978.fitnessappv2.FitnessApp
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.UserInformation
import com.gmail.bodziowaty6978.fitnessappv2.common.util.BaseRepository
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.data.api.UserDataApi
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.repository.UserDataRepository


class UserDataRepositoryImp(
    private val userDataApi: UserDataApi,
    private val resourceProvider: ResourceProvider
) : UserDataRepository, BaseRepository(resourceProvider) {

    override suspend fun saveNutritionValues(nutritionValues: NutritionValues): Resource<Boolean> {
        return try {
            val wereAcknowledged =
                userDataApi.saveNutritionValues(nutritionValues = nutritionValues)
            if (wereAcknowledged) {
                FitnessApp.saveWantedNutritionValues(nutritionValues)
                Resource.Success(true)
            } else Resource.Error(resourceProvider.getUnknownErrorString())
        } catch (e: Exception) {
            handleExceptionWithResource(exception = e)
        }
    }

    override suspend fun saveUserInformation(
        userInformation: UserInformation
    ): Resource<Boolean> {
        return try {
            val wereAcknowledged =
                userDataApi.saveUserInformation(userInformation = userInformation)
            if (wereAcknowledged) {
                FitnessApp.saveUserInformation(userInformation)
                Resource.Success(true)
            } else Resource.Error(resourceProvider.getUnknownErrorString())
        } catch (e: Exception) {
            handleExceptionWithResource(exception = e)
        }
    }
}