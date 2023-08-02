package com.gmail.bogumilmecel2.ui.components.base

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun CustomDialog(
    title: String,
    secondaryText: String?,
    primaryButtonParams: ButtonParams,
    secondaryButtonParams: ButtonParams?,
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {
            onDismissRequest()
        },
        title = {
            Text(
                text = title,
                style = FitnessAppTheme.typography.ImportantTextMedium
            )
        },
        text = {
            secondaryText?.let {
                Text(
                    text = secondaryText,
                    style = FitnessAppTheme.typography.ParagraphMedium,
                    color = FitnessAppTheme.colors.ContentSecondary
                )
            }
        },
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, end = 16.dp, start = 16.dp)
            ) {
                secondaryButtonParams?.let {
                    CustomButton(
                        text = it.text,
                        modifier = Modifier.weight(1f),
                        buttonStyle = ButtonStyle.OutlinedPrimary
                    ) {
                        it.onClick()
                    }
                }

                WidthSpacer(width = 16.dp)

                CustomButton(
                    text = primaryButtonParams.text,
                    modifier = Modifier.weight(1f)
                ) {
                    primaryButtonParams.onClick()
                }
            }
        },
        shape = RoundedCornerShape(20.dp),
        backgroundColor = FitnessAppTheme.colors.BackgroundQuaternary
    )
}