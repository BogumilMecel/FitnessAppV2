package com.gmail.bogumilmecel2.ui.components.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun CustomDialog(
    title: String,
    secondaryText: String?,
    endButtonParams: ButtonParams,
    secondaryButtonParams: ButtonParams?,
    onDismissRequest: () -> Unit,
) {
    CustomDialog(
        title = title,
        content = {
            secondaryText?.let {
                Text(
                    text = secondaryText,
                    style = FitnessAppTheme.typography.ParagraphMedium,
                    color = FitnessAppTheme.colors.ContentSecondary,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }
        },
        endButtonParams = endButtonParams,
        secondaryButtonParams = secondaryButtonParams,
        onDismissRequest = onDismissRequest
    )
}

@Composable
fun CustomDialog(
    title: String,
    content: @Composable () -> Unit,
    endButtonParams: ButtonParams,
    secondaryButtonParams: ButtonParams?,
    onDismissRequest: () -> Unit,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = FitnessAppTheme.colors.BackgroundTertiary,
        ) {
            Column {
                Text(
                    text = title,
                    style = FitnessAppTheme.typography.HeaderMedium,
                    color = FitnessAppTheme.colors.ContentPrimary,
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .padding(vertical = 16.dp)
                )

                content()

                ButtonsRow(
                    endButtonParams = endButtonParams,
                    secondaryButtonParams = secondaryButtonParams
                )
            }
        }
    }
}

@Composable
private fun ButtonsRow(
    endButtonParams: ButtonParams,
    secondaryButtonParams: ButtonParams?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.End
    ) {
        secondaryButtonParams?.let {
            CustomButton(
                text = it.text,
                buttonStyle = ButtonStyle.Transparent,
                onClick = it.onClick
            )
        }

        WidthSpacer(8.dp)

        CustomButton(
            text = endButtonParams.text,
            buttonStyle = ButtonStyle.Transparent,
            onClick = endButtonParams.onClick
        )
    }
}