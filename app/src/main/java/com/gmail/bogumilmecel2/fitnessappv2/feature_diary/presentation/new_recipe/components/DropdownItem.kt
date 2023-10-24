package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_recipe.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.components.DropdownArrow
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme
import com.gmail.bogumilmecel2.ui.theme.LocalColor.BackgroundQuaternary

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropdownItem(
    onArrowClick: () -> Unit,
    selectedItem: String,
    isDropdownExpanded: Boolean,
    dropdownItems: List<String>,
    onItemSelected: (Int) -> Unit
) {
    Card(
        elevation = 2.dp,
        shape = FitnessAppTheme.shapes.Large,
        modifier = Modifier.size(width = 96.dp, height = 48.dp),
        onClick = { onArrowClick() },
        backgroundColor = BackgroundQuaternary
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedItem,
                style = FitnessAppTheme.typography.ParagraphLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f),
                maxLines = 1
            )

            DropdownArrow(
                isArrowPointedDownwards = isDropdownExpanded,
                onArrowClicked = onArrowClick
            )

            DropdownMenu(
                expanded = isDropdownExpanded,
                onDismissRequest = { onArrowClick() }
            ) {
                dropdownItems.forEachIndexed { index, i ->
                    DropdownMenuItem(onClick = { onItemSelected(index) }) {
                        Text(
                            text = i,
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
        }
    }
}