package com.example.testcomposemvvm.presentation.ui.recipe_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.testcomposemvvm.R
import com.example.testcomposemvvm.presentation.components.CircularIndeterminateProgressBar
import com.example.testcomposemvvm.presentation.components.FoodCategoryChip
import com.example.testcomposemvvm.presentation.components.RecipeCard
import com.example.testcomposemvvm.presentation.components.SearchAppBar
import com.example.testcomposemvvm.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    val viewModel:RecipeListViewModel by activityViewModels()

//        super.onCreate(savedInstanceState)
//        Log.d("Fragment",viewModel.getRandomString())
//        Log.d("Fragment",viewModel.getToken())
//        Log.d("Fragment",viewModel.getRepo().toString())
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return ComposeView(requireContext()).apply {
            setContent {
                // Every Time viewModel recipes change , recipes will change and the composable using it will be recomposed
                val recipes = viewModel.recipes.value
                val query = viewModel.query.value
                val selectedCategory = viewModel.selectedCategory.value
                val focusManager = LocalFocusManager.current
                val loading = viewModel.loading.value
                val isDark = viewModel.isDark.value

                AppTheme(darkTheme = isDark) {
                    Column() {

                        SearchAppBar(
                            query = query,
                            onQueryChanged = viewModel::onQueryChanged  ,
                            newSearch =  viewModel::newSearch ,
                            focusManager = focusManager,
                            scrollPosition = viewModel.scrollPosition,
                            selectedCategory = selectedCategory,
                            onSelectCategoryChanged = viewModel::onSelectCategoryChanged,
                            onChangedScrollPosition = viewModel::onChangedScrollPosition,
                            onToogleTheme = viewModel::toggleLightTheme
                        )
                        Box(modifier = Modifier.fillMaxSize()
                            .background(color= MaterialTheme.colors.background)
                        ){
                            LazyColumn(){
                                itemsIndexed(items = recipes){
                                        index, recipe -> RecipeCard(recipe = recipe, onClick = {})
                                }
                            }
                            CircularIndeterminateProgressBar(isDisplayed = loading)
                        }


                    }
                }


            }
        }
//        val view = inflater.inflate(R.layout.fragment_recipe_list,container,false)
//        view.findViewById<ComposeView>(R.id.compose_view).setContent {
//            Column(modifier= Modifier
//                .border(border = BorderStroke(1.dp, Color.Black))
//                .padding(16.dp)) {
//            Text(text = "this Ahmed M3allem ")
//            Spacer(modifier = Modifier.padding(10.dp))
//            Text(text="Ye eni ye franca")
//            Spacer(modifier = Modifier.padding(10.dp))
//            CircularProgressIndicator()
//            Spacer(modifier = Modifier.padding(10.dp))
//            val customView = HorizontalDottedProgress(LocalContext.current)
//            AndroidView(factory = {customView})
//            }
//        }
//     return view
    }
}

