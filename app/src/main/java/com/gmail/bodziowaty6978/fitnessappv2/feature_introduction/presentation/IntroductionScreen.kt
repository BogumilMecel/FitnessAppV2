package com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.util.extensions.TAG
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.presentation.components.QuestionSection
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.presentation.components.TextQuestion
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.presentation.components.TilesQuestion
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalPagerApi::class, ExperimentalComposeUiApi::class)
@Destination
@Composable
fun IntroductionScreen(
    viewModel: IntroductionViewModel = hiltViewModel()
) {
    val questions = viewModel.questionState.value
    val answerState = viewModel.introductionAnswerState.value

    val pagerState = rememberPagerState(initialPage = 0)
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
        viewModel.introductionUiEvent.collectLatest {
            when (it) {
                is IntroductionUiEvent.MoveBackward -> {
                    if (pagerState.currentPage != 0) {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        keyboardController?.hide()
                    }
                }

                is IntroductionUiEvent.MoveForward -> {
                    if (pagerState.currentPage != questions.size - 1) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        keyboardController?.hide()
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

        HorizontalPager(
            count = questions.size,
            state = pagerState
        ) { pager ->
            val currentQuestionKey = questions.keys.toList()[pager]
            val currentQuestion = questions[currentQuestionKey]!!

            QuestionSection(title = currentQuestion.title) {
                if (currentQuestion.tiles.isNotEmpty()) {
                    TilesQuestion(
                        question = currentQuestion,
                        onItemClick = {
                            viewModel.onEvent(
                                IntroductionEvent.EnteredAnswer(
                                    currentQuestionKey,
                                    it.toString()
                                )
                            )
                        },
                        currentItem = answerState[currentQuestionKey]!!
                    )
                } else {
                    TextQuestion(
                        text = answerState[currentQuestionKey]!!,
                        onTextEntered = {
                            viewModel.onEvent(
                                IntroductionEvent.EnteredAnswer(
                                    currentQuestionKey,
                                    it
                                )
                            )
                        },
                        unit = currentQuestion.unit,
                        tag = currentQuestionKey.TAG
                    )
                }
            }

        }

        if (arrowState != 0) {
            Button(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(50))
                    .testTag(stringResource(id = R.string.BACK)),
                onClick = {
                    viewModel.onEvent(
                        IntroductionEvent.ClickedArrow(false)
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary
                )
            ) {
                Text(
                    text = stringResource(id = R.string.back),
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.padding(vertical = 6.dp, horizontal = 10.dp)
                )
            }
        }

        Button(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .clip(RoundedCornerShape(50))
                .testTag(stringResource(id = R.string.NEXT)),
            onClick = {
                viewModel.onEvent(
                    if (pagerState.currentPage == questions.size - 1) {
                        IntroductionEvent.FinishIntroduction
                    } else {
                        IntroductionEvent.ClickedArrow(true)
                    }
                )
            },
        ) {
            Text(
                text = if (pagerState.currentPage == questions.size - 1) {
                    stringResource(id = R.string.finish)
                } else {
                    stringResource(
                        id = R.string.next
                    )
                },
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .padding(vertical = 6.dp, horizontal = 10.dp)
                    .testTag(
                        stringResource(id = R.string.NEXT_TEXT)
                    )
            )
        }
    }
}