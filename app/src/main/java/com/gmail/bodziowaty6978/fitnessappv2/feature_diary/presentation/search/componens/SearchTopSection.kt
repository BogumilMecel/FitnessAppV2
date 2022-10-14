package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.search.componens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.BackArrow
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.DefaultTextField
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.Grey
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.TextGrey
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.TextWhite
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.search.SearchEvent
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SearchTopSection(
    modifier: Modifier,
    searchBarText:String,
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BackArrow {
                onEvent(SearchEvent.ClickedBackArrow)
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = mealName,
                    style = MaterialTheme.typography.h2
                )

                Text(
                    text = date,
                    style = MaterialTheme.typography.body2,
                    color = TextGrey
                )

            }

            Spacer(modifier = Modifier.width(24.dp))
        }

        Spacer(modifier = Modifier.height(5.dp))

        DefaultTextField(
            value = searchBarText,
            placeholder = {
                Text(text = stringResource(id = R.string.product_name))
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag(stringResource(id = R.string.SEARCH_TEXT_FIELD))
                .padding(horizontal = 20.dp),
            onValueChange = {
                onEvent(SearchEvent.EnteredSearchText(it))
            },
            shape = RoundedCornerShape(25)
        )

        Spacer(modifier = Modifier.height(8.dp))

        TabRow(
            selectedTabIndex = pagerState.currentPage,
            contentColor = Color.White,
            backgroundColor = Grey
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
                selected = pagerState.currentPage==0,
                onClick = {
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
                selected = pagerState.currentPage==1,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(1)
                    }
                }
            )
        }
    }
}