package com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomBasicTestField(
    modifier: Modifier = Modifier,
    elevation: Int = 3,
    value: String,
    onValueChange:(String) -> Unit,
    singleLine: Boolean = false
) {
    Card(
        shape = RoundedCornerShape(30),
        elevation = elevation.dp,
        modifier = modifier
    ) {
        BasicTextField(
            value = value,
            onValueChange ={
                onValueChange(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            textStyle = MaterialTheme.typography.body1,
            cursorBrush = SolidColor(Color.White),
            singleLine = singleLine
        )
    }
}