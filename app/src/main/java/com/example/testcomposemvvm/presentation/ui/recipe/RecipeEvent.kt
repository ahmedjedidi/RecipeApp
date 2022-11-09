package com.example.testcomposemvvm.presentation.ui.recipe

sealed class RecipeEvent{
    data class getRecipeEvent(val id:Int): RecipeEvent()
}