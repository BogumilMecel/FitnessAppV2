package com.gmail.bodziowaty6978.fitnessappv2.common.data.room

import androidx.room.TypeConverter
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Price
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product
import com.google.gson.Gson

object Converters {

    private val gson = Gson()

    @TypeConverter
    @JvmStatic
    fun nutritionValuesToString(nutritionValues: NutritionValues):String{
        return gson.toJson(nutritionValues) ?: ""
    }

    @TypeConverter
    @JvmStatic
    fun stringToNutritionValues(data: String?): NutritionValues {
        return data?.let {
            gson.fromJson(data, NutritionValues::class.java)
        } ?: NutritionValues()
    }

    @TypeConverter
    @JvmStatic
    fun productToString(product: Product):String{
        return gson.toJson(product) ?: ""
    }

    @TypeConverter
    @JvmStatic
    fun stringToProduct(data: String?): Product {
        return data?.let {
            gson.fromJson(data, Product::class.java)
        } ?: Product()
    }

    @TypeConverter
    @JvmStatic
    fun priceToString(price: Price):String{
        return gson.toJson(price) ?: ""
    }

    @TypeConverter
    @JvmStatic
    fun stringToPrice(data: String?):Price{
        return data?.let {
            gson.fromJson(data, Price::class.java)
        } ?: Price()
    }
}