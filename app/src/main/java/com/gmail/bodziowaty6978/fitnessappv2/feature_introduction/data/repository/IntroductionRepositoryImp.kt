package com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.data.repository

import com.gmail.bodziowaty6978.fitnessappv2.common.data.utils.CustomSharedPreferencesUtils
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.UserInformation
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.data.api.IntroductionApi
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.repository.IntroductionRepository


class IntroductionRepositoryImp(
    private val introductionApi: IntroductionApi,
    private val customSharedPreferencesUtils: CustomSharedPreferencesUtils,
    private val resourceProvider: ResourceProvider
): IntroductionRepository {

    override suspend fun saveNutritionValues(
        token: String,
        nutritionValues: NutritionValues
    ): Resource<NutritionValues> {
        return try {
            val savedNutritionValues = introductionApi.saveNutritionValues(
                nutritionValues = nutritionValues, token = token
            )
            customSharedPreferencesUtils.saveWantedNutritionValues(savedNutritionValues)
            Resource.Success(savedNutritionValues)
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Error(resourceProvider.getUnknownErrorString())
        }
    }

    override suspend fun saveUserInformation(
        token: String,
        userInformation: UserInformation
    ): Resource<UserInformation> {
        return try {
            val savedUserInformation = introductionApi.saveUserInformation(
                userInformation = userInformation, token = token
            )
            customSharedPreferencesUtils.saveUserInformation(userInformation)
            Resource.Success(savedUserInformation)
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Error(resourceProvider.getUnknownErrorString())
        }
    }
}