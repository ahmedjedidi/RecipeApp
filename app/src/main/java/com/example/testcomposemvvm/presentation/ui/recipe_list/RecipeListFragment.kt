package com.example.testcomposemvvm.presentation.ui.recipe_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person

import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.testcomposemvvm.R
import com.example.testcomposemvvm.presentation.components.*
import com.example.testcomposemvvm.presentation.theme.AppTheme
import com.example.testcomposemvvm.presentation.utils.SnackBarController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipeListFragment : Fragment() {
    val viewModel: RecipeListViewModel by viewModels()
    val snackbarController = SnackBarController(lifecycleScope)




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
                val scaffoldState = rememberScaffoldState();
                val page = viewModel.page.value

                //val isShowing = remember{ mutableStateOf(false) }
                //val snackbarHostState= remember{SnackbarHostState()}

//                Column() {
//
//                    Button(onClick = {
//                        lifecycleScope.launch{
//                            snackbarHostState.showSnackbar(
//                                message = "This is a SnackBar",
//                                actionLabel = "Hide",
//                                duration = SnackbarDuration.Short
//                            )
//                        }}) {
//                        Text("Show SnackBar")
//                    }
//                    snackBarbarDemo2(snackbarHostState)
////                    snackBarDemo(hideSnackBar = {
////                        isShowing.value=false
////                    },isShowing=isShowing.value)
//                }


                AppTheme(darkTheme = isDark) {
                    Scaffold(
                        topBar = {
                            SearchAppBar(
                                query = query,
                                onQueryChanged = viewModel::onQueryChanged,
                                newSearch = {
                                    if (viewModel.selectedCategory.value?.value == "Milk") {
                                    snackbarController.getScope().launch{
                                    snackbarController.showSnackBar(
                                        scaffoldState = scaffoldState,
                                      message=   "invalid Category : Milk!",actionLabel = "Hide"
                                     )
                                    }
                                    }
                                    else {
                                        viewModel.onTriggerEvent(RecipeListEvent.NewSearchEvent)
                                    }
                                },
                                focusManager = focusManager,
                                scrollPosition = viewModel.scrollPosition,
                                selectedCategory = selectedCategory,
                                onSelectCategoryChanged = viewModel::onSelectCategoryChanged,
                                onChangedScrollPosition = viewModel::onChangedScrollPosition,
                                onToogleTheme = viewModel::toggleLightTheme
                            )
                        },
                        drawerContent = {
                           // myDrawer()
                        },
                        bottomBar = {
                        //    myBottomBar()
                        },
                        scaffoldState = scaffoldState,
                        snackbarHost = { scaffoldState.snackbarHostState }
                    ) { paddingValues ->
                        RecipeList(
                            paddingValues = paddingValues,
                            recipes = recipes,
                            onChangeRecipeScrollPosition = viewModel::onChangeRecipeScrollPosition ,
                            page = page,
                            loading = loading,
                            onNextPage ={ viewModel.onTriggerEvent(RecipeListEvent.NextPageEvent)} ,
                            scaffoldState = scaffoldState,
                            snackbarController = snackbarController,
                            navController = findNavController()
                        )
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
//            Text(text = "this is a text ")
//            Spacer(modifier = Modifier.padding(10.dp))
//            Text(text="Text 2")
//            Spacer(modifier = Modifier.padding(10.dp))
//            CircularProgressIndicator()
//            Spacer(modifier = Modifier.padding(10.dp))
//            val customView = HorizontalDottedProgress(LocalContext.current)
//            AndroidView(factory = {customView})
//            }
//        }
//     return view
}
@Composable
fun myBottomBar(
){
    BottomNavigation(
        elevation = 12.dp
    ) {
        BottomNavigationItem(
            selected = false, onClick = { },
            icon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = null
                )
            },
        )
        BottomNavigationItem(
            selected = false, onClick = { },
            icon = {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = null
                )
            },
        )

        BottomNavigationItem(
            selected = false, onClick = { },
            icon = {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null
                )
            },
        )

    }
}

@Composable
fun myDrawer() {
    Column(modifier = Modifier.padding(8.dp)) {
        Text("Menu1")
        Text("Menu2")
        Text("Menu3")
        Text("Menu4")
    }
}


@Composable
fun snackBarbarDemo2(
    snackbarHostState: SnackbarHostState
){
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val snackBar= createRef();

            SnackbarHost(modifier = Modifier.constrainAs(snackBar) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, hostState =snackbarHostState, snackbar = {
                Snackbar(action = {
                    TextButton(
                        onClick = { snackbarHostState.currentSnackbarData?.dismiss() }
                    ) {
                        Text(text = snackbarHostState.currentSnackbarData?.message ?: "", style = TextStyle(color = Color.White))
                    }

                }) {
                    Text(snackbarHostState.currentSnackbarData?.actionLabel ?: "")
                }
            })
    }
}




 @Composable
 fun snackBarDemo(
     isShowing:Boolean,
     hideSnackBar: ()-> Unit
 ){
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val snackBar= createRef();
        if(isShowing){
            Snackbar(modifier = Modifier.constrainAs(snackBar) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },action = { Text("hide" ,modifier=Modifier.clickable(onClick = hideSnackBar),
                style = MaterialTheme.typography.h4) }){
                Text("SnackBar")
            }
        }
    }
 }

