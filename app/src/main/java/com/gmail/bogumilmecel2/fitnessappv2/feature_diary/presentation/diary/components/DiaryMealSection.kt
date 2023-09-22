package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.diary.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.components.DefaultCardBackground
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.diary.DiaryEvent
import com.gmail.bogumilmecel2.ui.theme.LocalColor.OrangeYellow1

@Composable
fun DiaryMealSection(
    mealName: MealName,
    diaryEntries: List<DiaryItem>?,
    nutritionValues: NutritionValues? = null,
    onEvent: (DiaryEvent) -> Unit,
    wantedNutritionValues: NutritionValues
) {
    DefaultCardBackground(
        modifier = Modifier.padding(
            horizontal = 10.dp,
            vertical = 6.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .testTag(stringResource(id = mealName.getDisplayValue()))
                .padding(bottom = 15.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = mealName.getDisplayValue()),
                    style = MaterialTheme.typography.h3,
                )

                Row {
                    Icon(imageVector = Icons.Default.Edit,
                         contentDescription = "Edit",
                         modifier = Modifier
                             .clickable {

                             }
                             .padding(10.dp)
                             .size(32.dp),
                         tint = OrangeYellow1)

                    Icon(imageVector = Icons.Default.AddCircle,
                         contentDescription = "Add",
                         modifier = Modifier
                             .clickable {
                                 onEvent(DiaryEvent.ClickedAddProduct(mealName = mealName))
                             }
                             .padding(10.dp)
                             .size(32.dp),
                         tint = OrangeYellow1)
                }
            }

            diaryEntries?.forEach {
                DiaryEntryItem(
                    diaryItem = it,
                    onItemClicked = {
                        onEvent(DiaryEvent.ClickedDiaryEntry(it))
                    },
                    onItemLongClick = {
                        onEvent(DiaryEvent.LongClickedDiaryEntry(
                            diaryItem = it,
                            mealName = mealName
                        ))
                    }
                )
            }

            Divider(
                modifier = Modifier.height(16.dp),
                color = Color.Transparent
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        end = 15.dp,
                        start = 15.dp
                    )
            ) {
                MealSectionNutritionItem(
                    currentValue = nutritionValues?.calories ?: 0,
                    wantedValue = wantedNutritionValues.calories,
                    name = stringResource(id = R.string.calories),
                    modifier = Modifier
                )

                Spacer(modifier = Modifier.width(32.dp))


                MealSectionNutritionItem(
                    currentValue = nutritionValues?.carbohydrates?.toInt() ?: 0,
                    wantedValue = wantedNutritionValues.carbohydrates.toInt(),
                    name = stringResource(id = R.string.carbs),
                    modifier = Modifier
                )

                Spacer(modifier = Modifier.width(32.dp))

                MealSectionNutritionItem(
                    currentValue = nutritionValues?.protein?.toInt() ?: 0,
                    wantedValue = wantedNutritionValues.protein.toInt(),
                    name = stringResource(id = R.string.protein),
                    modifier = Modifier

                )

                Spacer(modifier = Modifier.width(32.dp))


                MealSectionNutritionItem(
                    currentValue = nutritionValues?.fat?.toInt() ?: 0,
                    wantedValue = wantedNutritionValues.fat.toInt(),
                    name = stringResource(id = R.string.fat),
                    modifier = Modifier
                )
            }
        }
    }
}