package com.gmail.bodziowaty6978.fitnessappv2.feature_account.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.BackHandler
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.BlueViolet3
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.LightGreen3
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.OrangeYellow3
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.TextGrey
import com.gmail.bodziowaty6978.fitnessappv2.feature_account.presentation.components.LogOutButton
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.components.ChartSection
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun AccountScreen(
    viewModel: AccountViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    LaunchedEffect(key1 = true) {
        viewModel.initData()
    }

    BackHandler {
        viewModel.onEvent(AccountEvent.BackPressed)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            shape = RoundedCornerShape(15.dp),
            elevation = 3.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .padding(bottom = 10.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Nutrition Goals",
                        style = MaterialTheme.typography.h3
                    )

                    IconButton(
                        onClick = {
                            viewModel.onEvent(AccountEvent.ClickedEditNutritionGoals)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    ChartSection(
                        modifier = Modifier.size(128.dp),
                        nutritionData = state.nutritionData,
                        isCircleTextVisible = true
                    )

                    Column {

                        val nutritionValues = state.nutritionData.nutritionValues

                        Text(
                            text = stringResource(id = R.string.carbohydrates),
                            style = MaterialTheme.typography.body2.copy(
                                color = TextGrey
                            )
                        )

                        Text(
                            text = "${nutritionValues.carbohydrates}",
                            style = MaterialTheme.typography.h4.copy(
                                color = LightGreen3
                            )
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = stringResource(id = R.string.protein),
                            style = MaterialTheme.typography.body2.copy(
                                color = TextGrey
                            )
                        )

                        Text(
                            text = "${nutritionValues.protein}",
                            style = MaterialTheme.typography.h4.copy(
                                color = BlueViolet3
                            )
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = stringResource(id = R.string.fat),
                            style = MaterialTheme.typography.body2.copy(
                                color = TextGrey
                            )
                        )

                        Text(
                            text = "${nutritionValues.fat}",
                            style = MaterialTheme.typography.h4.copy(
                                color = OrangeYellow3
                            )
                        )

                        Spacer(modifier = Modifier.height(6.dp))
                    }

                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LogOutButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) { viewModel.onEvent(AccountEvent.ClickedLogOutButtonClicked) }
    }
}