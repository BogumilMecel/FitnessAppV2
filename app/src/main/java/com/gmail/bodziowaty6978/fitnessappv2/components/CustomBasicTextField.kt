package com.gmail.bodziowaty6978.fitnessappv2.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.DarkGreyElevation9
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.TextGreyPlaceholder

@Composable
fun CustomBasicTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = false,
    placeholder: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    textStyle: TextStyle = MaterialTheme.typography.body1,
    textPadding: Dp = 12.dp
) {
    var isFocused by remember { mutableStateOf(false) }

    Card(
        elevation = 2.dp,
        shape = RoundedCornerShape(15.dp),
        modifier = modifier,
        backgroundColor = DarkGreyElevation9
    ) {
        BasicTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(textPadding)
                .onFocusChanged {
                    isFocused = it.isFocused
                },
            textStyle = textStyle,
            cursorBrush = SolidColor(Color.White),
            singleLine = singleLine,
            keyboardOptions = keyboardOptions,
        )

        if (value.isBlank() && placeholder != null && !isFocused) {
            Text(
                text = placeholder,
                style = textStyle,
                color = TextGreyPlaceholder,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(textPadding)
            )
        }
    }
}
