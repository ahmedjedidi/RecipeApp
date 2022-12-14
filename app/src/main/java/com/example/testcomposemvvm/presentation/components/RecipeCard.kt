package com.example.testcomposemvvm.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.testcomposemvvm.R
import com.example.testcomposemvvm.domain.model.Recipe

@Composable
fun RecipeCard(
    recipe: Recipe,
    onClick : () -> Unit
    ) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(top = 6.dp, bottom = 6.dp, start = 3.dp, end = 3.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
            elevation = 8.dp
    ) {
        Column() {
            recipe.featuredImage?.let { url ->
                Image(
                    painter = rememberAsyncImagePainter(url),
                        contentDescription = null,
                        modifier = Modifier
                            .height(225.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
            }
            recipe.title?.let { title ->
                Row( modifier = Modifier
                    .padding(top=12.dp, bottom=12.dp, start = 8.dp, end=8.dp)
                    .fillMaxWidth()

                ) {
                Text(text = title, modifier= Modifier
                    .fillMaxWidth(0.85f)
                    .wrapContentWidth(Alignment.Start),
                    style = MaterialTheme.typography.h5
                )
                Text(text = recipe.rating.toString(), modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.End)
                    .align(Alignment.CenterVertically),
                    style = MaterialTheme.typography.h6
                )
                }
            }

        }

    }
}