package com.gmail.bogumilmecel2.ui.components.base

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun CustomTabRow(
    selectedTabIndex: Int,
    tabs: List<String>,
    backgroundColor: Color = FitnessAppTheme.colors.Background,
    onTabSelected: (Int) -> Unit
) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        backgroundColor = backgroundColor
    ) {
        tabs.forEachIndexed { index, tabText ->
            Tab(
                text = {
                    Text(
                        text = tabText,
                        style = MaterialTheme.typography.button,
                        color = FitnessAppTheme.colors.ContentPrimary
                    )
                },
                selected = index == selectedTabIndex,
                onClick = {
                    onTabSelected(index)
                }
            )
        }
    }
}