package com.gmail.bogumilmecel2.fitnessappv2.common.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.util.BottomBarScreen
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun RowScope.AddItem(
    bottomBarScreen: BottomBarScreen,
    screensNumber: Int,
    isSelected: Boolean,
    onItemClicked: () -> Unit
) {
    val animationSpec = tween<Color>(
        durationMillis = 200,
        easing = LinearEasing
    )

    val selectedColor by animateColorAsState(
        targetValue = FitnessAppTheme.colors.ContentPrimary,
        label = "selected bottom navigation item color",
        animationSpec = animationSpec
    )

    val dividerColor by animateColorAsState(
        targetValue = if (isSelected) FitnessAppTheme.colors.ContentPrimary else Color.Transparent,
        label = "bottom navigation divider color",
        animationSpec = animationSpec
    )

    val notSelectedColor by animateColorAsState(
        targetValue = FitnessAppTheme.colors.ContentTertiary,
        label = "not selected bottom navigation item color",
        animationSpec = animationSpec
    )

    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider(
            modifier = Modifier
                .width(LocalConfiguration.current.screenWidthDp.dp / screensNumber),
            color = dividerColor
        )

        this@AddItem.BottomNavigationItem(
            label = { Text(text = stringResource(id = bottomBarScreen.labelResId)) },
            icon = {
                Icon(
                    imageVector = bottomBarScreen.icon,
                    contentDescription = stringResource(id = bottomBarScreen.labelResId)
                )
            },
            selected = isSelected,
            onClick = onItemClicked,
            selectedContentColor = selectedColor,
            unselectedContentColor = notSelectedColor
        )
    }
}