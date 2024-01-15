package com.gmail.bogumilmecel2.ui.components.base

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.TextFieldLineLimits
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.clearText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    enabled: Boolean = true,
    maxLines: Int = 1,
    label: String? = null,
    errorMessage: String? = null,
    endContent: (@Composable () -> Unit)? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    val focused by interactionSource.collectIsFocusedAsState()

    var labelVisible by remember { mutableStateOf(false) }
    labelVisible = label != null && focused

    var hintVisible by remember { mutableStateOf(false) }
    hintVisible = state.text.isEmpty() && !focused && label != null

    var clearVisible by remember { mutableStateOf(false) }
    clearVisible = state.text.isNotEmpty() && focused

    val textStyle = FitnessAppTheme.typography.ParagraphLarge

    BasicTextField2(
        state = state,
        modifier = Modifier,
        interactionSource = interactionSource,
        textStyle = textStyle.copy(color = FitnessAppTheme.colors.ContentPrimary),
        cursorBrush = SolidColor(FitnessAppTheme.colors.ContentPrimary),
        enabled = enabled,
        lineLimits = TextFieldLineLimits.MultiLine(maxHeightInLines = maxLines, minHeightInLines = 1),
        decorator = { decorator ->
            val backgroundColor = FitnessAppTheme.colors.BackgroundPrimary
            val borderColor = when {
                errorMessage != null -> FitnessAppTheme.colors.Error
                focused -> FitnessAppTheme.colors.ContentPrimary
                else -> FitnessAppTheme.colors.ContentSecondary
            }
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Box {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                            .border(
                                width = if (focused) 2.dp else 1.dp,
                                color = borderColor,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .background(
                                color = backgroundColor,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .heightIn(min = 64.dp)
                            .padding(vertical = 16.dp)
                            .align(Alignment.Center),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                        ) {
                            Box(
                                modifier = Modifier.weight(1f),
                                propagateMinConstraints = true,
                                contentAlignment = if (maxLines == 1) Alignment.Center else Alignment.TopCenter
                            ) {
                                this@Row.AnimatedVisibility(
                                    visible = hintVisible,
                                    enter = fadeIn(),
                                    exit = fadeOut()
                                ) {
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        style = textStyle,
                                        color = FitnessAppTheme.colors.ContentSecondary,
                                        text = label.orEmpty(),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = if (maxLines == 1) Alignment.CenterVertically else Alignment.Top
                                ) {
                                    Box(modifier = Modifier.weight(1f)) {
                                        decorator()
                                    }

                                    AnimatedVisibility(
                                        visible = clearVisible,
                                        enter = fadeIn(),
                                        exit = fadeOut()
                                    ) {
                                        CustomIcon(
                                            icon = IconVector.Clear,
                                            modifier = Modifier
                                                .padding(start = 12.dp)
                                                .size(24.dp)
                                                .clickable { state.clearText() },
                                            tint = FitnessAppTheme.colors.ContentPrimary
                                        )
                                    }

                                    endContent?.invoke()
                                }
                            }
                        }
                    }
                    this@Column.AnimatedVisibility(
                        modifier = Modifier,
                        visible = labelVisible,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .drawBehind {
                                    drawRect(
                                        color = backgroundColor,
                                        topLeft = Offset(
                                            x = 0f,
                                            y = 8f
                                        )
                                    )
                                }
                                .padding(horizontal = 4.dp),
                            style = FitnessAppTheme.typography.LabelSmall,
                            color = borderColor,
                            text = label.orEmpty(),
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 16.dp)
                        .padding(horizontal = 4.dp)
                        .padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AnimatedVisibility(
                        visible = errorMessage != null,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        CustomIcon(
                            icon = IconVector.Warning,
                            modifier = Modifier.size(16.dp),
                            tint = FitnessAppTheme.colors.Error
                        )
                    }

                    AnimatedVisibility(
                        visible = errorMessage != null,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Text(
                            style = FitnessAppTheme.typography.ParagraphSmall,
                            color = FitnessAppTheme.colors.Error,
                            text = errorMessage.orEmpty(),
                            textAlign = TextAlign.Start,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }
        }
    )
}