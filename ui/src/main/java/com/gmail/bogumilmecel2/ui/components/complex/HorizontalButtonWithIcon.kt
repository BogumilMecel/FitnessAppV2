package com.gmail.bogumilmecel2.ui.components.complex

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.ui.components.base.CustomIcon
import com.gmail.bogumilmecel2.ui.components.base.HeightSpacer
import com.gmail.bogumilmecel2.ui.components.base.Icon
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun HorizontalButtonWithIcon(
    text: String,
    icon: Icon,
    modifier: Modifier = Modifier,
    contentColor: Color,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = FitnessAppTheme.colors.BackgroundSecondary
        ),
        border = BorderStroke(
            width = 1.dp,
            color = contentColor
        )
    ) {
        Column(
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomIcon(
                icon = icon,
                tint = contentColor,
            )

            HeightSpacer(4.dp)

            Text(
                text = text,
                style = MaterialTheme.typography.button,
                color = contentColor,
                maxLines = 1
            )
        }
    }
}