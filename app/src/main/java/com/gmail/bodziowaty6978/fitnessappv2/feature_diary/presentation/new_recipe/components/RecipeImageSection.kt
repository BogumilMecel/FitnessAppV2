package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_recipe.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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

@Composable
fun RecipeImageSection(
    modifier: Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(15),
        elevation = 2.dp
    ) {
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