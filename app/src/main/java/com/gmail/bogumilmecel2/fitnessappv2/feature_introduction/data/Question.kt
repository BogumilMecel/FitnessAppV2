package com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.data

data class Question(
    val title:String,
    val unit:String = "",
    val tiles:List<String> = emptyList()
)
