package com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case

class CheckIfWeightIsValidUseCase {
    operator fun invoke(value: Double) = value > 0 && value < 400
}