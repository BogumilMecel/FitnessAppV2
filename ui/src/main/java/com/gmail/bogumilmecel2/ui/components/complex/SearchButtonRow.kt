package com.gmail.bogumilmecel2.ui.components.complex

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.ui.components.base.ButtonParams
import com.gmail.bogumilmecel2.ui.components.base.Icon

@Composable
fun SearchButtonRow(
    buttons: List<SearchButtonParams>,
    buttonsColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        buttons.forEach { button ->
            HorizontalButtonWithIcon(
                modifier = Modifier.weight(1F),
                text = button.buttonParams.text,
                icon = button.icon,
                contentColor = buttonsColor,
                onClick = button.buttonParams.onClick
            )
        }
    }
}

data class SearchButtonParams(
    val buttonParams: ButtonParams,
    val icon: Icon
)