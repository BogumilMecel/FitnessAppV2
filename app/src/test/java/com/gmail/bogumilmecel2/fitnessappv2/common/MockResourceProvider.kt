package com.gmail.bogumilmecel2.fitnessappv2.common

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider

class MockResourceProvider: ResourceProvider {

    private val strings: MutableMap<Int, String> = mutableMapOf()

    override fun getString(stringResId: Int, vararg args: Any) = strings[stringResId] ?: ""

    override fun getPluralString(pluralResId: Int, quantity: Int) = ""

    override fun mockString(stringResId: Int, value: String) {
        strings[stringResId] = value
    }
}