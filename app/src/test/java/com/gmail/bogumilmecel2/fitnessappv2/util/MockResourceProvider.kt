package com.gmail.bogumilmecel2.fitnessappv2.util

import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider

class MockResourceProvider: ResourceProvider {
    override fun getString(stringResId: Int, vararg args: Any) = getResourceFieldName(R.string::class.java, stringResId)

    override fun getPluralString(pluralResId: Int, quantity: Int): String {
        TODO("Not yet implemented")
    }

    private fun getResourceFieldName(resourceClass: Class<*>, resourceId: Int) =
        resourceClass.fields.find { it.getInt(null) == resourceId }?.name.orEmpty()
}