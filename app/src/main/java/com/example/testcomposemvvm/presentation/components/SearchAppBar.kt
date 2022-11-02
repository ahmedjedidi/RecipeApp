package com.example.testcomposemvvm.presentation.components

import android.text.Layout
import android.util.Log
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.testcomposemvvm.presentation.ui.recipe_list.FoodCategory
import com.example.testcomposemvvm.presentation.ui.recipe_list.getAllCategory

@Composable
fun SearchAppBar(
    query:String,
    onQueryChanged: (String) -> Unit,
    newSearch: () ->Unit,
    focusManager:FocusManager,
    scrollPosition: Float,
    selectedCategory: FoodCategory?,
    onSelectCategoryChanged:(String) ->Unit,
    onChangedScrollPosition: (Float) ->Unit,
    onToogleTheme : () -> Unit
) {
    Surface(modifier = Modifier
        .fillMaxWidth(),
        color= MaterialTheme.colors.surface,
        elevation =8.dp
    ){
        Column() {
            Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
                TextField(modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(8.dp),
                    value = query, onValueChange = { newValue ->
                        onQueryChanged(newValue)
                    },
                    label = { Text(text = "Search") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = null)
                    },
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            newSearch()
                            focusManager.clearFocus()
                        }
                    ),
                    textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.surface,

                        )
                )
              IconButton(onClick = onToogleTheme ,modifier= Modifier
                  .align(Alignment.CenterVertically)
                  .padding(8.dp)){
                 Icon(Icons.Default.MoreVert, contentDescription = null)
              }
            }
            val scrollState = rememberScrollState()

            Row(modifier= Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState)
                .padding(start = 8.dp, bottom = 8.dp)

            ) {
                LaunchedEffect(true) {
                    //Scroll to ScrollPosition set when category is selected
                    scrollState.scrollTo(scrollPosition.toInt())
                }


                for(category in getAllCategory()){
                    FoodCategoryChip(category = category.value,
                        isSelected = category == selectedCategory,
                        onSelectedCategoryChanged = {
                            onSelectCategoryChanged(it)
                            //Change scrollPosition when Category is clicked
                            onChangedScrollPosition(scrollState.value.toFloat())
                            Log.d("RecipeListFragment","onSelectedCategoryChanged")
                        },
                        onExecuteSearchDone = {
                           newSearch()
                            Log.d("RecipeListFragment","it") })
                }
            }
        }



    }
}