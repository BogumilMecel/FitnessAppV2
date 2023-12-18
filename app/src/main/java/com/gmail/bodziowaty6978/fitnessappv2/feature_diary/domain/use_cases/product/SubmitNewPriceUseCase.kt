package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.product

import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.Currency
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.common.util.extensions.toValidDouble
import com.gmail.bodziowaty6978.fitnessappv2.common.util.extensions.toValidInt
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Price
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.product.NewPriceRequest
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class SubmitNewPriceUseCase(
    private val diaryRepository: DiaryRepository,
    private val resourceProvider: ResourceProvider
) {
    suspend operator fun invoke(
        paidHowMuch: String,
        paidForWeight: String,
        productId: String
    ): Resource<Price> {
        val paidHowMuchValue = paidHowMuch.toValidDouble()
        val paidForWeightValue = paidForWeight.toValidInt()
        return if(paidHowMuchValue == null || paidForWeightValue == null) {
          Resource.Error(uiText = resourceProvider.getString(R.string.please_make_sure_you_have_entered_correct_values_for_new_price))
        } else if (paidHowMuchValue <= 0 || paidForWeightValue <= 0) {
            Resource.Error(uiText = resourceProvider.getString(R.string.please_make_sure_you_have_entered_correct_values_for_new_price))
        } else {
            diaryRepository.addNewPrice(
                newPriceRequest = NewPriceRequest(
                    paidForWeight = paidForWeightValue,
                    paidHowMuch = paidHowMuchValue,
                    productId = productId,
                    currency = Currency.PLN
                )
            )
        }
    }
}