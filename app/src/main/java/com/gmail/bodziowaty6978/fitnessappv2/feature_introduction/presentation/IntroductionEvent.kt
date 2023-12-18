package com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.presentation

import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.model.QuestionName
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.model.Tile

sealed interface IntroductionEvent {
    object ClickedArrowForward : IntroductionEvent
    object ClickedArrowBackwards : IntroductionEvent
//    data class EnteredGender(val gender: GenderQuestion.Gender) : IntroductionEvent
//    data class EnteredAge(val age: String) : IntroductionEvent
//    data class EnteredHeight(val height: String) : IntroductionEvent
//    data class EnteredCurrentWeight(val weight: String) : IntroductionEvent
//    data class EnteredTypeOfWork(val typeOfWork: WorkQuestion.TypeOfWork) : IntroductionEvent
//    data class EnteredTrainingFrequency(val trainingFrequency: TrainingQuestion.TrainingFrequency) :
//        IntroductionEvent
//
//    data class EnteredActivityLevel(val activityLevel: ActivityQuestion.ActivityLevel) :
//        IntroductionEvent
//    data class EnteredDesiredWeight(val desiredWeight: DesiredWeightQuestion.DesiredWeight): IntroductionEvent
    data class EnteredAnswer(val questionName: QuestionName, val newValue: String): IntroductionEvent
    data class ClickedTile(val questionName: QuestionName, val tile: Tile): IntroductionEvent
    object FinishIntroduction : IntroductionEvent
}