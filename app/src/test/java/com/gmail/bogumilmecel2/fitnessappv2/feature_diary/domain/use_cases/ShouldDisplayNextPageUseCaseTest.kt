package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ShouldDisplayNextPageUseCaseTest {

    private val shouldDisplayNextPageUseCase = ShouldDisplayNextPageUseCase()

    @Test
    fun `Check if should display next page with sample value, false expected`() {
        assertFalse(
            shouldDisplayNextPageUseCase(
                size = 19,
                perPage = 20,
                currentPage = 1
            )
        )
    }

    @Test
    fun `Check if should display next page with sample value, true expected`() {
        assertTrue(
            shouldDisplayNextPageUseCase(
                size = 20,
                perPage = 20,
                currentPage = 1
            )
        )
    }
}