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
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.gmail.bogumilmecel2.ui.R
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun CustomIcon(
    modifier: Modifier = Modifier,
    icon: Icon,
    iconColor: Color = FitnessAppTheme.colors.Primary
) {
    when(icon) {
        is IconPainter -> {
            CustomIcon(
                iconPainter = icon,
                iconColor = iconColor,
                modifier = modifier
            )
        }

        is IconVector -> {
            CustomIcon(
                iconVector = icon,
                iconColor = iconColor,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun CustomIcon(
    modifier: Modifier = Modifier,
    iconVector: IconVector,
    iconColor: Color = FitnessAppTheme.colors.Primary
) = with(iconVector) {
    Icon(
        modifier = modifier,
        imageVector = imageVector,
        contentDescription = stringResource(id = iconVector.contentDescriptionId),
        tint = iconColor
    )
}

@Composable
private fun CustomIcon(
    modifier: Modifier = Modifier,
    iconPainter: IconPainter,
    iconColor: Color = FitnessAppTheme.colors.Primary
) = with(iconPainter) {
    Icon(
        modifier = modifier,
        painter = imagePainter,
        contentDescription = stringResource(id = contentDescriptionId),
        tint = iconColor
    )
}

interface Icon {
    val contentDescriptionId: Int
}

data class IconPainter(
    val imagePainter: Painter,
    override val contentDescriptionId: Int
): Icon

sealed class IconVector(
    val imageVector: ImageVector,
    override val contentDescriptionId: Int
): Icon {
    companion object {
        @Composable
        fun barcode() = IconPainter(
            imagePainter = painterResource(id = R.drawable.barcode_scan),
            contentDescriptionId = R.string.barcode
        )
    }

    object Heart: IconVector(
        imageVector = Icons.Default.FavoriteBorder,
        contentDescriptionId = R.string.favorite
    )
    object HeartFilled: IconVector(
        imageVector = Icons.Filled.Favorite,
        contentDescriptionId = R.string.favorite_filled
    )
    object Back: IconVector(
        imageVector = Icons.Default.ArrowBack,
        contentDescriptionId = R.string.back
    )
    object Search: IconVector(
        imageVector = Icons.Default.Search,
        contentDescriptionId = R.string.search
    )
    object Cancel: IconVector(
        imageVector = Icons.Default.Search,
        contentDescriptionId = R.string.cancel
    )
    object Add: IconVector(
        imageVector = Icons.Default.Add,
        contentDescriptionId = R.string.add
    )
    object Logout: IconVector(
        imageVector = Icons.Default.Logout,
        contentDescriptionId = R.string.log_out
    )
    object Save: IconVector(
        imageVector = Icons.Default.Save,
        contentDescriptionId = R.string.save
    )
    object Edit: IconVector(
        imageVector = Icons.Default.Edit,
        contentDescriptionId = R.string.edit
    )
    object Email: IconVector(
        imageVector = Icons.Default.Email,
        contentDescriptionId = R.string.email
    )
    object Password: IconVector(
        imageVector = Icons.Default.Password,
        contentDescriptionId = R.string.password
    )
    object Login: IconVector(
        imageVector = Icons.Default.Login,
        contentDescriptionId = R.string.login
    )
    object Account: IconVector(
        imageVector = Icons.Default.AccountCircle,
        contentDescriptionId = R.string.account
    )
}