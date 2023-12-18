package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import androidx.compose.runtime.Composable
import com.gmail.bogumilmecel2.fitnessappv2.common.BaseMockkTest
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants.Diary.GRAMS
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants.Diary.MILLILITERS
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants.Diary.SERVING
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants.Diary.SERVINGS
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants.Diary.mockMeasurementUnitString
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants.Diary.mockServingsStrings
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.MeasurementUnit
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.DiaryEntryType
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntry
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class GenerateDiaryItemDialogTitleUseCaseTest : BaseMockkTest() {

    private val generateDiaryItemDialogTitleUseCase =
        GenerateDiaryItemDialogTitleUseCase(resourceProvider = resourceProvider)

    @Test
    fun `Check if dialog title is correct from recipe diary entry witch 1 serving`() {
        val servings = MockConstants.Diary.CORRECT_RECIPE_SERVINGS_1.toValidIntOrThrow()
        mockServingsStrings(
            quantity = servings,
            resourceProvider = resourceProvider
        )
        val title = generateDiaryItemDialogTitleUseCase(
            diaryItem = RecipeDiaryEntry(
                recipeName = MockConstants.Diary.RECIPE_NAME_1,
                servings = servings
            )
        )
        assertEquals(
            actual = title,
            expected = "${MockConstants.Diary.RECIPE_NAME_1} ($servings $SERVING)"
        )
    }

    @Test
    fun `Check if dialog title is correct from recipe diary entry with more than 1 serving`() {
        val servings = MockConstants.Diary.CORRECT_RECIPE_SERVINGS_2.toValidIntOrThrow()
        mockServingsStrings(
            quantity = servings,
            resourceProvider = resourceProvider
        )
        val title = generateDiaryItemDialogTitleUseCase(
            diaryItem = RecipeDiaryEntry(
                recipeName = MockConstants.Diary.RECIPE_NAME_2,
                servings = servings
            )
        )
        assertEquals(
            actual = title,
            expected = "${MockConstants.Diary.RECIPE_NAME_2} ($servings $SERVINGS)"
        )
    }

    @Test
    fun `Check if dialog title is correct from product diary entry with grams measurement unit`() {
        val weight = MockConstants.Diary.CORRECT_PRODUCT_DIARY_ENTRY_WEIGHT_1.toValidIntOrThrow()
        mockMeasurementUnitString(resourceProvider)
        val title = generateDiaryItemDialogTitleUseCase(
            diaryItem = ProductDiaryEntry(
                productName = MockConstants.Diary.PRODUCT_NAME_1,
                weight = weight,
                productMeasurementUnit = MeasurementUnit.GRAMS
            )
        )
        assertEquals(
            actual = title,
            expected = "${MockConstants.Diary.PRODUCT_NAME_1} (${weight}$GRAMS)"
        )
    }

    @Test
    fun `Check if dialog title is correct from product diary entry with grams milliliters unit`() {
        val weight = MockConstants.Diary.CORRECT_PRODUCT_DIARY_ENTRY_WEIGHT_2.toValidIntOrThrow()
        mockMeasurementUnitString(resourceProvider)
        val title = generateDiaryItemDialogTitleUseCase(
            diaryItem = ProductDiaryEntry(
                productName = MockConstants.Diary.PRODUCT_NAME_2,
                weight = weight,
                productMeasurementUnit = MeasurementUnit.MILLILITERS
            )
        )
        assertEquals(
            actual = title,
            expected = "${MockConstants.Diary.PRODUCT_NAME_2} (${weight}$MILLILITERS)"
        )
    }

    @Test
    fun `Check if exception is thrown when not using predefined diary items`() {
        assertThrows<Exception> {
            generateDiaryItemDialogTitleUseCase(
                diaryItem = object : DiaryItem {
                    override val id: String = ""
                    override val nutritionValues: NutritionValues = NutritionValues()
                    override val utcTimestamp: Long = 0
                    override val userId: String = ""
                    override val date: String = ""
                    override val mealName: MealName = MealName.BREAKFAST

                    @Composable
                    override fun getDisplayValue() = ""
                    override fun getDiaryEntryType() = DiaryEntryType.PRODUCT
                }
            )
        }
    }
}