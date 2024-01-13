package com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState

@OptIn(ExperimentalFoundationApi::class)
fun TextFieldState.appendText(text: String?) = this.edit { append(text) }