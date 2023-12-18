package com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.github.tehras.charts.piechart.PieChart
import com.github.tehras.charts.piechart.PieChartData
import com.github.tehras.charts.piechart.animation.simpleChartAnimation
import com.github.tehras.charts.piechart.renderer.SimpleSliceDrawer

@Composable
fun PieChartWithMiddleText(
    modifier: Modifier = Modifier,
    pieChartData: PieChartData,
    middleText: String
) {
    Box(
        modifier = modifier.clip(CircleShape)
    ) {
        PieChart(
            pieChartData = pieChartData,
            animation = simpleChartAnimation(),
            sliceDrawer = SimpleSliceDrawer(
                sliceThickness = 20f
            ),
            modifier = Modifier.matchParentSize()
        )

        Box(
            modifier = Modifier
                .padding(25.dp)
                .align(Alignment.Center)
                .clip(CircleShape)
                .matchParentSize()
        ) {
            Text(
                text = middleText,
                style = MaterialTheme.typography.body2.copy(
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}