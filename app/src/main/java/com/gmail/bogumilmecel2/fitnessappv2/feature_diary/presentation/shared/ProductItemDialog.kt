package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.shared

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.ui.components.base.ButtonParams
import com.gmail.bogumilmecel2.ui.components.base.CustomDialog

@Composable
fun ProductItemDialog(
    title: String,
    onDeleteButtonClicked: () -> Unit,
    onEditButtonClicked: () -> Unit,
    onDismissRequest: () -> Unit
) {
    CustomDialog(
        title = title,
        secondaryText = stringResource(id = R.string.what_would_you_like_to_do),
        primaryButtonParams = ButtonParams(
            text = stringResource(id = R.string.delete),
            onClick = onDeleteButtonClicked
        ),
        secondaryButtonParams = ButtonParams(
            text = stringResource(id = R.string.edit),
            onClick = onEditButtonClicked
        ),
        onDismissRequest = {
            onDismissRequest()
        },
    )
}