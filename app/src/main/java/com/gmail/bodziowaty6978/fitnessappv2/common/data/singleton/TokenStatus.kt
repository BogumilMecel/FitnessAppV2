package com.gmail.bodziowaty6978.fitnessappv2.common.data.singleton

object TokenStatus {
    var isTokenPresent:Boolean? = null
        private set

    fun changeTokenStatus(status:Boolean){
        isTokenPresent = status
    }
}