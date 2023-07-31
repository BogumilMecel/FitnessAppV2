package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search.componens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SearchList(items: List<SearchItemParams>) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(items) { item ->
            SearchItem(searchItemParams = item)
        }
    }
}

@Composable
fun ForEachSearchList(items: List<SearchItemParams>) {
    items.forEach {
        SearchItem(searchItemParams = it)
    }
}