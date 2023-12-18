package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search.componens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.ui.components.base.ButtonParams
import com.gmail.bogumilmecel2.ui.components.base.HeightSpacer
import com.gmail.bogumilmecel2.ui.components.base.Icon
import com.gmail.bogumilmecel2.ui.components.complex.DiaryList
import com.gmail.bogumilmecel2.ui.components.complex.HorizontalButtonWithIcon
import com.gmail.bogumilmecel2.ui.components.complex.SearchItemParams

@Composable
fun SearchSection(
    modifier: Modifier,
    contentColor: Color,
    buttonParams: List<SearchButtonParams>,
    searchItems: List<SearchItemParams>,
    onListEndReached: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            buttonParams.forEach { button ->
                HorizontalButtonWithIcon(
                    modifier = Modifier.weight(1F),
                    text = button.buttonParams.text,
                    icon = button.icon,
                    contentColor = contentColor,
                    onClick = button.buttonParams.onClick
                )
            }
        }

        HeightSpacer()

        if (searchItems.isNotEmpty()) {
            DiaryList(
                items = searchItems,
                onScrollToEnd = {
                    onListEndReached()
                }
            )
        }
    }
}

data class SearchButtonParams(
    val buttonParams: ButtonParams,
    val icon: Icon
)