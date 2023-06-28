package com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.toValidDouble
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.toValidInt
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.User
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.ActivityLevel
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.DesiredWeight
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.Gender
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.IntroductionRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.TrainingFrequency
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.TypeOfWork
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.repository.UserDataRepository

class SaveIntroductionInformationUseCase(
    private val userDataRepository: UserDataRepository,
    private val resourceProvider: ResourceProvider,
) {

    suspend operator fun invoke(
        selectedGender: Gender,
        age: String,
        height: String,
        weight: String,
        typeOfWork: TypeOfWork,
        activityLevel: ActivityLevel,
        trainingFrequency: TrainingFrequency,
        desiredWeight: DesiredWeight,
    ): Resource<User> {
        val ageValue = age.toValidInt() ?: return getFieldErrorResource()
        val heightValue = height.toValidInt() ?: return getFieldErrorResource()
        val weightValue = weight.toValidDouble() ?: return getFieldErrorResource()

        return if (ageValue < 0 || ageValue > 100) {
            Resource.Error(uiText = resourceProvider.getString(R.string.please_make_sure_you_have_entered_your_age))
        } else if (heightValue < 0 || heightValue > 250) {
            Resource.Error(uiText = resourceProvider.getString(R.string.introduction_height_error))
        } else if (weightValue < 0 || weightValue > 500) {
            Resource.Error(uiText = resourceProvider.getString(R.string.please_make_sure_you_have_entered_your_age))
        } else {
            userDataRepository.saveUserInformation(
                introductionRequest = IntroductionRequest(
                    gender = selectedGender,
                    weight = weightValue,
                    age = ageValue,
                    height = heightValue,
                    activityLevel = activityLevel,
                    trainingFrequency = trainingFrequency,
                    typeOfWork = typeOfWork,
                    desiredWeight = desiredWeight,
                )
            )
        }
    }

    private fun getFieldErrorResource() =
        Resource.Error<User>(uiText = resourceProvider.getString(R.string.please_make_sure_all_fields_are_filled_in_correctly))
}