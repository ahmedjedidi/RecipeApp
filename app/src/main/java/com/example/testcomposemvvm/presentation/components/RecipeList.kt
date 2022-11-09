package com.example.testcomposemvvm.presentation.components

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.testcomposemvvm.R
import com.example.testcomposemvvm.domain.model.Recipe
import com.example.testcomposemvvm.presentation.ui.recipe_list.PAGE_SIZE
import com.example.testcomposemvvm.presentation.ui.recipe_list.RecipeListEvent
import com.example.testcomposemvvm.presentation.utils.SnackBarController
import kotlinx.coroutines.launch


@Composable
fun RecipeList(
    paddingValues: PaddingValues,
    recipes : List<Recipe>,
    onChangeRecipeScrollPosition: (Int) -> Unit,
    page:Int,
    loading:Boolean,
    onNextPage:(RecipeListEvent) -> Unit,
    scaffoldState :ScaffoldState,
    snackbarController:SnackBarController,
    navController: NavController

){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(color = MaterialTheme.colors.background)
    ) {
        LazyColumn() {
            itemsIndexed(items = recipes) { index, recipe ->
                onChangeRecipeScrollPosition(index)
                if((index+1)>= (PAGE_SIZE * page) && !loading){
                    onNextPage(RecipeListEvent.NextPageEvent)
                }
                RecipeCard(recipe = recipe, onClick = {
                    if (recipe.id != null){
                    val bundle = Bundle()
                    bundle.putInt("recipeId",recipe.id)
                    navController.navigate(R.id.viewRecipe,bundle)
                    }
                    else{
                     snackbarController.getScope().launch {
                         snackbarController.showSnackBar(
                             message = "Recipe don't found",
                             scaffoldState = scaffoldState,
                             actionLabel = "OK"
                         )
                     }
                    }
                })
            }
        }
        CircularIndeterminateProgressBar(isDisplayed = loading)
        defaultSnckBar(snackbarHostState = scaffoldState.snackbarHostState,
            onDismiss = {scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()},
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}