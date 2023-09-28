package com.gmail.bogumilmecel2.ui.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

data class FitnessAppShapes(
    val Small: CornerBasedShape = RoundedCornerShape(size = 8.dp),
    val Medium: CornerBasedShape = RoundedCornerShape(size = 12.dp),
    val Large: CornerBasedShape = RoundedCornerShape(size = 16.dp),
)