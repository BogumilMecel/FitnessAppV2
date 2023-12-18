package com.gmail.bogumilmecel2.fitnessappv2.common

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.CustomDateUtils
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject

open class BaseMockkTest {

    val resourceProvider = mockk<ResourceProvider>()
    val cachedValuesProvider = mockk<CachedValuesProvider>()

    fun mockDateString(value: String) {
        mockkObject(CustomDateUtils)
        every { CustomDateUtils.getCurrentDateString() } returns value
    }

    fun mockString(resId: Int, value: String) {
        every { resourceProvider.getString(resId) } returns value
    }
}