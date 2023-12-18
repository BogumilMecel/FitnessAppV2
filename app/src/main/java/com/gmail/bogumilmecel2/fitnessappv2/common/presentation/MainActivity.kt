package com.gmail.bogumilmecel2.fitnessappv2.common.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.navigation.NavHostGraph
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitnessAppTheme {
                NavHostGraph(navController = rememberNavController())
            }
        }
    }
}

