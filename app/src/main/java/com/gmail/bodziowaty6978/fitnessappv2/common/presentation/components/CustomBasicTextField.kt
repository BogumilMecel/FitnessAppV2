package com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.TextGrey

@Composable
fun CustomBasicTestField(
    modifier: Modifier = Modifier,
    elevation: Int = 3,
    value: String,
    onValueChange:(String) -> Unit,
    singleLine: Boolean = false,
    placeholder: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    textStyle: TextStyle = MaterialTheme.typography.body1
) {
    Card(
        shape = RoundedCornerShape(30),
        elevation = elevation.dp,
        modifier = modifier
    ) {
        BasicTextField(
            value = value,
            onValueChange ={
                onValueChange(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            textStyle = textStyle,
            cursorBrush = SolidColor(Color.White),
            singleLine = singleLine,
            keyboardOptions = keyboardOptions,
        )

        if (value.isBlank() && placeholder != null){
            Text(
                text = placeholder,
                style = MaterialTheme.typography.body1.copy(
                    color = TextGrey
                ),
                modifier = Modifier.padding(15.dp)
            )
        }
    }
}