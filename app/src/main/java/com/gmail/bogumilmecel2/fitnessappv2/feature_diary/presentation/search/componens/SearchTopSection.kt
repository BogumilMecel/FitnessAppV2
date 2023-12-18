package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search.componens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search.SearchEvent
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search.SearchTab
import com.gmail.bogumilmecel2.ui.components.base.CustomBasicTextField
import com.gmail.bogumilmecel2.ui.components.base.CustomTabRow
import com.gmail.bogumilmecel2.ui.components.base.HeightSpacer
import com.gmail.bogumilmecel2.ui.components.complex.HeaderRow
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun SearchTopSection(
    searchBarText: String,
    mealName: String,
    date: String,
    selectedTabIndex: Int,
    onEvent: (SearchEvent) -> Unit,
) {
    val backgroundColor = FitnessAppTheme.colors.BackgroundSecondary

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = backgroundColor)
            .padding(top = 8.dp)
    ) {
        HeaderRow(
            middlePrimaryText = mealName,
            middleSecondaryText = date,
            onBackPressed = {
                onEvent(SearchEvent.ClickedBackArrow)
            }
        )

        HeightSpacer(8.dp)

        CustomBasicTextField(
            value = searchBarText,
            placeholder = when (selectedTabIndex) {
                SearchTab.RECIPES.ordinal -> stringResource(id = R.string.search_for_recipes)
                else -> stringResource(id = R.string.search_for_products)
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag(stringResource(id = R.string.SEARCH_TEXT_FIELD))
                .padding(horizontal = 16.dp),
            singleLine = true,
            onValueChange = {
                onEvent(SearchEvent.EnteredSearchText(it))
            }
        )

        HeightSpacer(8.dp)

        CustomTabRow(
            selectedTabIndex = selectedTabIndex,
            tabs = SearchTab.values().map {
                stringResource(id = it.textResId)
            },
            backgroundColor = backgroundColor,
            onTabSelected = {
                onEvent(SearchEvent.SelectedTab(it))
            }
        )
    }
}