package com.gmail.bogumilmecel2.fitnessappv2.common

import org.junit.After
import org.junit.Before
import org.mockito.MockitoAnnotations

open class BaseTest {

    private lateinit var closable: AutoCloseable

    @Before
    fun openMocks() {
        closable = MockitoAnnotations.openMocks(this)
    }

    @After
    fun closeMocks() {
        closable.close()
    }
}