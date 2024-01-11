package com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.util.ViewModelLayout
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.QuestionName
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.QuestionType
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.presentation.components.BottomButton
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.presentation.components.QuestionSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.presentation.components.TextQuestion
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.presentation.components.TilesQuestion
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest

@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalFoundationApi::class
)
@Destination
@Composable
fun IntroductionScreen(navigator: DestinationsNavigator) {
    hiltViewModel<IntroductionViewModel>().ViewModelLayout(navigator = navigator) {
        val state by __state.collectAsStateWithLifecycle()
        val questionSize = QuestionName.entries.size

        val pagerState = rememberPagerState(
            initialPage = 0,
        ) {
            questionSize
        }
        val keyboardController = LocalSoftwareKeyboardController.current

        var arrowState by remember {
            mutableStateOf(0)
        }

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect {
                arrowState = when (pagerState.currentPage) {
                    0 -> 0
                    else -> 2
                }
            }
        }

        LaunchedEffect(key1 = true) {
            (this@ViewModelLayout as IntroductionViewModel).introductionUiEvent.collectLatest {
                when (it) {
                    is IntroductionUiEvent.MoveBackward -> {
                        if (pagerState.currentPage != 0) {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            keyboardController?.hide()
                        }
                    }

                    is IntroductionUiEvent.MoveForward -> {
                        if (pagerState.currentPage != questionSize - 1) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            keyboardController?.hide()
                        } else {
                            onEvent(IntroductionEvent.FinishIntroduction)
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(id = R.string.let_us_know_something_about_you),
                style = MaterialTheme.typography.h1,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 100.dp)
            )

            HorizontalPager(state = pagerState) { index ->
                val currentItem = QuestionName.entries[index]
                QuestionSection(title = stringResource(id = currentItem.getQuestionTitle())) {
                    when (currentItem.getQuestionType()) {
                        QuestionType.INPUT -> {
                            state.getStringAnswer(currentItem)?.let { answer ->
                                TextQuestion(
                                    text = answer,
                                    onTextEntered = {
                                        onEvent(
                                            IntroductionEvent.EnteredAnswer(
                                                questionName = currentItem,
                                                newValue = it
                                            )
                                        )
                                    },
                                    unitResId = currentItem.getQuestionUnit(),
                                    tag = currentItem.name
                                )
                            }
                        }

                        QuestionType.TILE -> {
                            state.getTileAnswer(questionName = currentItem)?.let { selectedTile ->
                                TilesQuestion(
                                    questionName = currentItem,
                                    currentItem = selectedTile,
                                    onItemClick = { clickedTile ->
                                        onEvent(
                                            IntroductionEvent.ClickedTile(
                                                tile = clickedTile
                                            )
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }

            if (arrowState != 0) {
                BottomButton(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .testTag(stringResource(id = R.string.BACK)),
                    text = stringResource(id = R.string.back),
                    onClick = {
                        onEvent(IntroductionEvent.ClickedArrowBackwards)
                    },
                    buttonColors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.secondary
                    )
                )
            }

            BottomButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .testTag(stringResource(id = R.string.NEXT)),
                text = stringResource(
                    id = if (pagerState.currentPage == questionSize - 1) R.string.finish else R.string.next
                ),
                onClick = {
                    onEvent(IntroductionEvent.ClickedArrowForward)
                }
            )
        }
    }
}