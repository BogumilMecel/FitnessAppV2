package com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)
