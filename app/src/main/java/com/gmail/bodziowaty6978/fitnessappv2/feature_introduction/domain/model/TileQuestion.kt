package com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.model

import com.gmail.bodziowaty6978.fitnessappv2.R

data class GenderQuestion(
    override val questionType: QuestionType = QuestionType.TILE,
    override val questionName: QuestionName = QuestionName.GENDER,
    var selectedGender: Gender = Gender.MALE
) : Question {
    enum class Gender {
        MALE, FEMALE;

        fun getDisplayValue() = when (this) {
            MALE -> R.string.introduction_male
            FEMALE -> R.string.introduction_female
        }
    }
}

data class AgeQuestion(
    override val questionType: QuestionType = QuestionType.INPUT,
    override val questionName: QuestionName = QuestionName.AGE,
    var value: String = ""
) : Question

data class HeightQuestion(
    override val questionType: QuestionType = QuestionType.INPUT,
    override val questionName: QuestionName = QuestionName.AGE,
    var value: String = ""
) : Question

data class CurrentWeightQuestion(
    override val questionType: QuestionType = QuestionType.INPUT,
    override val questionName: QuestionName = QuestionName.AGE,
    var value: String = ""
) : Question

data class WorkQuestion(
    override val questionType: QuestionType = QuestionType.TILE,
    override val questionName: QuestionName = QuestionName.AGE,
    var selectedTypeOfWork: TypeOfWork = TypeOfWork.SEDENTARY,
) : Question {

    enum class TypeOfWork {
        SEDENTARY, MIXED, PHYSICAL;

        fun getDisplayValue() = when (this) {
            SEDENTARY -> R.string.introduction_sedentary
            MIXED -> R.string.introduction_mixed
            PHYSICAL -> R.string.introduction_physical
        }
    }
}

data class TrainingQuestion(
    override val questionType: QuestionType = QuestionType.TILE,
    override val questionName: QuestionName = QuestionName.AGE,
    var selectedTrainingFrequency: TrainingFrequency = TrainingFrequency.NONE,
) : Question {
    enum class TrainingFrequency {
        NONE, LOW, AVERAGE, HIGH, VERY_HIGH;

        fun getDisplayValue() = when (this) {
            NONE -> R.string.introduction_none
            LOW -> R.string.introduction_training_low
            AVERAGE -> R.string.introduction_training_average
            HIGH -> R.string.introduction_training_high
            VERY_HIGH -> R.string.introduction_training_very_high
        }
    }
}

data class ActivityQuestion(
    override val questionType: QuestionType = QuestionType.TILE,
    override val questionName: QuestionName = QuestionName.AGE,
    var selectedActivityLevel: ActivityLevel = ActivityLevel.LOW,
) : Question {
    enum class ActivityLevel {
        LOW, MODERATE, HIGH, VERY_HIGH;

        fun getDisplayValue() = when (this) {
            LOW -> R.string.introduction_activity_low
            MODERATE -> R.string.introduction_activity_moderate
            HIGH -> R.string.introduction_activity_high
            VERY_HIGH -> R.string.introduction_activity_very_high
        }
    }
}

data class DesiredWeightQuestion(
    override val questionType: QuestionType = QuestionType.INPUT,
    override val questionName: QuestionName = QuestionName.DESIRED_WEIGHT,
    var selectedDesiredWeight: DesiredWeight = DesiredWeight.LOOSE
) : Question {
    enum class DesiredWeight {
        LOOSE, GAIN
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
}

enum class QuestionType {
    TILE, INPUT
}
