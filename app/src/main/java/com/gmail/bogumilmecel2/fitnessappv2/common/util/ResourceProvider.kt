package com.gmail.bogumilmecel2.fitnessappv2.common.util

import android.content.Context
import androidx.annotation.ArrayRes
import androidx.annotation.StringRes
import com.gmail.bogumilmecel2.fitnessappv2.R

class ResourceProvider(private val context: Context) {

    fun getString(@StringRes stringResId: Int): String {
        return context.getString(stringResId)
    }
    fun getUnknownErrorString():String{
        return context.getString(R.string.unknown_error)
    }
    fun getStringArray(@ArrayRes stringArrayId:Int):List<String>{
        return context.resources.getStringArray(stringArrayId).toList()
    }
}