package com.gmail.bogumilmecel2.ui.components.complex

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.ui.components.base.CustomIconButton
import com.gmail.bogumilmecel2.ui.components.base.IconButtonParams
import com.gmail.bogumilmecel2.ui.components.base.IconVector
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun HeaderRow(
    middlePrimaryText: String?,
    middleSecondaryText: String? = null,
    onBackPressed: (() -> Unit)? = null,
    endIconButtonParams: IconButtonParams? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        onBackPressed?.let {
            CustomIconButton(
                icon = IconVector.Back,
                onClick = onBackPressed,
                tint = FitnessAppTheme.colors.ContentPrimary
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
                .then(
                    other = if (endIconButtonParams == null) {
                        Modifier.padding(end = 48.dp)
                    } else Modifier
                )
                .then(
                    other = if (onBackPressed == null) {
                        Modifier.padding(start = 48.dp)
                    } else Modifier
                )
        ) {
            middlePrimaryText?.let {
                Text(
                    text = middlePrimaryText,
                    style = FitnessAppTheme.typography.HeaderLarge
                )
            }
            middleSecondaryText?.let {
                Text(
                    text = middleSecondaryText,
                    style = FitnessAppTheme.typography.ParagraphMedium,
                    color = FitnessAppTheme.colors.ContentSecondary
                )
            }
        }

        endIconButtonParams?.let {
            CustomIconButton(
                icon = endIconButtonParams.iconVector,
                onClick = endIconButtonParams.onClick,
                enabled = endIconButtonParams.enabled,
            )
        }
    }
}