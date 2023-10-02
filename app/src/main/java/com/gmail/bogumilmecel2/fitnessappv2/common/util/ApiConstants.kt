package com.gmail.bogumilmecel2.fitnessappv2.common.util

object ApiConstants {
    object Headers {
        const val COUNTRY = "country"
        const val CURRENCY = "currency"
        const val TIMEZONE = "time_zone"
        const val DEVICE_ID = "device_id"
    }

    object Query {
        const val SEARCH_TEXT = "search_text"
        const val PAGE = "page"
    }

    object HttpStatusCodes {
        const val NOT_FOUND = 404
    }

    const val DEFAULT_PAGE_SIZE = 20
    const val DEFAULT_OFFLINE_PAGE_SIZE = 50
}