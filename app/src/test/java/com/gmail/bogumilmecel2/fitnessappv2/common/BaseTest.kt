package com.gmail.bogumilmecel2.fitnessappv2.common

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.ResourceProvider
import org.junit.After
import org.junit.Before
import org.mockito.MockitoAnnotations

open class BaseTest {

    private lateinit var closable: AutoCloseable
    lateinit var resourceProvider: ResourceProvider

    @Before
    fun openMocks() {
        closable = MockitoAnnotations.openMocks(this)
        resourceProvider = MockResourceProvider()
    }

    @After
    fun closeMocks() {
        closable.close()
    }
}