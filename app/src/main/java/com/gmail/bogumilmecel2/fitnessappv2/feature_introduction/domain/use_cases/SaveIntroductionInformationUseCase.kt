package com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.util.RealResourceProvider
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
    private val realResourceProvider: RealResourceProvider,
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
        val ageValue = age.toValidInt()
        val heightValue = height.toValidInt()
        val weightValue = weight.toValidDouble()

        return if (ageValue == null || (ageValue < 0 || ageValue > 100)) {
            Resource.Error(uiText = realResourceProvider.getString(R.string.please_make_sure_you_have_entered_your_age))
        } else if (heightValue == null || (heightValue < 0 || heightValue > 250)) {
            Resource.Error(uiText = realResourceProvider.getString(R.string.introduction_height_error))
        } else if (weightValue == null || (weightValue < 0 || weightValue > 500)) {
            Resource.Error(uiText = realResourceProvider.getString(R.string.please_make_sure_you_have_entered_your_age))
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
}