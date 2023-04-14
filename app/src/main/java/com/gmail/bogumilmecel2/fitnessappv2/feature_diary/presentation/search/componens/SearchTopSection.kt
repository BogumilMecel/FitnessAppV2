package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search.componens

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.ui.theme.DarkGreyElevation1
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.ui.theme.TextWhite
import com.gmail.bogumilmecel2.fitnessappv2.components.CustomBasicTextField
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search.SearchEvent
import com.gmail.bogumilmecel2.ui.components.complex.HeaderRow
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SearchTopSection(
    modifier: Modifier,
    searchBarText: String,
    mealName: String,
    date: String,
    onEvent: (SearchEvent) -> Unit,
    pagerState: PagerState,
    scope: CoroutineScope
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        HeaderRow(
            middlePrimaryText = mealName,
            middleSecondaryText = date,
            onBackPressed = {
                onEvent(SearchEvent.ClickedBackArrow)
            }
        )

        Spacer(modifier = Modifier.height(5.dp))

        CustomBasicTextField(
            value = searchBarText,
            placeholder = when (pagerState.currentPage) {
                0 -> stringResource(id = R.string.product_name)
                else -> stringResource(id = R.string.recipe_name)
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag(stringResource(id = R.string.SEARCH_TEXT_FIELD))
                .padding(horizontal = 20.dp),
            onValueChange = {
                onEvent(SearchEvent.EnteredSearchText(it))
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        TabRow(
            selectedTabIndex = pagerState.currentPage,
            contentColor = Color.White,
            backgroundColor = DarkGreyElevation1
        ) {
            Tab(
                text = {
                    Text(
                        text = stringResource(id = R.string.products),
                        style = MaterialTheme.typography.button.copy(
                            color = TextWhite
                        )
                    )
                },
                selected = pagerState.currentPage == 0,
                onClick = {
                    onEvent(SearchEvent.ClickedProductsTab)
                    scope.launch {
                        pagerState.animateScrollToPage(0)
                    }
                }
            )

            Tab(
                text = {
                    Text(
                        text = stringResource(id = R.string.recipes),
                        style = MaterialTheme.typography.button.copy(
                            color = TextWhite
                        )
                    )
                },
                selected = pagerState.currentPage == 1,
                onClick = {
                    onEvent(SearchEvent.ClickedRecipesTab)
                    scope.launch {
                        pagerState.animateScrollToPage(1)
                    }
                }
            )
        }
    }
}