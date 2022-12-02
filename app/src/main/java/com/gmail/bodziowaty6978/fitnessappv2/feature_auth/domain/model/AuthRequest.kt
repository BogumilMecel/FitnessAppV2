package com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.model

data class AuthRequest(
    val username: String? = null,
    val email: String,
    val password: String
)
