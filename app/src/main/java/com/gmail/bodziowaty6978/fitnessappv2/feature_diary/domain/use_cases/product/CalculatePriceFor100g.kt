package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.product

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Price

class CalculatePriceFor100g {

    operator fun invoke(
        priceStringValue:String,
        priceStringForValue:String
    ):Price?{
        val priceValue = priceStringValue.toDoubleOrNull()
        val priceForValue = priceStringForValue.toDoubleOrNull()
        return if (priceValue!=null && priceForValue != null){
            val value = priceValue/priceForValue*100.0
            return Price(
                value = value,
                currency = "zł",
            )
        }else{
            null
        }
    }
}