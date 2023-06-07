package com.gmail.bogumilmecel2.ui.components.base

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.gmail.bogumilmecel2.ui.R
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun CustomIcon(
    modifier: Modifier = Modifier,
    iconStyle: CustomIconStyle,
    iconColor: Color = FitnessAppTheme.colors.Primary
) = with(iconStyle) {
    androidx.compose.material.Icon(
        modifier = modifier,
        imageVector = imageVector,
        contentDescription = stringResource(id = iconStyle.contentDescriptionId),
        tint = iconColor
    )
}

sealed class CustomIconStyle(
    val imageVector: ImageVector,
    val contentDescriptionId: Int
) {
    object Heart: CustomIconStyle(
        imageVector = Icons.Default.FavoriteBorder,
        contentDescriptionId = R.string.favorite
    )
    object HeartFilled: CustomIconStyle(
        imageVector = Icons.Filled.Favorite,
        contentDescriptionId = R.string.favorite_filled
    )
    object Back: CustomIconStyle(
        imageVector = Icons.Default.ArrowBack,
        contentDescriptionId = R.string.back
    )
    object Search: CustomIconStyle(
        imageVector = Icons.Default.Search,
        contentDescriptionId = R.string.search
    )
    object Cancel: CustomIconStyle(
        imageVector = Icons.Default.Search,
        contentDescriptionId = R.string.cancel
    )
    object Add: CustomIconStyle(
        imageVector = Icons.Default.Add,
        contentDescriptionId = R.string.add
    )
    object Logout: CustomIconStyle(
        imageVector = Icons.Default.Logout,
        contentDescriptionId = R.string.log_out
    )
    object Save: CustomIconStyle(
        imageVector = Icons.Default.Save,
        contentDescriptionId = R.string.save
    )
    object Edit: CustomIconStyle(
        imageVector = Icons.Default.Edit,
        contentDescriptionId = R.string.edit
    )
    object Email: CustomIconStyle(
        imageVector = Icons.Default.Email,
        contentDescriptionId = R.string.email
    )
    object Password: CustomIconStyle(
        imageVector = Icons.Default.Password,
        contentDescriptionId = R.string.password
    )
    object Login: CustomIconStyle(
        imageVector = Icons.Default.Login,
        contentDescriptionId = R.string.login
    )
    object Account: CustomIconStyle(
        imageVector = Icons.Default.AccountCircle,
        contentDescriptionId = R.string.account
    )
}