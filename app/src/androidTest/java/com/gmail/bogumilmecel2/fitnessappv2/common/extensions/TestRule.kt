package com.gmail.bogumilmecel2.fitnessappv2.common.extensions

import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.MainActivity

fun AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>.clearAndSetContent(content: @Composable () -> Unit) {
    (this.activity.findViewById<ViewGroup>(android.R.id.content)?.getChildAt(0) as? ComposeView)?.setContent(content)
        ?: this.setContent(content)
}