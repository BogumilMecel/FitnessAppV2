package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_recipe.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.components.DefaultCardBackground
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_recipe.NewRecipeEvent
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_recipe.NewRecipeState
import com.gmail.bogumilmecel2.ui.components.base.CustomBasicTextField
import com.gmail.bogumilmecel2.ui.components.base.CustomButton
import com.gmail.bogumilmecel2.ui.components.base.CustomIconStyle
import com.gmail.bogumilmecel2.ui.components.base.HeightSpacer
import com.gmail.bogumilmecel2.ui.components.base.WidthSpacer
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

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

        HeightSpacer(height = 6.dp)

        CustomBasicTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.name,
            placeholder = stringResource(id = R.string.recipe_name),
            onValueChange = {
                onEvent(NewRecipeEvent.EnteredName(it))
            })

        HeightSpacer()

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

                    WidthSpacer(width = 6.dp)

                    Text(
                        text = stringResource(id = R.string.difficulty),
                        style = MaterialTheme.typography.body2.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                HeightSpacer(height = 6.dp)

                DropdownItem(
                    onArrowClick = { onEvent(NewRecipeEvent.ClickedDifficultyArrow) },
                    selectedItem = state.selectedDifficulty.displayValue,
                    isDropdownExpanded = state.isDifficultyExpanded,
                    dropdownItems = state.difficulties.map { it.displayValue },
                    onItemSelected = {
                        onEvent(NewRecipeEvent.SelectedDifficulty(state.difficulties[it]))
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

                    WidthSpacer(width = 6.dp)

                    Text(
                        text = stringResource(id = R.string.time),
                        style = MaterialTheme.typography.body2.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                HeightSpacer(height = 6.dp)

                DropdownItem(
                    onArrowClick = { onEvent(NewRecipeEvent.ClickedTimeArrow) },
                    selectedItem = state.selectedTime.getDisplayValueWithoutMin(),
                    isDropdownExpanded = state.isTimeExpanded,
                    dropdownItems = state.times.map { it.displayValue },
                    onItemSelected = {
                        onEvent(NewRecipeEvent.SelectedTime(state.times[it]))
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

                    WidthSpacer(width = 6.dp)

                    Text(
                        text = stringResource(id = R.string.servings),
                        style = MaterialTheme.typography.h3
                    )
                }

                HeightSpacer(height = 6.dp)

                CustomBasicTextField(
                    value = state.servings,
                    onValueChange = {
                        onEvent(NewRecipeEvent.EnteredServing(it))
                    },
                    modifier = Modifier.width(80.dp).height(48.dp),
                    singleLine = true,
                    textAlign = TextAlign.Center
                )
            }
        }

        HeightSpacer()

        DefaultCardBackground(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(15.dp)
            ) {
                Column(
                    modifier = Modifier.weight(0.8f)
                ) {
                    Text(
                        text = stringResource(id = R.string.new_recipe_public),
                        style = MaterialTheme.typography.body1
                    )

                    HeightSpacer(height = 4.dp)

                    Text(
                        text = stringResource(id = R.string.new_recipe_public_des),
                        style = MaterialTheme.typography.body2.copy(color = FitnessAppTheme.colors.ContentSecondary)
                    )
                }

                WidthSpacer()

                Switch(
                    checked = state.isRecipePublic,
                    onCheckedChange = {
                        onEvent(NewRecipeEvent.SwitchedPublic(it))
                    },
                    modifier = Modifier.weight(0.2f)
                )
            }
        }

        HeightSpacer()

        CustomButton(
            modifier = Modifier.fillMaxWidth(),
            iconLeft = CustomIconStyle.Save,
            text = stringResource(id = R.string.new_recipe_save)
        ) {
            onEvent(NewRecipeEvent.ClickedSaveRecipe)
        }
    }
}