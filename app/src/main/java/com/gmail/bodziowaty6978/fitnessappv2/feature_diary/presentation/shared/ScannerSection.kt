package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.shared

import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.barcode.BarcodeAnalyzer
import com.google.mlkit.vision.barcode.common.Barcode

@Composable
fun ScannerSection(
    onCodeScanned: (Barcode) -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }

    AndroidView(
        factory = { viewContext ->
            cameraProviderFuture.get().unbindAll()
            val previewView = PreviewView(viewContext)
            val preview = Preview.Builder().build()
            val selector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()
            preview.setSurfaceProvider(previewView.surfaceProvider)


            val imageAnalysis = ImageAnalysis.Builder()
                .setTargetResolution(
                    Size(
                        previewView.width,
                        previewView.height
                    )
                )
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            imageAnalysis.setAnalyzer(
                ContextCompat.getMainExecutor(viewContext),
                BarcodeAnalyzer { result ->
                    cameraProviderFuture.get().unbindAll()
                    onCodeScanned(result[0])
                }
            )

            try {
                cameraProviderFuture.get().bindToLifecycle(
                    lifecycleOwner,
                    selector,
                    preview,
                    imageAnalysis
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
            previewView
        },
        modifier = Modifier
            .fillMaxSize()
    )


}