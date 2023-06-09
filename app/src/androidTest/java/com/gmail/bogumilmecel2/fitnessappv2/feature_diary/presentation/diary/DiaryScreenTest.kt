package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.diary

import androidx.compose.ui.test.*
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.BaseAndroidTest
import com.gmail.bogumilmecel2.fitnessappv2.common.data.singleton.CurrentDate
import com.gmail.bogumilmecel2.fitnessappv2.common.di.AppModule
import com.gmail.bogumilmecel2.fitnessappv2.destinations.DiaryScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
internal class DiaryScreenTest: BaseAndroidTest() {

    lateinit var mealNames: List<String>

    @Before
    fun setUp() {
        setContent(destination = DiaryScreenDestination)
        mealNames = MealName.values().map {
            composeRule.activity.getString(it.getDisplayValue())
        }
    }

    @Test
    fun diaryScreenComposed_EveryMealSectionVisible() {
        mealNames.forEach {
            composeRule.onNodeWithTag(it).assertIsDisplayed()
        }
    }

    @Test
    fun diaryScreenComposed_TodayDisplayedInsteadOfDate() {
        val calendarSectionDate =
            composeRule.onNodeWithTag(composeRule.activity.getString(R.string.CALENDAR_DATE))
        calendarSectionDate.assertIsDisplayed().assert(
            hasText(composeRule.activity.getString(R.string.today))
        )
    }

    @Test
    fun diaryScreenComposed_YesterdayDisplayedInsteadOfDate() {
        composeRule.onNodeWithTag(composeRule.activity.getString(R.string.CALENDAR_BACK))
            .performClick()
        val calendarSectionDate =
            composeRule.onNodeWithTag(composeRule.activity.getString(R.string.CALENDAR_DATE))
        calendarSectionDate.assertIsDisplayed().assert(
            hasText(composeRule.activity.getString(R.string.yesterday))
        )
        CurrentDate.addDay()
    }

    @Test
    fun diaryScreenComposed_TomorrowDisplayedInsteadOfDate() {
        composeRule.onNodeWithTag(composeRule.activity.getString(R.string.CALENDAR_FORWARD))
            .performClick()
        val calendarSectionDate =
            composeRule.onNodeWithTag(composeRule.activity.getString(R.string.CALENDAR_DATE))
        calendarSectionDate.assertIsDisplayed().assert(
            hasText(composeRule.activity.getString(R.string.tomorrow))
        )
        CurrentDate.deductDay()
    }
}