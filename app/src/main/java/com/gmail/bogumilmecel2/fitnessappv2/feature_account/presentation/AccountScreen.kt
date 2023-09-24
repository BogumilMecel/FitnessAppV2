package com.gmail.bogumilmecel2.fitnessappv2.feature_account.presentation

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
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.components.BackHandler
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.components.PieChartWithMiddleText
import com.gmail.bogumilmecel2.fitnessappv2.common.util.ConfigureViewModel
import com.gmail.bogumilmecel2.fitnessappv2.components.DefaultCardBackground
import com.gmail.bogumilmecel2.ui.components.base.CustomButton
import com.gmail.bogumilmecel2.ui.components.base.CustomIconButton
import com.gmail.bogumilmecel2.ui.components.base.IconButtonParams
import com.gmail.bogumilmecel2.ui.components.base.IconVector
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme
import com.gmail.bogumilmecel2.ui.theme.LocalColor.BlueViolet3
import com.gmail.bogumilmecel2.ui.theme.LocalColor.LightGreen3
import com.gmail.bogumilmecel2.ui.theme.LocalColor.OrangeYellow3
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun AccountScreen(
    viewModel: AccountViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    ConfigureViewModel(viewModel = viewModel)

    BackHandler {
        viewModel.onEvent(AccountEvent.BackPressed)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(10.dp))

        DefaultCardBackground(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
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
                        text = stringResource(id = R.string.nutrition_goals),
                        style = FitnessAppTheme.typography.HeaderSmall,
                        color = FitnessAppTheme.colors.ContentPrimary
                    )

                    CustomIconButton(
                        params = IconButtonParams(
                            iconVector = IconVector.Edit,
                            onClick = {
                                viewModel.onEvent(AccountEvent.ClickedEditNutritionGoals)
                            }
                        )
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    PieChartWithMiddleText(
                        pieChartData = state.nutritionData.pieChartData,
                        middleText = stringResource(
                            id = R.string.kcal_with_value,
                            state.nutritionData.nutritionValues.calories
                        ),
                        modifier = Modifier.size(128.dp),
                    )

                    Column {
                        val nutritionValues = state.nutritionData.nutritionValues

                        Text(
                            text = stringResource(id = R.string.carbohydrates),
                            style = FitnessAppTheme.typography.ParagraphMedium,
                            color = FitnessAppTheme.colors.ContentSecondary
                        )

                        Text(
                            text = "${nutritionValues.carbohydrates}",
                            style = FitnessAppTheme.typography.HeaderExtraSmall,
                            color = LightGreen3
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = stringResource(id = R.string.protein),
                            style = FitnessAppTheme.typography.ParagraphMedium,
                            color = FitnessAppTheme.colors.ContentSecondary
                        )

                        Text(
                            text = "${nutritionValues.protein}",
                            style = FitnessAppTheme.typography.HeaderExtraSmall,
                            color = BlueViolet3
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = stringResource(id = R.string.fat),
                            style = FitnessAppTheme.typography.ParagraphMedium,
                            color = FitnessAppTheme.colors.ContentSecondary
                        )

                        Text(
                            text = "${nutritionValues.fat}",
                            style = FitnessAppTheme.typography.HeaderExtraSmall,
                            color = OrangeYellow3
                        )

                        Spacer(modifier = Modifier.height(6.dp))
                    }

                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        DefaultCardBackground(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.account_ask_for_weight_daily),
                    style = FitnessAppTheme.typography.ParagraphLarge,
                    modifier = Modifier.weight(1f)
                )

                Switch(
                    checked = true,
                    onCheckedChange = {},
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        CustomButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            leftIcon = IconVector.Logout,
            text = stringResource(id = R.string.log_out),
        ) {
            viewModel.onEvent(AccountEvent.ClickedLogOutButtonClicked)
        }
    }
}