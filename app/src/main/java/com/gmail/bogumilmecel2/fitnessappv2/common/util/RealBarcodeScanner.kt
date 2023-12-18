package com.gmail.bogumilmecel2.fitnessappv2.common.util

import android.content.Context
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import kotlinx.coroutines.tasks.await

class RealBarcodeScanner(context: Context): BarcodeScanner {
    private val options = GmsBarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
        .build()

    private val scanner = GmsBarcodeScanning.getClient(
        context,
        options,
    )

    override suspend fun startScan(onBarcodeScanned: (String?) -> Unit) {
        try {
            val result = scanner.startScan().await()
            onBarcodeScanned(result.rawValue)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}