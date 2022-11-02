package com.example.testcomposemvvm.network.response

import com.example.testcomposemvvm.network.data.RecipeDto
import com.google.gson.annotations.SerializedName

data class RecipeSearchResponse (
    @SerializedName("count")
    var count:Int,
    @SerializedName("results")
    var recipes:List<RecipeDto>
    )
