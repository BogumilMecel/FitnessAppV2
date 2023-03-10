package com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.CustomBasicTextField

@Composable
fun TextQuestion(
    text: String,
    unitResId: Int?,
    onTextEntered: (String) -> Unit,
    tag:String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomBasicTextField(
            value = text,
            onValueChange = {
                onTextEntered(it)
            },
            modifier = Modifier
                .width(80.dp)
                .testTag(tag),
            textStyle = MaterialTheme.typography.h2.copy(
                textAlign = TextAlign.Center
            ),
            singleLine = true
        )

        unitResId?.let {
            Text(
                text = stringResource(id = it),
                style = MaterialTheme.typography.body1.copy(
                    color = MaterialTheme.colors.onBackground
                ),
                modifier = Modifier
                    .padding(start = 10.dp)
            )
        }
    }
}