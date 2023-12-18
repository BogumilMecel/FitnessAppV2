package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.TextGrey

@Composable
fun ProductTopSection(
    mealName: String,
    currentDate: String,
    onBackArrowPressed: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ){

        IconButton(
            onClick = {
                onBackArrowPressed()
            },
            modifier = Modifier
                .align(Alignment.CenterStart)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "arrow back"
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            Text(
                text = mealName,
                style = MaterialTheme.typography.h2
            )

            Text(
                text = currentDate,
                style = MaterialTheme.typography.body2,
                color = TextGrey
            )

        }

    }
}