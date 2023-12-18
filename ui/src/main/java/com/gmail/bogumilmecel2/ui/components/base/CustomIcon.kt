package com.gmail.bogumilmecel2.ui.components.base

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.ui.theme.FitnessAppColors

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
        tint = FitnessAppColors.ContentWhite()
    )
}

sealed class CustomIconStyle(
    val imageVector: ImageVector,
    val contentDescription: String
) {
    object HeartIcon: CustomIconStyle(
        imageVector = Icons.Default.FavoriteBorder,
        contentDescription = "icon favorite"
    )
    object HeartIconFilled: CustomIconStyle(
        imageVector = Icons.Filled.Favorite,
        contentDescription = "icon favorite filled"
    )
    object BackIcon: CustomIconStyle(
        imageVector = Icons.Default.ArrowBack,
        contentDescription = "icon back"
    )
    object SearchIcon: CustomIconStyle(
        imageVector = Icons.Default.Search,
        contentDescription = "icon search"
    )
}

data class IconParams(
    val iconStyle: CustomIconStyle,
    val onClick: (() -> Unit)? = null
)