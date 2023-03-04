package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_recipe.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.DefaultCardBackground

@Composable
fun RecipeImageSection(
    modifier: Modifier
) {
    DefaultCardBackground(modifier = modifier) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Outlined.CameraAlt,
                contentDescription = "camera",
                Modifier.size(32.dp)
            )


            Spacer(modifier = Modifier.height(6.dp))

            OutlinedButton(
                onClick = {
                    /*TODO*/
                },
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = Color.Transparent,
                ),
                border = BorderStroke(1.dp, color = MaterialTheme.colors.primary),
                shape = RoundedCornerShape(15)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.add),
                        tint = MaterialTheme.colors.primary
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = stringResource(id = R.string.add_image).uppercase(),
                        style = MaterialTheme.typography.button.copy(
                            color = MaterialTheme.colors.primary
                        )
                    )
                }
            }
        }
    }

}