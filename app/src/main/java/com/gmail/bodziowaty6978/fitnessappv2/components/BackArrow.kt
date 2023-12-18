package com.gmail.bodziowaty6978.fitnessappv2.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BackArrow(
    modifier: Modifier = Modifier,
    onBackArrowPressed:() -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = {
            onBackArrowPressed()
        }
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "arrow back",
            modifier = Modifier
                .size(34.dp)
                .padding(4.dp)
        )
    }
}