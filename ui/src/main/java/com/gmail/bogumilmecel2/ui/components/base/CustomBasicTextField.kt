package com.gmail.bogumilmecel2.ui.components.base

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme
import com.gmail.bogumilmecel2.ui.theme.LocalColor.DarkGreyElevation9

@Composable
fun CustomBasicTextField(
    modifier: Modifier = Modifier,
    value: String,
    testTag: String? = null,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = false,
    placeholder: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    textAlign: TextAlign = TextAlign.Start,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon: IconVector? = null
) {
    val textPadding = PaddingValues(
        start = 12.dp,
        end = 12.dp,
        top = 12.dp,
        bottom = 12.dp
    )
    var isFocused by remember { mutableStateOf(false) }

    Card(
        elevation = 2.dp,
        shape = RoundedCornerShape(15.dp),
        modifier = modifier,
        backgroundColor = DarkGreyElevation9
    ) {
        BasicTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = textPadding.calculateTopPadding(),
                    end = textPadding.calculateEndPadding(LayoutDirection.Ltr),
                    bottom = textPadding.calculateBottomPadding(),
                    start = if (leadingIcon != null) {
                        (textPadding.calculateStartPadding(LayoutDirection.Ltr).value + 28f).dp
                    } else {
                        textPadding.calculateStartPadding(LayoutDirection.Ltr)
                    },
                )
                .then(
                    other = testTag?.let { tag ->
                        Modifier.testTag(tag)
                    } ?: Modifier
                )
                .onFocusChanged {
                    isFocused = it.isFocused
                },
            textStyle = FitnessAppTheme.typography.ParagraphLarge.copy(
                color = FitnessAppTheme.colors.ContentPrimary,
                textAlign = textAlign
            ),
            cursorBrush = SolidColor(Color.White),
            singleLine = singleLine,
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(textPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIcon?.let {
                CustomIcon(
                    iconStyle = it,
                    modifier = Modifier.size(24.dp)
                )
            }

            WidthSpacer(width = 4.dp)

            if (value.isBlank() && placeholder != null && !isFocused) {
                CustomText(
                    text = placeholder,
                    fitnessAppTextStyle = FitnessAppTextStyle.ParagraphSecondaryLarge,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = textAlign
                )
            }
        }
    }
}
