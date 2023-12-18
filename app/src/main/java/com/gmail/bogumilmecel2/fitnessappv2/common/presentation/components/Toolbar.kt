package com.gmail.bogumilmecel2.fitnessappv2.common.presentation.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun Toolbar(
    title: String,
    isBackArrowVisible: Boolean,
    onBackArrowClicked: () -> Unit
) {

    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.h1,
                color = Color.White
            )
        },
        contentColor = FitnessAppTheme.colors.Background,
        elevation = 0.dp,
        navigationIcon = if (isBackArrowVisible) {
            {
                IconButton(onClick = { onBackArrowClicked() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = FitnessAppTheme.colors.ContentPrimary
                    )
                }
            }
        } else {
            null
        },
    )

}