package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_product

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.components.DropdownArrowSmall
import com.gmail.bogumilmecel2.fitnessappv2.common.util.ViewModelLayout2
import com.gmail.bogumilmecel2.ui.components.base.CustomButton
import com.gmail.bogumilmecel2.ui.components.base.CustomIcon
import com.gmail.bogumilmecel2.ui.components.base.CustomTextField
import com.gmail.bogumilmecel2.ui.components.base.HeightSpacer
import com.gmail.bogumilmecel2.ui.components.base.IconVector
import com.gmail.bogumilmecel2.ui.components.base.InputTransformations
import com.gmail.bogumilmecel2.ui.components.base.VerticalDivider
import com.gmail.bogumilmecel2.ui.components.base.WidthSpacer
import com.gmail.bogumilmecel2.ui.components.complex.CustomTab
import com.gmail.bogumilmecel2.ui.components.complex.CustomTabRow
import com.gmail.bogumilmecel2.ui.components.complex.HeaderRow
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalFoundationApi::class)
@Destination(navArgsDelegate = NewProductNavArguments::class)
@Composable
fun NewProductScreen(navigator: DestinationsNavigator) {
    hiltViewModel<NewProductViewModel>().ViewModelLayout2<NewProductViewModel>(navigator = navigator) {

        val dropDownMenuExpanded by dropDownMenuExpanded.collectAsStateWithLifecycle()
        val selectedMeasurementUnit by selectedMeasurementUnit.collectAsStateWithLifecycle()
        val selectedNutritionValuesIn by selectedNutritionValuesIn.collectAsStateWithLifecycle()
        val availableMeasurementUnits by availableMeasurementUnits.collectAsStateWithLifecycle()
        val nutritionValuesIn by nutritionValuesIn.collectAsStateWithLifecycle()

        Column(modifier = Modifier.fillMaxSize()) {
            HeaderRow(
                middlePrimaryText = stringResource(id = R.string.create_product),
                onBackPressed = {
                    onEvent(NewProductEvent.ClickedBackArrow)
                }
            )

            HeightSpacer(16.dp)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(state = rememberScrollState())
            ) {
                CustomTextField(
                    state = productName,
                    label = stringResource(id = R.string.product_name),
                )

                CustomTextField(
                    state = containerWeight,
                    label = stringResource(id = R.string.container_weight),
                    inputTransformation = InputTransformations.DigitsOnlyTransformation,
                    endContent = {
                        Row(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .height(IntrinsicSize.Max),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            DropdownMenu(
                                expanded = dropDownMenuExpanded,
                                onDismissRequest = { onEvent(NewProductEvent.ClickedDropDownMenu) }
                            ) {
                                availableMeasurementUnits.forEach { item ->
                                    DropdownMenuItem(
                                        onClick = {
                                            onEvent(NewProductEvent.SelectedMeasurementUnit(item))
                                        },
                                        modifier = Modifier
                                            .testTag(stringResource(id = item.getStringRes()) + "DROPDOWN_MENU_ITEM")
                                    ) {
                                        Text(
                                            text = stringResource(id = item.getStringRes()),
                                            style = MaterialTheme.typography.body1
                                        )
                                    }
                                }
                            }

                            VerticalDivider(
                                color = FitnessAppTheme.colors.ContentSecondary,
                                thickness = 2.dp
                            )

                            WidthSpacer(12.dp)

                            Text(
                                text = stringResource(id = selectedMeasurementUnit.getStringRes()),
                                style = FitnessAppTheme.typography.LabelLarge,
                            )

                            DropdownArrowSmall(
                                isArrowPointedDownwards = dropDownMenuExpanded,
                                onArrowClicked = { onEvent(NewProductEvent.ClickedDropDownMenu) }
                            )
                        }
                    }
                )

                CustomTextField(
                    state = barcode,
                    label = stringResource(id = R.string.bar_code),
                    endContent = {
                        CustomIcon(
                            icon = IconVector.barcode(),
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .size(24.dp)
                                .clickable { onEvent(NewProductEvent.ClickedScannerButton) },
                            tint = FitnessAppTheme.colors.ContentPrimary
                        )
                    }
                )

                HeightSpacer(16.dp)

                Text(
                    text = stringResource(id = R.string.nutrition_values),
                    style = FitnessAppTheme.typography.HeaderSmall,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                HeightSpacer(8.dp)

                CustomTabRow(
                    selectedTabIndex = selectedNutritionValuesIn.ordinal,
                    tabs = nutritionValuesIn.map {
                        CustomTab(title = stringResource(id = it.stringResId))
                    },
                    onTabSelected = {
                        onEvent(NewProductEvent.ClickedNutritionTab(position = it))
                    }
                )

                HeightSpacer(8.dp)

                CustomTextField(
                    state = calories,
                    label = stringResource(id = R.string.calories),
                    inputTransformation = InputTransformations.DigitsOnlyTransformation
                )

                CustomTextField(
                    state = carbohydrates,
                    label = stringResource(id = R.string.carbohydrates),
                    inputTransformation = InputTransformations.DecimalTransformation()
                )

                CustomTextField(
                    state = protein,
                    label = stringResource(id = R.string.protein),
                    inputTransformation = InputTransformations.DecimalTransformation()
                )

                CustomTextField(
                    state = fat,
                    label = stringResource(id = R.string.fat),
                    inputTransformation = InputTransformations.DecimalTransformation()
                )

                HeightSpacer(16.dp)
            }

            CustomButton(
                text = stringResource(id = R.string.add_product),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                leftIcon = IconVector.Save,
                onClick = { onEvent(NewProductEvent.ClickedSaveButton) },
            )
        }
    }
}