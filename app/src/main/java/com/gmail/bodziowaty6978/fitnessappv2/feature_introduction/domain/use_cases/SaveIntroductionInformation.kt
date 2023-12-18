package com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.use_cases

import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.UserInformation
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.use_case.SaveNutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.repository.UserDataRepository
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.presentation.util.IntroductionExpectedQuestionAnswer

class SaveIntroductionInformation(
    private val userDataRepository: UserDataRepository,
    private val resourceProvider: ResourceProvider,
    private val calculateNutritionValues: CalculateNutritionValues,
    private val saveNutritionValues: SaveNutritionValues
) {

    suspend operator fun invoke(
        answers: Map<IntroductionExpectedQuestionAnswer, String>
    ): Resource<Unit> {
        var resource: Resource<Unit> = Resource.Success(Unit)
        val gender = answers[IntroductionExpectedQuestionAnswer.Gender]!!.toInt()
        val currentWeight =
            answers[IntroductionExpectedQuestionAnswer.CurrentWeight]!!.replace(
                ",",
                "."
            )
                .toDoubleOrNull()
        val height =
            answers[IntroductionExpectedQuestionAnswer.Height]!!.replace(
                ",",
                "."
            ).toDoubleOrNull()
        val workType = answers[IntroductionExpectedQuestionAnswer.TypeOfWork]!!.toInt()
        val workoutsInWeek =
            answers[IntroductionExpectedQuestionAnswer.HowOftenDoYouTrain]!!.toInt()
        val activityInDay = answers[IntroductionExpectedQuestionAnswer.ActivityDuringADay]!!.toInt()
        val wantedWeight =
            answers[IntroductionExpectedQuestionAnswer.DesiredWeight]!!.replace(
                ",",
                "."
            )
                .toDoubleOrNull()
        val age = answers[IntroductionExpectedQuestionAnswer.Age]!!.replace(
            ",",
            "."
        ).toIntOrNull()

        if (currentWeight == null || currentWeight <= 0) {
            resource = Resource.Error(
                uiText = resourceProvider.getString(R.string.please_make_sure_you_have_entered_your_weight)
            )
        }
        if (wantedWeight == null || wantedWeight <= 0) {
            resource = Resource.Error(
                uiText = resourceProvider.getString(R.string.please_make_sure_you_have_entered_your_desired_weight)
            )
        }
        if (height == null || height <= 0) {
            resource = Resource.Error(
                uiText = resourceProvider.getString(R.string.please_make_sure_you_have_entered_your_age)
            )
        }
        if (age == null || age <= 0) {
            resource = Resource.Error(
                uiText = resourceProvider.getString(R.string.please_make_sure_you_have_entered_your_age)
            )
        }
        if (resource !is Resource.Error) {
            val nutritionValues = calculateNutritionValues(
                gender,
                currentWeight!!,
                height!!,
                workType,
                workoutsInWeek,
                activityInDay,
                wantedWeight!!,
                age!!
            )
            val nutritionResource = saveNutritionValues(nutritionValues = nutritionValues)
            userDataRepository.saveUserInformation(
                userInformation = UserInformation(
                    activityInADay = activityInDay,
                    typeOfWork = workType,
                    workoutInAWeek = workoutsInWeek,
                    gender = gender,
                    height = height,
                    age = age,
                    wantedWeight = wantedWeight,
                    currentWeight = currentWeight,
                )
            )
            resource =
                if (nutritionResource is Resource.Success) Resource.Success(Unit) else Resource.Error(
                    resourceProvider.getUnknownErrorString()
                )
        }
        return resource
    }
}