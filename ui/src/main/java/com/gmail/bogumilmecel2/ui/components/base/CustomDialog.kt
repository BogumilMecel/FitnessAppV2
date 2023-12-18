package com.gmail.bogumilmecel2.ui.components.base

import android.view.Gravity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun CustomDialog(
    title: String,
    secondaryText: String,
    endButtonParams: ButtonParams,
    secondaryButtonParams: ButtonParams? = null,
    extraButtonParams: ButtonParams? = null,
    onDismissRequest: () -> Unit,
) {
    CustomDialog(
        title = title,
        content = {
            Text(
                text = secondaryText,
                style = FitnessAppTheme.typography.ParagraphMedium,
                color = FitnessAppTheme.colors.ContentSecondary,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        },
        endButtonParams = endButtonParams,
        secondaryButtonParams = secondaryButtonParams,
        extraButtonParams = extraButtonParams,
        onDismissRequest = onDismissRequest
    )
}

@Composable
fun CustomDialog(
    title: String,
    content: @Composable () -> Unit,
    endButtonParams: ButtonParams,
    secondaryButtonParams: ButtonParams? = null,
    extraButtonParams: ButtonParams? = null,
    onDismissRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        val dialogWindowProvider = LocalView.current.parent as DialogWindowProvider
        dialogWindowProvider.window.setGravity(Gravity.BOTTOM)

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            color = FitnessAppTheme.colors.BackgroundTertiary,
        ) {
            Column {
                HeightSpacer(24.dp)

                Text(
                    text = title,
                    style = FitnessAppTheme.typography.HeaderMedium,
                    color = FitnessAppTheme.colors.ContentPrimary,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )

                HeightSpacer()

                content()

                HeightSpacer()

                val buttonModifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)

                CustomButton(
                    text = endButtonParams.text,
                    buttonStyle = ButtonStyle.Primary,
                    onClick = endButtonParams.onClick,
                    modifier = buttonModifier
                )

                secondaryButtonParams?.let {
                    HeightSpacer(4.dp)

                    CustomButton(
                        text = secondaryButtonParams.text,
                        buttonStyle = ButtonStyle.OutlinedPrimary,
                        onClick = secondaryButtonParams.onClick,
                        modifier = buttonModifier
                    )
                }

                extraButtonParams?.let {
                    HeightSpacer(12.dp)

                    Box(
                        modifier = buttonModifier,
                        contentAlignment = Alignment.Center
                    ) {
                        ClickableText(
                            text = extraButtonParams.text,
                            onClick = extraButtonParams.onClick,
                        )
                    }
                }

                HeightSpacer(24.dp)
            }
        }
    }
}