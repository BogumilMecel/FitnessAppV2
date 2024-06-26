package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search

//import androidx.compose.ui.test.*
//import androidx.compose.ui.test.junit4.createAndroidComposeRule
//import com.gmail.bogumilmecel2.fitnessappv2.R
//import com.gmail.bogumilmecel2.fitnessappv2.common.domain.navigation.Navigator
//import com.gmail.bogumilmecel2.fitnessappv2.common.extensions.clearAndSetContent
//import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.MainActivity
//import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.navigation.NavHostGraph
//import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.ui.theme.FitnessAppV2Theme
//import dagger.hilt.android.testing.HiltAndroidRule
//import dagger.hilt.android.testing.HiltAndroidTest
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.mockito.junit.MockitoJUnitRunner
//import javax.inject.Inject
//
//@HiltAndroidTest
//@RunWith(MockitoJUnitRunner::class)
//internal class SearchScreenTest {
//
//    @get:Rule(order = 0)
//    val hiltRule = HiltAndroidRule(this)
//
//    @get:Rule(order = 1)
//    val composeRule = createAndroidComposeRule<MainActivity>()
//
//    @Inject
//    lateinit var navigator: Navigator
//
//    @Before
//    fun setUp() {
//        hiltRule.inject()
//        composeRule.clearAndSetContent {
//            FitnessAppV2Theme {
//                NavHostGraph(
//                    navigator = navigator,
//                    startDestination = Screen.SearchScreen.route + "?mealName={mealName}"
//                )
//            }
//        }
//    }
//
//    @Test
//    fun onSearchTextEntered_TextVisible(){
//        val text = "abc"
//        val textField = composeRule.onNodeWithTag(composeRule.activity.getString(R.string.SEARCH_TEXT_FIELD))
//        textField.performTextClearance()
//        textField.performTextInput(text)
//        textField.assert(hasText(text))
//    }
//
//    @Test
//    fun onSearchButtonClicked_ProductNameVisible(){
//        val banana = "banana"
//        val textField = composeRule.onNodeWithTag(composeRule.activity.getString(R.string.SEARCH_TEXT_FIELD))
//        val searchButton = composeRule.onNodeWithTag(composeRule.activity.getString(R.string.SEARCH_BUTTON))
//        textField.performTextClearance()
//        textField.performTextInput(banana)
//        textField.assert(hasText(banana))
//        searchButton.performClick()
//        textField.performTextClearance()
//        composeRule.onNodeWithText(banana).assertIsDisplayed()
//    }
//
//    @Test
//    fun onScannerButtonClicked_ScannerVisible(){
//        val scannerButton = composeRule.onNodeWithTag(composeRule.activity.getString(R.string.scan))
//        scannerButton.performClick()
//        composeRule.onNodeWithTag(composeRule.activity.getString(R.string.scanner)).assertIsDisplayed()
//    }
//}