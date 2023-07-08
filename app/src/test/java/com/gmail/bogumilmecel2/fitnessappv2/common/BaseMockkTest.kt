package com.gmail.bogumilmecel2.fitnessappv2.common

import com.gmail.bogumilmecel2.fitnessappv2.common.util.CustomDateUtils
import io.mockk.every
import io.mockk.mockkObject

open class BaseMockkTest {

    fun mockDateString(value: String) {
        mockkObject(CustomDateUtils)
        every { CustomDateUtils.getCurrentDateString() } returns value
    }
}