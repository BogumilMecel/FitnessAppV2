package com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.presentation

import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.model.ActivityQuestion
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.model.AgeQuestion
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.model.CurrentWeightQuestion
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.model.DesiredWeightQuestion
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.model.GenderQuestion
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.model.HeightQuestion
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.model.Question
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.model.TrainingQuestion
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.model.WorkQuestion

data class IntroductionState(
    val questions: List<Question> = listOf(
        GenderQuestion(),
        AgeQuestion(),
        HeightQuestion(),
        CurrentWeightQuestion(),
        WorkQuestion(),
        TrainingQuestion(),
        ActivityQuestion(),
        DesiredWeightQuestion()
    )
)
