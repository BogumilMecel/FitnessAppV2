package com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.data.repository

import androidx.datastore.core.DataStore
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.UserInformation
import com.gmail.bodziowaty6978.fitnessappv2.common.util.CustomResult
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.repository.IntroductionRepository


class IntroductionRepositoryImp(
    private val informationDatastore : DataStore<UserInformation>,
    private val nutritionDatastore : DataStore<NutritionValues>
): IntroductionRepository {

    override suspend fun saveIntroductionInformation(
        userInformation: UserInformation,
        nutritionValues: NutritionValues
    ): CustomResult {
        return try {
            informationDatastore.updateData {
                it.copy(
                    activityInADay = userInformation.activityInADay,
                    typeOfWork = userInformation.typeOfWork,
                    workoutInAWeek = userInformation.workoutInAWeek,
                    gender = userInformation.gender,
                    height = userInformation.height,
                    currentWeight = userInformation.currentWeight,
                    wantedWeight = userInformation.wantedWeight,
                    age = userInformation.age,
                )
            }

            nutritionDatastore.updateData {
                it.copy(
                    calories = nutritionValues.calories,
                    carbohydrates = nutritionValues.carbohydrates,
                    protein = nutritionValues.protein,
                    fat = nutritionValues.fat,
                )
            }

            CustomResult.Success
        }catch (e:Exception){
            CustomResult.Error(e.message.toString())
        }

    }
}