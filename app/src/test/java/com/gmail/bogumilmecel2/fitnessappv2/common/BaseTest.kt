package com.gmail.bogumilmecel2.fitnessappv2.common

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.CustomDateUtils
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.toValidInt
import com.gmail.bogumilmecel2.fitnessappv2.util.MockResourceProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.spyk
import kotlin.test.assertEquals
import kotlin.test.assertIs

open class BaseTest {

    val mockedDate = "2023-12-12"
    val mockedDate2 = "2022-12-12"

    val resourceProvider: ResourceProvider = spyk<MockResourceProvider>()
    val cachedValuesProvider = mockk<CachedValuesProvider>()

    fun mockDateString(value: String) {
        mockkObject(CustomDateUtils)
        every { CustomDateUtils.getCurrentDateString() } returns value
    }

    fun String.toValidIntOrThrow() = this.toValidInt() ?: throw Exception()

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