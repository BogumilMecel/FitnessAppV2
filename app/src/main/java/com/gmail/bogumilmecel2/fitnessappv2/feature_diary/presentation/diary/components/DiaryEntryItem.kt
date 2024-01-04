package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.diary.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.round
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntry
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme
import com.gmail.bogumilmecel2.ui.theme.LocalColor.BackgroundQuaternary

// TODO: Refactor it so it uses params
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DiaryEntryItem(
    diaryItem: DiaryItem,
    onItemClicked: () -> Unit,
    onItemLongClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = {
                    onItemClicked()
                },
                onLongClick = {
                    onItemLongClick()
                },
            )
            .background(BackgroundQuaternary),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 15.dp,
                    top = 15.dp
                )
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = when (diaryItem) {
                            is ProductDiaryEntry -> diaryItem.productName.orEmpty()
                            is RecipeDiaryEntry -> diaryItem.recipeName.orEmpty()
                            else -> ""
                        },
                        style = MaterialTheme.typography.body1
                    )

                    Text(
                        text = "(${diaryItem.getDisplayValue()})",
                        style = MaterialTheme.typography.body2,
                        color = FitnessAppTheme.colors.ContentSecondary,
                        modifier = Modifier
                            .padding(start = 3.dp)
                    )
                }

                diaryItem.nutritionValues?.let { nutritionValues ->
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
                            text = "Kcal:" + nutritionValues.calories.toString(),
                            style = MaterialTheme.typography.body2,
                            color = FitnessAppTheme.colors.ContentSecondary,
                        )

                        Text(
                            text = "Carb:" + nutritionValues.carbohydrates.round(2)
                                .toString(),
                            style = MaterialTheme.typography.body2,
                            color = FitnessAppTheme.colors.ContentSecondary
                        )

                        Text(
                            text = "Prot:" + nutritionValues.protein.round(2)
                                .toString(),
                            style = MaterialTheme.typography.body2,
                            color = FitnessAppTheme.colors.ContentSecondary
                        )

                        Text(
                            text = "Fat:" + nutritionValues.fat.round(2).toString(),
                            style = MaterialTheme.typography.body2,
                            color = FitnessAppTheme.colors.ContentSecondary
                        )
                    }
                }
            }
        }
    }
}