package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.common.BaseTest
import com.gmail.bogumilmecel2.fitnessappv2.common.util.CustomDateUtils
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.OfflineDiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary.GetOfflineDiaryEntriesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertTrue

internal class GetOfflineDiaryEntriesUseCaseTest: BaseTest() {

    private val offlineDiaryRepository = mockk<OfflineDiaryRepository>()
    private val getOfflineDiaryEntriesUseCase = GetOfflineDiaryEntriesUseCase(
        offlineDiaryRepository = offlineDiaryRepository
    )

    @Test
    fun `Check if all data from repository is returned`() =
        runTest {
            val productDiaryEntries = buildList {
                repeat(5) {
                    add(ProductDiaryEntry(id = "$it"))
                }
            }

            val recipeDiaryEntries = buildList {
                repeat(5) {
                    add(RecipeDiaryEntry(id = "$it"))
                }
            }

            coEvery { offlineDiaryRepository.getProductDiaryEntries(date = any()) } returns Resource.Success(productDiaryEntries)
            coEvery { offlineDiaryRepository.getRecipeDiaryEntries(date = any()) } returns Resource.Success(recipeDiaryEntries)

            val diaryItems = buildList {
                addAll(productDiaryEntries)
                addAll(recipeDiaryEntries)
            }

            getOfflineDiaryEntriesUseCase(date = CustomDateUtils.getDate()).forEach {
                assertTrue { diaryItems.contains(it) }
            }
        }
}