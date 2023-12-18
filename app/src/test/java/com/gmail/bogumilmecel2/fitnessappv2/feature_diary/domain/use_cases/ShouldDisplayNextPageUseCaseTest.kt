package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ShouldDisplayNextPageUseCaseTest {

    private val shouldDisplayNextPageUseCase = ShouldDisplayNextPageUseCase()

    @Test
    fun `Check if should display next page with sample value, false expected`() {
        assertFalse { callTestedMethod(size = 19) }
    }

    @Test
    fun `Check if should display next page with sample value, true expected`() {
        assertTrue { callTestedMethod(size = 20) }
    }

    @Test
    fun `Check if should display next page with second sample value, true expected`() {
        assertTrue { callTestedMethod(size = 21) }
    }

    private fun callTestedMethod(size: Int) = shouldDisplayNextPageUseCase(
        size = size,
        perPage = 20,
        currentPage = 1
    )
}