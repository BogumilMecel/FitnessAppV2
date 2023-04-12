package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product

import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case.GetUserCurrencyUseCase
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.common.util.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.toValidDouble
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.toValidInt
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.ProductPrice
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.product.NewPriceRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class SubmitNewPriceUseCase(
    private val diaryRepository: DiaryRepository,
    private val resourceProvider: ResourceProvider,
    private val getUserCurrencyUseCase: GetUserCurrencyUseCase
) {
    suspend operator fun invoke(
        paidHowMuch: String,
        paidForWeight: String,
        productId: String
    ): Resource<ProductPrice> {
        val paidHowMuchValue = paidHowMuch.toValidDouble()
        val paidForWeightValue = paidForWeight.toValidInt()
        return if(paidHowMuchValue == null || paidForWeightValue == null) {
          Resource.Error(uiText = resourceProvider.getString(R.string.please_make_sure_you_have_entered_correct_values_for_new_price))
        } else if (paidHowMuchValue <= 0 || paidForWeightValue <= 0) {
            Resource.Error(uiText = resourceProvider.getString(R.string.please_make_sure_you_have_entered_correct_values_for_new_price))
        } else {
            diaryRepository.submitNewPrice(
                newPriceRequest = NewPriceRequest(
                    paidForWeight = paidForWeightValue,
                    paidHowMuch = paidHowMuchValue,
                ),
                productId = productId,
                currency = getUserCurrencyUseCase()
            )
        }
    }
}