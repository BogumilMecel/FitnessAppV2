package com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun BottomButton(
    modifier: Modifier,
    text: String,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(),
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(50)),
        colors = buttonColors,
        onClick = {
            onClick()
        }
    ) {
        Text(
            text = text,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .padding(
                    vertical = 6.dp,
                    horizontal = 10.dp
                )
        )
    }
}