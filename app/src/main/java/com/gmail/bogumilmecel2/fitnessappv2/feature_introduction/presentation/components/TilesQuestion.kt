package com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.QuestionName
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.Tile

@Composable
fun TilesQuestion(
    questionName: QuestionName,
    currentItem:Tile,
    onItemClick:(Tile) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        questionName.getQuestionTiles().forEach {
            Tile(
                content = stringResource(id = it.getDisplayValue()),
                isSelected = it == currentItem,
                onItemClick = {
                    onItemClick(it)
                }
            )
        }
    }
}