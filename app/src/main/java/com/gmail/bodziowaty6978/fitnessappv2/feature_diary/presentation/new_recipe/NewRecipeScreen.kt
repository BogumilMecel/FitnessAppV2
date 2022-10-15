package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_recipe

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.BackArrow
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.CustomBasicTestField

@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalMaterialApi::class)
@Composable
fun NewRecipeScreen(
    viewModel: NewRecipeViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
        ) {
            BackArrow(
                modifier = Modifier.align(Alignment.CenterStart)
            ) {

            }

            Text(
                text = stringResource(id = R.string.add_recipe),
                style = MaterialTheme.typography.h3,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            shape = RoundedCornerShape(15),
            elevation = 2.dp
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Outlined.CameraAlt,
                    contentDescription = "camera",
                    Modifier.size(32.dp)
                )


                Spacer(modifier = Modifier.height(6.dp))

                OutlinedButton(
                    onClick = {
                        /*TODO*/
                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = Color.Transparent,
                    ),
                    border = BorderStroke(1.dp, color = MaterialTheme.colors.primary),
                    shape = RoundedCornerShape(15)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(id = R.string.add),
                            tint = MaterialTheme.colors.primary
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            text = stringResource(id = R.string.add_image).uppercase(),
                            style = MaterialTheme.typography.button.copy(
                                color = MaterialTheme.colors.primary
                            )
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier.padding(horizontal = 15.dp)
        ) {
            Text(
                text = stringResource(id = R.string.name), modifier = Modifier.padding(start = 4.dp)
            )

            Spacer(modifier = Modifier.height(6.dp))

            CustomBasicTestField(
                modifier = Modifier.fillMaxWidth(),
                value = state.name,
                onValueChange = {
                    viewModel.onEvent(NewRecipeEvent.EnteredName(it))
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
                        Icon(imageVector = Icons.Default.Equalizer, contentDescription = null)

                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            text = stringResource(id = R.string.difficulty),
                            style = MaterialTheme.typography.body2.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Card(elevation = 3.dp,
                         shape = RoundedCornerShape(30),
                         modifier = Modifier.width(80.dp),
                         onClick = {
                             viewModel.onEvent(NewRecipeEvent.ClickedDifficultyArrow)
                         }) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp)
                        ) {
                            Text(
                                text = state.selectedDifficulty,
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier.align(Alignment.CenterStart)
                            )

                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                modifier = Modifier.align(Alignment.CenterEnd)
                            )

                            DropdownMenu(expanded = state.isDifficultyExpanded, onDismissRequest = {
                                viewModel.onEvent(NewRecipeEvent.ClickedDifficultyArrow)
                            }) {
                                state.difficulties.forEachIndexed { index, i ->
                                    DropdownMenuItem(onClick = {
                                        viewModel.onEvent(
                                            NewRecipeEvent.SelectedDifficulty(
                                                index
                                            )
                                        )
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

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.AccessTime, contentDescription = null)

                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            text = stringResource(id = R.string.time),
                            style = MaterialTheme.typography.body2.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Card(elevation = 3.dp,
                         shape = RoundedCornerShape(30),
                         modifier = Modifier.width(100.dp),
                         onClick = {
                             viewModel.onEvent(NewRecipeEvent.ClickedTimeArrow)
                         }) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp)
                        ) {
                            Text(
                                text = state.selectedTime,
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier.align(Alignment.CenterStart)
                            )

                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                modifier = Modifier.align(Alignment.CenterEnd)
                            )

                            DropdownMenu(expanded = state.isTimeExpanded, onDismissRequest = {
                                viewModel.onEvent(NewRecipeEvent.ClickedTimeArrow)
                            }) {
                                state.times.forEachIndexed { index, i ->
                                    DropdownMenuItem(onClick = {
                                        viewModel.onEvent(NewRecipeEvent.SelectedTime(index))
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

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.SupervisorAccount, contentDescription = null
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            text = stringResource(id = R.string.servings),
                            style = MaterialTheme.typography.h3
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    CustomBasicTestField(
                        value = state.servings, onValueChange = {
                            viewModel.onEvent(NewRecipeEvent.EnteredServing(it))
                        }, modifier = Modifier.width(90.dp), singleLine = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = 3.dp,
                shape = RoundedCornerShape(15)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Ingredients List",
                        style = MaterialTheme.typography.h3.copy(
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)
                    )

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            text = "Add ingredient",
                            style = MaterialTheme.typography.button.copy(
                                color = MaterialTheme.colors.primary
                            )
                        )
                    }
                }
            }
        }
    }
}