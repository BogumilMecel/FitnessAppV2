package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

class CalculateSkipUseCase {
    operator fun invoke(page: Int, sizePerPage: Long) = sizePerPage * (page - 1)
}