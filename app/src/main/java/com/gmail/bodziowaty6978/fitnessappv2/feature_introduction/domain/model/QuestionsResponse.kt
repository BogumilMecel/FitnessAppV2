package com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.model

data class QuestionsResponse(
    val tileQuestions: List<TileQuestion>,
    val inputQuestions: List<InputQuestion>
)