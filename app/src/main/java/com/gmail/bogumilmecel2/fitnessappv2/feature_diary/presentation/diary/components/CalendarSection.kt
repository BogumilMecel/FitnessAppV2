package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.diary.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.gmail.bogumilmecel2.fitnessappv2.R

@Composable
fun CalendarSection(
    modifier: Modifier = Modifier,
    date: String,
    onArrowForwardClicked: () -> Unit,
    onArrowBackwardsClicked: () -> Unit,
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(
            onClick = onArrowBackwardsClicked,
            modifier = Modifier.testTag(stringResource(id = R.string.CALENDAR_BACK))
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIos,
                contentDescription = "Back arrow",
                tint = MaterialTheme.colors.onBackground
            )
        }


        Text(
            text = date,
            style = MaterialTheme.typography.h2.copy(
                color = MaterialTheme.colors.onBackground
            ),
            modifier = Modifier.testTag(stringResource(id = R.string.CALENDAR_DATE))
        )

        IconButton(
            onClick = onArrowForwardClicked,
            modifier = Modifier.testTag(stringResource(id = R.string.CALENDAR_FORWARD))
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = "Back arrow",
                tint = MaterialTheme.colors.onBackground
            )
        }


    }
}