package com.example.testcomposemvvm.presentation.ui.recipe

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testcomposemvvm.domain.model.Recipe
import com.example.testcomposemvvm.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class RecipeViewModel
    @Inject
    constructor(
        private  val recipeRepository: RecipeRepository,
        private  @Named("auth_token") val token:String,
        ) : ViewModel()
    {
       val recipe:MutableState<Recipe?> = mutableStateOf(null)
       val loading = mutableStateOf(false)

       fun onTriggerEvent(event:RecipeEvent){
           viewModelScope.launch {
               try {
                   when (event) {
                       is RecipeEvent.getRecipeEvent -> {
                      if(recipe.value==null) getRecipe(event.id)
                   }
                   }
               } catch (e: Exception) {
                   Log.e("Exception", "message Exception :${e.message} cause Exception ${e.cause}")
               }
           }
        }

       private suspend fun getRecipe(id:Int){
           loading.value =true
           val result = recipeRepository.get(token = token, id = id)
           recipe.value=result
           loading.value=false
       }
    }