package com.gmail.bogumilmecel2.fitnessappv2.common.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.navigation.Navigator
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.navigation.NavHostGraph
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BottomSheetContentProvider
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var bottomSheetContentProvider: BottomSheetContentProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitnessAppTheme {
                NavHostGraph(
                    navigator = navigator,
                    bottomSheetContentProvider = bottomSheetContentProvider
                )
            }
        }
    }
}

