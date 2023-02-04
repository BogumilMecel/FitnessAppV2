package com.gmail.bodziowaty6978.fitnessappv2.feature_account.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gmail.bodziowaty6978.fitnessappv2.R

@Composable
fun LogOutButton(
    modifier: Modifier,
    onClick:() -> Unit
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = MaterialTheme.colors.primary
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Text(
                text = stringResource(id = R.string.log_out).uppercase(),
                style = MaterialTheme.typography.button
            )

            Spacer(modifier = Modifier.width(24.dp))

            Icon(
                imageVector = Icons.Default.Logout,
                contentDescription = stringResource(id = R.string.log_out),
                tint = Color.Black
            )
        }
    }
}