package com.gmail.bogumilmecel2.ui.components.complex

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gmail.bogumilmecel2.ui.components.base.CustomLazyColumn
import com.gmail.bogumilmecel2.ui.components.base.WidthDivider

@Composable
fun DiaryList(
    items: List<SearchItemParams>,
    onScrollToEnd: (() -> Unit)? = null,
) {
    CustomLazyColumn(
        modifier = Modifier.fillMaxWidth(),
        onScrollToEnd = onScrollToEnd
    ) {
        items(items) { item ->
            DiaryItem(searchItemParams = item)
            WidthDivider()
        }
    }
}

@Composable
fun ForEachDiaryList(items: List<SearchItemParams>) {
    items.forEach {
        DiaryItem(searchItemParams = it)
        WidthDivider()
    }
}