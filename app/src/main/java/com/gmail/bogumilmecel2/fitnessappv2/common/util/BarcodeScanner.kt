package com.gmail.bogumilmecel2.fitnessappv2.common.util

interface BarcodeScanner {
    suspend fun startScan(onBarcodeScanned: (String?) -> Unit)
}