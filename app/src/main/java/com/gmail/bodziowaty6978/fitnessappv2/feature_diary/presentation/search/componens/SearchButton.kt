package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.search.componens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SearchButton(
    text: String,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    color: Color,
    onClick: () -> Unit
) {

    Button(
        modifier = modifier
            .padding(horizontal = 5.dp),
        onClick = {
            onClick()
        },
        shape = RoundedCornerShape(25),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = color
        )
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            icon()

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = text,
                style = MaterialTheme.typography.button.copy(
                    color = MaterialTheme.colors.onPrimary
                ),
                maxLines = 1
            )
        }
    }
}