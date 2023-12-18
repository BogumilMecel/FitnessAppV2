package com.gmail.bogumilmecel2.ui.components.base

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun CustomIcon(
    iconStyle: CustomIconStyle,
    iconPosition: Position = Position.OnBackground
) = with(iconStyle) {
    androidx.compose.material.Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        tint = iconPosition.getContentColor()
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
    object Logout: CustomIconStyle(
        imageVector = Icons.Default.Logout,
        contentDescription = "log out"
    )
    object Save: CustomIconStyle(
        imageVector = Icons.Default.Save,
        contentDescription = "save"
    )
    object Edit: CustomIconStyle(
        imageVector = Icons.Default.Edit,
        contentDescription = "edit"
    )
}

enum class Position {
    OnPrimary, OnBackground;

    @Composable
    fun getContentColor() = when(this) {
        OnPrimary -> FitnessAppTheme.colors.Black
        OnBackground -> FitnessAppTheme.colors.ContentPrimary
    }
}