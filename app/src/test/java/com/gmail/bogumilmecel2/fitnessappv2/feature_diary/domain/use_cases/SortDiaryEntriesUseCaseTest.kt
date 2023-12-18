package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary.SortDiaryEntriesUseCase
import org.junit.Before
import org.junit.Test
import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class SortDiaryEntriesUseCaseTest {

    private val diaryEntries: MutableList<DiaryItem> = mutableListOf()
    private val sortDiaryEntriesUseCase = SortDiaryEntriesUseCase()

    @Before
    fun setUp() {
        (0..10).map {
            diaryEntries.add(
                ProductDiaryEntry(
                    id = "$it",
                    timestamp = Random(it).nextLong()
                )
            )
        }
        (20..30).map {
            diaryEntries.add(
                RecipeDiaryEntry(
                    id = "$it",
                    timestamp = Random(it).nextLong()
                )
            )
        }
    }

    @Test
    fun `if called, return map of the same size as entry data size`() {
        val result = sortDiaryEntriesUseCase(diaryEntries = diaryEntries)

        assertEquals(
            expected = diaryEntries.size,
            actual = result.values.sumOf { it.size }
        )
    }

    @Test
    fun `if called, return map of diary entries all sorted descending by timestamp`() {
        val result = sortDiaryEntriesUseCase(diaryEntries = diaryEntries)

        result.values.forEach { diaryItems ->
            assertTrue(
                actual = diaryItems.asSequence().zipWithNext { a, b -> a.timestamp >= b.timestamp }.all { it }
            )
        }
    }
}
