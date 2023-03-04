package com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.model

import com.gmail.bodziowaty6978.fitnessappv2.R

enum class Gender: Tile {
    MALE, FEMALE;

    override fun getDisplayValue() = when (this) {
        MALE -> R.string.introduction_male
        FEMALE -> R.string.introduction_female
    }
}

enum class TypeOfWork: Tile {
    SEDENTARY, MIXED, PHYSICAL;

    override fun getDisplayValue() = when (this) {
        SEDENTARY -> R.string.introduction_sedentary
        MIXED -> R.string.introduction_mixed
        PHYSICAL -> R.string.introduction_physical
    }
}

enum class TrainingFrequency: Tile {
    NONE, LOW, AVERAGE, HIGH, VERY_HIGH;

    override fun getDisplayValue() = when (this) {
        NONE -> R.string.introduction_none
        LOW -> R.string.introduction_training_low
        AVERAGE -> R.string.introduction_training_average
        HIGH -> R.string.introduction_training_high
        VERY_HIGH -> R.string.introduction_training_very_high
    }
}

enum class ActivityLevel: Tile {
    LOW, MODERATE, HIGH, VERY_HIGH;

    override fun getDisplayValue() = when (this) {
        LOW -> R.string.introduction_activity_low
        MODERATE -> R.string.introduction_activity_moderate
        HIGH -> R.string.introduction_activity_high
        VERY_HIGH -> R.string.introduction_activity_very_high
    }
}

enum class DesiredWeight : Tile {
    LOOSE, KEEP, GAIN;

    override fun getDisplayValue() = when(this) {
        LOOSE -> R.string.introduction_loose
        GAIN -> R.string.introduction_gain
        KEEP -> R.string.introduction_keep
    }
}

enum class QuestionName {
    GENDER, AGE, HEIGHT, CURRENT_WEIGHT, TYPE_OF_WORK, TRAINING_FREQUENCY, ACTIVITY_LEVEL, DESIRED_WEIGHT;

    fun getQuestionTitle() = when (this) {
        GENDER -> R.string.what_is_your_gender
        AGE -> R.string.what_is_your_age
        HEIGHT -> R.string.what_is_your_height
        CURRENT_WEIGHT -> R.string.what_is_your_current_weight
        TYPE_OF_WORK -> R.string.what_type_of_work_do_you_have
        TRAINING_FREQUENCY -> R.string.how_often_do_you_train_in_a_week
        ACTIVITY_LEVEL -> R.string.how_would_you_describe_your_activity_level_during_a_day
        DESIRED_WEIGHT -> R.string.what_is_your_desired_weight
    }

    fun getQuestionType() = when (this) {
        GENDER, TYPE_OF_WORK, TRAINING_FREQUENCY, ACTIVITY_LEVEL, DESIRED_WEIGHT -> QuestionType.TILE
        AGE, HEIGHT, CURRENT_WEIGHT -> QuestionType.INPUT
    }

    fun getQuestionUnit() = when(this) {
        HEIGHT -> R.string.cm
        CURRENT_WEIGHT -> R.string.kg
        else -> null
    }

    fun getQuestionTiles(): List<Tile> = when (this) {
            GENDER -> Gender.values()
            TYPE_OF_WORK -> TypeOfWork.values()
            ACTIVITY_LEVEL -> ActivityLevel.values()
            TRAINING_FREQUENCY -> TrainingFrequency.values()
            DESIRED_WEIGHT -> DesiredWeight.values()
            else -> emptyArray()
    }.toList()
}

enum class QuestionType {
    TILE, INPUT
}

interface Tile {
    fun getDisplayValue(): Int
}
