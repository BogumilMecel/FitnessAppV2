package com.gmail.bogumilmecel2.fitnessappv2.common.util

object Constants {
    object Headers {
        const val COUNTRY = "country"
        const val CURRENCY = "currency"
        const val TIMEZONE = "time_zone"
        const val DEVICE_ID = "device_id"
    }

    object Query {
        const val SEARCH_TEXT = "search_text"
        const val PAGE = "page"
        const val LATEST_DATE_TIME = "latest_date_time"
    }

    object HttpStatusCodes {
        const val NOT_FOUND = 404
    }

    const val DEFAULT_PAGE_SIZE = 20L
    const val DEFAULT_OFFLINE_PAGE_SIZE = 50L

    const val WEIGHT_PICKER_MIN_VALUE = 30.0
    const val WEIGHT_PICKER_MAX_VALUE = 250.0
    const val WEIGHT_PICKER_DEFAULT_VALUE = 80.0
}