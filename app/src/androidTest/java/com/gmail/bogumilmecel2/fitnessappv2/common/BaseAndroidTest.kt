package com.gmail.bogumilmecel2.fitnessappv2.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule

@HiltAndroidTest
open class BaseAndroidTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setupDaggerHilt() {
        hiltRule.inject()
    }

    fun setContent(content: @Composable () -> Unit) {
        composeRule.setContent {
            content()
        }
    }
}