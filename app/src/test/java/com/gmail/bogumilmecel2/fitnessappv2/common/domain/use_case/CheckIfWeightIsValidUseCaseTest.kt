package com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case

import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CheckIfWeightIsValidUseCaseTest {

    private val checkIfWeightIsValidUseCase = CheckIfWeightIsValidUseCase()

    @Test
    fun `Check if weight is lower than zero or 0 false is returned`() {
        assertFalse(checkIfWeightIsValidUseCase(-1.0))
        assertFalse(checkIfWeightIsValidUseCase(-0.0))
    }

    @Test
    fun `Check if weight is higher than 400 or 400 false is returned`() {
        assertFalse(checkIfWeightIsValidUseCase(401.0))
        assertFalse(checkIfWeightIsValidUseCase(400.0))
    }

    @Test
    fun `Check if weight is higher than 0 and lower than 400 true is returned`() {
        assertTrue(checkIfWeightIsValidUseCase(399.9))
        assertTrue(checkIfWeightIsValidUseCase(0.1))
    }
}