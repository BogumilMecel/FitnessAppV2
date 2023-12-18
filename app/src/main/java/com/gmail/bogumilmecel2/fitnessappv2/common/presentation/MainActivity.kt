package com.gmail.bogumilmecel2.fitnessappv2.common.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.navigation.NavHostGraph
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme
import com.google.android.gms.common.moduleinstall.ModuleInstall
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val moduleInstall = ModuleInstall.getClient(this)
        val moduleInstallRequest = ModuleInstallRequest.newBuilder()
            .addApi(GmsBarcodeScanning.getClient(this))
            .build()
        moduleInstall.installModules(moduleInstallRequest)

        setContent {
            FitnessAppTheme {
                NavHostGraph(navController = rememberNavController())
            }
        }
    }
}

