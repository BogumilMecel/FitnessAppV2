package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

class ShouldDisplayNextPageUseCase {
    operator fun invoke(size: Int, perPage: Long, currentPage: Int): Boolean {
        return size >= currentPage * perPage
    }
}