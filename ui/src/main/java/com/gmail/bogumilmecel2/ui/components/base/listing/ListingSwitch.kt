package com.gmail.bogumilmecel2.ui.components.base.listing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.ui.components.base.HeightSpacer
import com.gmail.bogumilmecel2.ui.components.base.WidthSpacer
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun ListingSwitch(
    modifier: Modifier = Modifier,
    topText: String,
    bottomText: String? = null,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = topText,
                style = FitnessAppTheme.typography.ParagraphLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            bottomText?.let {
                HeightSpacer(4.dp)

                Text(
                    text = bottomText,
                    style = FitnessAppTheme.typography.ParagraphMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = FitnessAppTheme.colors.ContentSecondary
                )
            }
        }

        WidthSpacer(8.dp)

        Switch(
            checked = checked,
            colors = SwitchDefaults.colors(
                uncheckedThumbColor = FitnessAppTheme.colors.ContentSecondary
            ),
            onCheckedChange = onCheckedChange,
        )
    }
}