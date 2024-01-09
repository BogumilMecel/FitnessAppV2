package com.gmail.bogumilmecel2.fitnessappv2.common

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.OfflineMode
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.CustomDateUtils
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.util.MockResourceProvider
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.spyk
import kotlinx.datetime.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertIs

open class BaseTest {

    val resourceProvider: ResourceProvider = spyk<MockResourceProvider>()
    val cachedValuesProvider = mockk<CachedValuesProvider>()

    fun mockDate(date: LocalDate = MockConstants.getDate2021()) {
        mockkObject(CustomDateUtils)
        every { CustomDateUtils.getDate() } returns date
    }

    fun mockUserId(userId: String = MockConstants.USER_ID) {
        coEvery { cachedValuesProvider.getUserId() } returns userId
    }

    fun mockOfflineMode(online: Boolean = true) {
        coEvery { cachedValuesProvider.getOfflineMode() } returns if (online) OfflineMode.Online else OfflineMode.Offline
    }

    fun String.toValidIntOrThrow() = this.replace(
        oldValue = ",",
        newValue = ""
    ).replace(
        oldValue = ".",
        newValue = ""
    ).toIntOrNull() ?: throw Exception()

    fun <T> Resource<T>.assertIsError(text: String? = null) {
        assertIs<Resource.Error<T>>(this)
        text?.let {
            assertEquals(
                expected = text,
                actual = this.uiText
            )
        }
    }

    fun <T> Resource<T>.assertIsSuccess() {
        assertIs<Resource.Success<T>>(this)
    }
}