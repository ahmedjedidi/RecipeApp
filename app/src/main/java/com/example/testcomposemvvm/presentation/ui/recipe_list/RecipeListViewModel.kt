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


const val PAGE_SIZE=30
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
        var recipeListScrollPosition = 0
        val page= mutableStateOf(1)
    init {
        onTriggerEvent(RecipeListEvent.NewSearchEvent)
        Log.d("isDark",getThemeInPreference().toString())
    }

    fun onTriggerEvent(event:RecipeListEvent){
        viewModelScope.launch {
            try {
             when (event){
                 is RecipeListEvent.NewSearchEvent -> newSearch()
                 is RecipeListEvent.NextPageEvent -> newPage()
             }
            } catch (e:Exception){
                Log.e("Exception","message Exception :${e.message} cause Exception ${e.cause}")
            }
        }
    }

     private suspend fun newSearch(){
         resetSearchState()
         Log.d("viewModel","new Search")
         loading.value=true
            val  result =  recipeRepository.search(token , 1,query.value)
            recipes.value = result
            loading.value=false

    }

    private suspend fun newPage(){
            //prevent duplicate events because recompose is happening so quickly
            // block get new page if is new page request is already in progess
            if((recipeListScrollPosition+1)>= (PAGE_SIZE * page.value)) {
                loading.value = true
                incrementPage()
                Log.d("new Page func", "next page= ${page.value}")

                if (page.value > 1) {
                    val result = recipeRepository.search(
                        token = token,
                        page = page.value,
                        query = query.value
                    )
                    Log.d("new Page func", "next Recipe= ${result}")
                    appendRecipes(result)
                }
                loading.value = false

        }
    }

    //add new recipes to the current List of recipes

    private fun appendRecipes(recipes: List<Recipe>){
        val current = ArrayList(this.recipes.value)
        current.addAll(recipes)
        this.recipes.value=current
    }

    private fun incrementPage(){
        page.value = page.value+1
    }

    fun onChangeRecipeScrollPosition(position:Int){
        recipeListScrollPosition =position
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
        page.value=1
        onChangeRecipeScrollPosition(0)
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