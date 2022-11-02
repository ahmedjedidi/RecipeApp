package com.example.testcomposemvvm.presentation.ui.recipe_list

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testcomposemvvm.domain.model.Recipe
import com.example.testcomposemvvm.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class RecipeListViewModel

    @Inject
    constructor(
        private val randomString:String,
        private val recipeRepository: RecipeRepository,
        private @Named("auth_token") val token :String,
        private val sharedPreferences: SharedPreferences
    )
    : ViewModel() {
        val recipes : MutableState<List<Recipe>> = mutableStateOf(listOf())
        val query = mutableStateOf("")
        val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)
        var scrollPosition : Float = 0f
        val loading = mutableStateOf(false)
        val isDark = mutableStateOf(getThemeInPreference())
    init {
        newSearch()
        Log.d("isDark",getThemeInPreference().toString())
    }

     fun newSearch(){
         resetSearchState()
         Log.d("viewModel","new Search")
         loading.value=true
        viewModelScope.launch {
            val  result =  recipeRepository.search(token , 1,query.value)
            recipes.value = result
            loading.value=false
        }
    }

    private fun getThemeInPreference():Boolean{
        return sharedPreferences.getBoolean("isDark",false)
    }

    private fun storeThemeInPreference(){
        sharedPreferences.edit().putBoolean("isDark", isDark.value).apply()
    }

    fun toggleLightTheme(){
        isDark.value = !isDark.value
        storeThemeInPreference()
    }

    private fun resetSearchState(){
        recipes.value= listOf()
        if(selectedCategory.value?.value != query.value)
            clearSelectedCategory()
    }

    private fun clearSelectedCategory(){
        selectedCategory.value=null
    }

    fun onQueryChanged(query:String){
     this.query.value=query
    }

    fun onSelectCategoryChanged(category: String){
        val newCategory=  getFoodCategory(category)
        selectedCategory.value = newCategory
        onQueryChanged(category)
    }

    fun onChangedScrollPosition(position: Float){
        scrollPosition = position
    }
}