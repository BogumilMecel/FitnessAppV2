package com.gmail.bogumilmecel2.ui.components.complex

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.ui.components.base.ButtonParams
import com.gmail.bogumilmecel2.ui.components.base.ButtonStyle
import com.gmail.bogumilmecel2.ui.components.base.CustomButton
import com.gmail.bogumilmecel2.ui.components.base.HeightSpacer
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun SimpleSheetLayoutContent(
    title: String,
    description: String? = null,
    content: (@Composable () -> Unit)? = null,
    firstButtonParams: ButtonParams,
    secondButtonParams: ButtonParams? = null,
    bottomTextButtonParams: ButtonParams? = null
) {
    Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = FitnessAppTheme.typography.HeaderLarge,
            textAlign = TextAlign.Center,
            color = FitnessAppTheme.colors.ContentPrimary
        )

        HeightSpacer(16.dp)

        description?.let {
            Text(
                text = it,
                style = FitnessAppTheme.typography.ParagraphMedium,
                textAlign = TextAlign.Center,
                color = FitnessAppTheme.colors.ContentPrimary
            )

            HeightSpacer(16.dp)
        }

        content?.let {
            content()

            HeightSpacer(16.dp)
        }

        CustomButton(
            text = firstButtonParams.text,
            onClick = firstButtonParams.onClick,
            modifier = Modifier
                .fillMaxWidth(),
        )

        HeightSpacer(8.dp)

        secondButtonParams?.let {
            CustomButton(
                text = it.text,
                onClick = it.onClick,
                modifier = Modifier
                    .fillMaxWidth(),
                buttonStyle = ButtonStyle.OutlinedPrimaryButton
            )

            HeightSpacer(8.dp)
        }

        bottomTextButtonParams?.let {
            HeightSpacer(8.dp)

            Text(
                text = it.text,
                style = FitnessAppTheme.typography.ParagraphMedium,
                textAlign = TextAlign.Center,
                color = FitnessAppTheme.colors.ContentSecondary,
                modifier = Modifier.clickable {
                    it.onClick()
                }
            )

            HeightSpacer(4.dp)
        }
    }
}