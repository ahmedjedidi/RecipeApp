package com.example.testcomposemvvm.presentation.ui.recipe

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AndroidUiDispatcher.Companion.Main
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.example.testcomposemvvm.presentation.components.CircularIndeterminateProgressBar
import com.example.testcomposemvvm.presentation.components.RecipeView
import com.example.testcomposemvvm.presentation.components.defaultSnckBar
import com.example.testcomposemvvm.presentation.theme.AppTheme
import com.example.testcomposemvvm.presentation.ui.recipe_list.RecipeListEvent
import com.example.testcomposemvvm.presentation.ui.recipe_list.RecipeListViewModel
import com.example.testcomposemvvm.presentation.utils.SnackBarController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecipeFragment : Fragment() {
    val viewModel: RecipeViewModel by viewModels()
    //private val recipeId: MutableState<Int?> = mutableStateOf(null)
    @Inject
    lateinit var sharedPreferences: SharedPreferences
    val snackbarController = SnackBarController(lifecycleScope)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("viewModel Recipe Fragment",viewModel.toString());

//        CoroutineScope(Main).launch {
//            delay(1000)
//            arguments?.getInt("recipeId").let { rid ->
//                recipeId.value=rid;
//            }
//        }
        arguments?.getInt("recipeId")?.let { rId ->
            viewModel.onTriggerEvent(RecipeEvent.getRecipeEvent(rId))
        }



    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {

                val recipe = viewModel.recipe.value
                val loading = viewModel.loading.value
                val scaffoldState = rememberScaffoldState();

                AppTheme(darkTheme = sharedPreferences.getBoolean("isDark",false)) {
                    Scaffold(
                        scaffoldState = scaffoldState,
                        snackbarHost = {scaffoldState.snackbarHostState  }
                    ) { paddingValues ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                                .background(color = MaterialTheme.colors.background)
                        ) {

                                recipe?.let {
                                    if (it.id==1){
                                        snackbarController.showSnackBar(
                                            message = "An Error occured with this recipe",
                                            actionLabel = "Ok",
                                            scaffoldState = scaffoldState
                                        )
                                    }
                                    else{
                                        RecipeView(recipe = recipe)
                                    }
                                }


                            CircularIndeterminateProgressBar(isDisplayed = loading)
                            defaultSnckBar(snackbarHostState = scaffoldState.snackbarHostState,
                                onDismiss = {scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()},
                                modifier = Modifier.align(Alignment.BottomCenter)
                            )

                        }
                    }
                }
            }
        }
    }
}