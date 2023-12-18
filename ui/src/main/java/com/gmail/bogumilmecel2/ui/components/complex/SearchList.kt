package com.gmail.bogumilmecel2.ui.components.complex

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gmail.bogumilmecel2.ui.components.base.CustomLazyColumn
import com.gmail.bogumilmecel2.ui.components.base.WidthDivider

@Composable
fun SearchList(
    items: List<SearchItemParams>,
    onScrollToEnd: (() -> Unit)? = null,
) {
    CustomLazyColumn(
        modifier = Modifier.fillMaxWidth(),
        onScrollToEnd = onScrollToEnd
    ) {
        items(items) { item ->
            SearchItem(searchItemParams = item)
            WidthDivider()
        }
    }
}

@Composable
fun ForEachSearchList(items: List<SearchItemParams>) {
    items.forEach {
        SearchItem(searchItemParams = it)
        WidthDivider()
    }
}