package com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.clearText

@OptIn(ExperimentalFoundationApi::class)
fun TextFieldState.appendText(text: String?) = this.edit { append(text) }

@OptIn(ExperimentalFoundationApi::class)
fun TextFieldState.clearAndAppendText(text: String?) = this.apply {
    clearText()
    appendText(text)
}

@OptIn(ExperimentalFoundationApi::class)
fun TextFieldState.getText() = this.text.toString()