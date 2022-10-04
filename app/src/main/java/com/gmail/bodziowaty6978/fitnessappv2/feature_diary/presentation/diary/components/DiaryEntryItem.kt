package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.diary.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.TextGrey
import com.gmail.bodziowaty6978.fitnessappv2.common.util.extensions.round
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.diary_entry.DiaryEntry

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DiaryEntryItem(
    diaryEntry: DiaryEntry,
    onItemClicked: () -> Unit,
    onItemLongClick: () -> Unit
) {

    Card(
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = {
                    onItemClicked()
                },
                onLongClick = {
                    onItemLongClick()
                },
            ),
        shape = RectangleShape,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp, top = 15.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = diaryEntry.product.name,
                        style = MaterialTheme.typography.body1
                    )

                    Text(
                        text = "(${diaryEntry.weight}${diaryEntry.product.unit})",
                        style = MaterialTheme.typography.body2,
                        color = TextGrey,
                        modifier = Modifier
                            .padding(start = 3.dp)
                    )
                }

                Divider(
                    modifier = Modifier
                        .height(4.dp),
                    color = Color.Transparent
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Kcal:" + diaryEntry.product.nutritionValues.calories.toString(),
                        style = MaterialTheme.typography.body2,
                        color = TextGrey,
                    )

                    Text(
                        text = "Carb:" + diaryEntry.product.nutritionValues.carbohydrates.round(2).toString(),
                        style = MaterialTheme.typography.body2,
                        color = TextGrey
                    )

                    Text(
                        text = "Prot:" + diaryEntry.product.nutritionValues.protein.round(2).toString(),
                        style = MaterialTheme.typography.body2,
                        color = TextGrey
                    )

                    Text(
                        text = "Fat:" + diaryEntry.product.nutritionValues.fat.round(2).toString(),
                        style = MaterialTheme.typography.body2,
                        color = TextGrey
                    )

                }
            }
        }
    }
}