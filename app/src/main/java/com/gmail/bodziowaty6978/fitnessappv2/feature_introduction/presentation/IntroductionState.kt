package com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.presentation

import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.model.ActivityLevel
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.model.DesiredWeight
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.model.Gender
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.model.QuestionName
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.model.Tile
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.model.TrainingFrequency
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.model.TypeOfWork

data class IntroductionState(
    val selectedGender: Gender = Gender.MALE,
    val age: String = "",
    val height: String = "",
    val currentWeight: String = "",
    val typeOfWork: TypeOfWork = TypeOfWork.SEDENTARY,
    val activityLevel: ActivityLevel = ActivityLevel.LOW,
    val trainingFrequency: TrainingFrequency = TrainingFrequency.LOW,
    val desiredWeight: DesiredWeight = DesiredWeight.LOOSE
) {
    fun getStringAnswer(questionName: QuestionName): String? = when(questionName) {
        QuestionName.AGE -> this.age
        QuestionName.CURRENT_WEIGHT -> this.currentWeight
        QuestionName.HEIGHT -> this.height
        else -> null
    }
    fun getTileAnswer(questionName: QuestionName): Tile? = when (questionName) {
        QuestionName.GENDER -> this.selectedGender
        QuestionName.TYPE_OF_WORK -> this.typeOfWork
        QuestionName.TRAINING_FREQUENCY -> this.activityLevel
        QuestionName.ACTIVITY_LEVEL -> this.trainingFrequency
        QuestionName.DESIRED_WEIGHT -> this.desiredWeight
        else -> null
    }
}
