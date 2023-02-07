package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_recipe.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropdownItem(
    onArrowClick: () -> Unit,
    selectedItem: String,
    isDropdownExpanded: Boolean,
    dropdownItems: List<String>,
    onItemSelected:(Int) -> Unit
) {
    Card(elevation = 3.dp,
         shape = RoundedCornerShape(30),
         modifier = Modifier.width(80.dp),
         onClick = {
             onArrowClick()
         }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Text(
                text = selectedItem,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.align(Alignment.CenterStart)
            )

            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterEnd)
            )

            DropdownMenu(
                expanded = isDropdownExpanded,
                onDismissRequest = {
                    onArrowClick()
                }) {
                dropdownItems.forEachIndexed { index, i ->
                    DropdownMenuItem(onClick = {
                        onItemSelected(index)
                    }) {
                        Text(
                            text = i, style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
        }

    }
}