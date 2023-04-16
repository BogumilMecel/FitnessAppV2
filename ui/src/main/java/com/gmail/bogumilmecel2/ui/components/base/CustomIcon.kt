package com.gmail.bogumilmecel2.ui.components.base

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun CustomIcon(
    iconParams: IconParams
) = with(iconParams) {
    androidx.compose.material.Icon(
        imageVector = iconStyle.imageVector,
        contentDescription = iconStyle.contentDescription,
        modifier = Modifier
            .clip(CircleShape)
            .then(other = onClick?.let { onClick -> Modifier.clickable(onClick = onClick,) } ?: Modifier)
            .padding(12.dp),
        tint = FitnessAppTheme.colors.ContentPrimary
    )
}

sealed class CustomIconStyle(
    val imageVector: ImageVector,
    val contentDescription: String
) {
    object Heart: CustomIconStyle(
        imageVector = Icons.Default.FavoriteBorder,
        contentDescription = "favorite"
    )
    object HeartFilled: CustomIconStyle(
        imageVector = Icons.Filled.Favorite,
        contentDescription = "favorite filled"
    )
    object Back: CustomIconStyle(
        imageVector = Icons.Default.ArrowBack,
        contentDescription = "back"
    )
    object Search: CustomIconStyle(
        imageVector = Icons.Default.Search,
        contentDescription = "search"
    )
    object Cancel: CustomIconStyle(
        imageVector = Icons.Default.Search,
        contentDescription = "cancel"
    )
    object Add: CustomIconStyle(
        imageVector = Icons.Default.Add,
        contentDescription = "add"
    )
}

data class IconParams(
    val iconStyle: CustomIconStyle,
    val onClick: (() -> Unit)? = null
)