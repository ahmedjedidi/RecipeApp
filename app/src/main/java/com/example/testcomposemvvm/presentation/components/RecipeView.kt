package com.example.testcomposemvvm.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.testcomposemvvm.domain.model.Recipe

@Composable
fun RecipeView(
    recipe:Recipe
){
   Column(
       modifier= Modifier
           .fillMaxWidth()
           .verticalScroll(rememberScrollState())
   ) {
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


       Column(
           modifier = Modifier
               .fillMaxWidth()
               .padding(8.dp)
       ) {
           recipe.title?.let { title ->
               Row(modifier = Modifier
                   .fillMaxWidth()
                   .padding(bottom = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                   Text(text = title,style= MaterialTheme.typography.h3)
                   Text(text = recipe.rating.toString(), style = MaterialTheme.typography.h5,
                   modifier=Modifier.align(Alignment.CenterVertically))
               }

           }
           recipe.publisher?.let { publisher ->
           val updated = recipe.dateUpdated
           Text(text = if(updated!=null){
               "Updated ${updated} by ${publisher}"
           }
           else{
               "by ${publisher}"
           },
           modifier= Modifier
               .fillMaxWidth()
               .padding(bottom = 8.dp),
           style = MaterialTheme.typography.caption)

           }
           for( ingredient in recipe.ingredients){
               Text(text = ingredient, modifier = Modifier
                   .padding(8.dp)
                   .fillMaxWidth(),
                   style= MaterialTheme.typography.body1
               )
           }

       }
   }

}