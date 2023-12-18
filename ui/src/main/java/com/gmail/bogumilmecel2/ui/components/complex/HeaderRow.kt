package com.gmail.bogumilmecel2.ui.components.complex

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.ui.components.base.CustomIcon
import com.gmail.bogumilmecel2.ui.components.base.CustomIconStyle
import com.gmail.bogumilmecel2.ui.components.base.CustomText
import com.gmail.bogumilmecel2.ui.components.base.FitnessAppTextStyle
import com.gmail.bogumilmecel2.ui.components.base.IconParams

@Composable
fun HeaderRow(
    middlePrimaryText: String?,
    middleSecondaryText: String? = null,
    onBackPressed: (() -> Unit)? = null,
    endIconParams: IconParams? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        onBackPressed?.let {
            CustomIcon(
                iconParams = IconParams(
                    iconStyle = CustomIconStyle.Back,
                    onClick = {
                        onBackPressed()
                    }
                )
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
                .then(
                    other = if (endIconParams == null) {
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
                CustomText(
                    text = middlePrimaryText,
                    fitnessAppTextStyle = FitnessAppTextStyle.HeaderLarge
                )
            }
            middleSecondaryText?.let {
                CustomText(
                    text = middleSecondaryText,
                    fitnessAppTextStyle = FitnessAppTextStyle.ParagraphSecondary
                )
            }
        }

        endIconParams?.let {
            CustomIcon(
                iconParams = endIconParams
            )
        }
    }
}