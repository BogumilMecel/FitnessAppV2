package com.gmail.bogumilmecel2.ui.components.complex

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.ui.components.base.CustomIcon
import com.gmail.bogumilmecel2.ui.components.base.Icon
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun CustomTabRow(
    modifier: Modifier = Modifier,
    selectedTabIndex: Int,
    tabs: List<Tab>,
    onTabSelected: (Int) -> Unit
) {
    Row(
        modifier = modifier
            .heightIn(min = 40.dp)
            .fillMaxWidth()
            .background(
                color = FitnessAppTheme.colors.BackgroundQuaternary,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(4.dp)
            .height(IntrinsicSize.Max)
    ) {
        tabs.forEachIndexed { index, (title, icon) ->
            val isThisTabActive = index == selectedTabIndex
            val shape = RoundedCornerShape(8.dp)
            val interactionSource = remember { MutableInteractionSource() }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clip(shape)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = { onTabSelected(index) }
                    )
                    .shadow(
                        elevation = if (isThisTabActive) 8.dp else 0.dp,
                        shape = shape,
                    )
                    .background(
                        color = if (isThisTabActive) {
                            FitnessAppTheme.colors.BackgroundSecondary
                        } else {
                            FitnessAppTheme.colors.Transparent
                        },
                        shape = shape
                    )
                    .padding(
                        vertical = 6.dp,
                        horizontal = 8.dp
                    ),
            ) {
                val contentColor = if (isThisTabActive) {
                    FitnessAppTheme.colors.ContentPrimary
                } else {
                    FitnessAppTheme.colors.ContentSecondary
                }
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    color = contentColor,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = FitnessAppTheme.typography.ParagraphMedium,
                )
                icon?.let { CustomIcon(icon = it, modifier = Modifier.size(24.dp)) }
            }
        }
    }
}

data class Tab(
    val title: String,
    val icon: Icon? = null,
)