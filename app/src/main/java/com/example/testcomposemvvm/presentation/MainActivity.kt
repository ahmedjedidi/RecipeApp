package com.example.testcomposemvvm.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.testcomposemvvm.R
import com.example.testcomposemvvm.network.RecipeService
import com.google.gson.GsonBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var randomString: String

    @Inject
    lateinit var app: BaseApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val service = Retrofit.Builder()
            .baseUrl("https://food2fork.ca/api/recipe/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(RecipeService::class.java)
         CoroutineScope(IO).launch {
            val recipe = service.get(token = "Token 9c8b06d329136da358c2d00e76946b0111ce2c48",
                 id = 583
             )
            Log.d("on Create","Recipe= ${recipe.title}")
            Log.d("on Create string",randomString)
            Log.d("on Create string", app.toString() )
         }
//        val mapper = RecipeNetworkMapper()
//        val recipe = Recipe()
//        val networkEntity:RecipeNetworkEntity=mapper.mapToEntity(recipe)
//        val r:Recipe=mapper.mapFromEntity(networkEntity)
//        supportFragmentManager.beginTransaction().replace(R.id.main_container,RecipeListFragment()).commit()
//            setContent {
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(color = Color(0xFFF2F2F2))
//                        .verticalScroll(rememberScrollState())
//                        .fillMaxHeight(),
//
//                    ) {
//                    Image(
//                        ImageBitmap.imageResource(id = R.drawable.happy_meal_small),
//                        contentDescription = null,
//                        modifier = Modifier.height(300.dp),
//                        contentScale = ContentScale.Crop
//                    )
//
//                    Column(modifier = Modifier.padding(8.dp))
//                    {
//                        Row(
//                            modifier =
//                            Modifier.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.SpaceBetween
//                        ) {
//                            Text(
//                                text = "Burguer", style = androidx.compose.ui.text.TextStyle(
//                                    fontSize = 24.sp,
//                                    color = Color.Red
//                                )
//                            )
//                            Text(
//                                text = "22 DT", style = androidx.compose.ui.text.TextStyle(
//                                    fontSize = 17.sp,
//                                    color = Color.Blue
//                                ), modifier = Modifier.align(alignment = Alignment.CenterVertically)
//
//                            )
//                        }
//                        Spacer(modifier = Modifier.padding(6.dp))
//                        Text(
//                            text = "Sauce chili , Mayo, Fried Chicken, cheddar",
//                            style = androidx.compose.ui.text.TextStyle(
//                                fontSize = 17.sp,
//                                color = Color.Red
//                            )
//                        )
//                        Spacer(modifier = Modifier.padding(6.dp))
//                        Button(
//                            onClick = { Log.d("test","button Clicked") },
//                            modifier = Modifier
//                                .align(alignment = Alignment.CenterHorizontally)
//                                .fillMaxWidth()
//                                .padding(horizontal = 10.dp),
//                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
//                            shape = RoundedCornerShape(20.dp),
//                        ) {
//                            Text(
//                                text = "Commander",
//                                style = TextStyle(color = Color.White, fontSize = 17.sp)
//                            )
//                        }
//
//                    }
//
//
//                }
//            }
        }
    }






