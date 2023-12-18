package com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TilesQuestion(
    tilesValues: List<String>,
    currentItem:String,
    onItemClick:(Int) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        tilesValues.forEachIndexed { index, content ->
            Tile(
                content = content,
                isSelected = index==currentItem.toInt(),
                onItemClick = {
                    onItemClick(index)
                }
            )
        }

    }
}