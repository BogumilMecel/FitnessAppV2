package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.search

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.ProductWithId

data class SearchState(
    val isLoading:Boolean = false,
    val items:List<ProductWithId> = emptyList(),
    val errorMessage:String? = null,
    val lastErrorMessage:String? = null,
    val searchBarText:String = "",
    val isScannerVisible:Boolean = false,
    val hasPermissionDialogBeenShowed:Boolean = false,
    val barcode:String? = null
)
