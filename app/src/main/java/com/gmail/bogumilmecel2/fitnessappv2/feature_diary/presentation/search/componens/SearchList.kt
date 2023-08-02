package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search.componens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun SearchList(items: List<SearchItemParams>) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(items) { item ->
            SearchItem(searchItemParams = item)
            SearchListDivider()
        }
    }
}

@Composable
fun ForEachSearchList(items: List<SearchItemParams>) {
    items.forEach {
        SearchItem(searchItemParams = it)
        SearchListDivider()
    }
}

@Composable
private fun SearchListDivider() {
    Divider(
        thickness = 1.dp,
        modifier = Modifier.fillMaxWidth(),
        color = FitnessAppTheme.colors.BackgroundQuaternary
    )
}