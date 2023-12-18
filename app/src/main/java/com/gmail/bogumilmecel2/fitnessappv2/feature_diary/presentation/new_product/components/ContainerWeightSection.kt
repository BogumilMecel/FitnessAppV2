package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_product.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.components.CustomBasicTextField
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.ui.theme.LightGrey
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_product.NewProductEvent
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_product.NewProductState

@Composable
fun ContainerWeightSection(
    state: NewProductState,
    onEvent: (NewProductEvent) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = when (state.nutritionValuesInSelectedTabIndex) {
                1 -> stringResource(R.string.container_weight) + "*"
                2 -> stringResource(R.string.average_weight) + "*"
                else -> stringResource(R.string.container_weight)
            },
            style = MaterialTheme.typography.body1,
            modifier = Modifier.weight(0.3F)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(0.4F)
        ) {
            CustomBasicTextField(
                value = state.containerWeightValue,
                onValueChange = {
                    onEvent(NewProductEvent.EnteredContainerWeight(it))
                },
                modifier = Modifier
                    .weight(1F)
                    .testTag(stringResource(id = R.string.container_weight) + "TEXT_FIELD"),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
            )

            Spacer(
                modifier = Modifier
                    .width(8.dp)
            )

            Row(
                modifier = Modifier
                    .border(1.dp, LightGrey)
                    .clip(RoundedCornerShape(15))
                    .border(1.dp, LightGrey)
                    .clickable {
                        onEvent(NewProductEvent.ClickedDropDownMenu)
                    }
                    .border(1.dp, LightGrey)
                    .testTag(stringResource(id = R.string.container_weight) + "DROPDOWN_MENU"),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(id = state.selectedMeasurementUnit.getDisplayValue()),
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .padding(horizontal = 15.dp, vertical = 15.dp)

                )
                Icon(
                    imageVector = if (state.isDropDownMenuExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                    contentDescription = "DropDown",
                    modifier = Modifier
                        .padding(end = 10.dp)
                )

                DropdownMenu(
                    expanded = state.isDropDownMenuExpanded,
                    onDismissRequest = {
                        onEvent(NewProductEvent.ClickedDropDownMenu)
                    }
                ) {
                    state.measurementUnits.forEach { item ->
                        DropdownMenuItem(
                            onClick = {
                                onEvent(NewProductEvent.SelectedMeasurementUnit(item))
                            },
                            modifier = Modifier
                                .testTag(stringResource(id = item.getDisplayValue()) + "DROPDOWN_MENU_ITEM")
                        ) {
                            Text(
                                text = stringResource(id = item.getDisplayValue()),
                                style = MaterialTheme.typography.body1
                            )
                        }
                    }
                }
            }
        }
    }
}