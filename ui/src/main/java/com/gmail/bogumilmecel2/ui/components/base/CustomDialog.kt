package com.gmail.bogumilmecel2.ui.components.base

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun CustomDialog(
    title: String,
    confirmButtonText: String,
    onDismissRequest: () -> Unit,
    onConfirmButtonClicked: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            onDismissRequest()
        },
        title = {
            CustomText(
                text = title,
                fitnessAppTextStyle = FitnessAppTextStyle.HeaderSmall
            )
        },
        confirmButton = {
            CustomButton(
                text = confirmButtonText,
                modifier = Modifier.padding(16.dp)
            ) {
                onConfirmButtonClicked()
            }
        },
        shape = RoundedCornerShape(20.dp),
        backgroundColor = FitnessAppTheme.colors.BackgroundLight
    )
}