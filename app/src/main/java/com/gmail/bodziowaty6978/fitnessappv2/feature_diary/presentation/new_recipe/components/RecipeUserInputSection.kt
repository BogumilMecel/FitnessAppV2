package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_recipe.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Equalizer
import androidx.compose.material.icons.filled.SupervisorAccount
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.CustomBasicTextField
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_recipe.NewRecipeEvent
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_recipe.NewRecipeState

@Composable
fun RecipeUserInputSection(
    state: NewRecipeState,
    onEvent: (NewRecipeEvent) -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 15.dp)
    ) {
        Text(
            text = stringResource(id = R.string.name),
            modifier = Modifier.padding(start = 4.dp)
        )

        Spacer(modifier = Modifier.height(6.dp))

        CustomBasicTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.name,
            placeholder = stringResource(id = R.string.recipe_name),
            onValueChange = {
                onEvent(NewRecipeEvent.EnteredName(it))
            })

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Equalizer,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = stringResource(id = R.string.difficulty),
                        style = MaterialTheme.typography.body2.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                DropdownItem(
                    onArrowClick = { onEvent(NewRecipeEvent.ClickedDifficultyArrow) },
                    selectedItem = state.selectedDifficulty,
                    isDropdownExpanded = state.isDifficultyExpanded,
                    dropdownItems = state.difficulties,
                    onItemSelected = {
                        onEvent(NewRecipeEvent.SelectedDifficulty(it))
                    }
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = stringResource(id = R.string.time),
                        style = MaterialTheme.typography.body2.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                DropdownItem(
                    onArrowClick = { onEvent(NewRecipeEvent.ClickedTimeArrow) },
                    selectedItem = state.selectedTime,
                    isDropdownExpanded = state.isTimeExpanded,
                    dropdownItems = state.times,
                    onItemSelected = {
                        onEvent(NewRecipeEvent.SelectedTime(it))
                    }
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.SupervisorAccount,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = stringResource(id = R.string.servings),
                        style = MaterialTheme.typography.h3
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                CustomBasicTextField(
                    value = state.servings, onValueChange = {
                        onEvent(NewRecipeEvent.EnteredServing(it))
                    }, modifier = Modifier.width(90.dp), singleLine = true
                )
            }
        }
    }
}