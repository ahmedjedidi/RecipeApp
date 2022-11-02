package com.example.testcomposemvvm.network.data

import com.example.testcomposemvvm.domain.model.Recipe
import com.example.testcomposemvvm.domain.utils.DomainMapper

class RecipeDtoMapper :  DomainMapper<RecipeDto, Recipe>{
    override fun mapToDomainModel(entity: RecipeDto): Recipe {
       return Recipe(
           id=entity.pk,
           title = entity.title,
           featuredImage = entity.featuredImage,
           rating = entity.rating,
           publisher = entity.publisher,
           sourceUrl = entity.sourceUrl,
           description = entity.description,
           cookingInstructions = entity.cookingInstructions,
           ingredients = entity.ingredients?: listOf(),
           dateAdded = entity.dateAdded,
           dateUpdated = entity.dateUpdated,
       )
    }

    override fun mapFromDomainModel(domainModel: Recipe): RecipeDto {
        return RecipeDto(
            pk=domainModel.id,
            title = domainModel.title,
            featuredImage = domainModel.featuredImage,
            rating = domainModel.rating,
            publisher = domainModel.publisher,
            sourceUrl = domainModel.sourceUrl,
            description = domainModel.description,
            cookingInstructions = domainModel.cookingInstructions,
            ingredients = domainModel.ingredients,
            dateAdded = domainModel.dateAdded,
            dateUpdated = domainModel.dateUpdated,
        )
    }

    fun toDomainList(initial:List<RecipeDto>):List<Recipe>{
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Recipe>):List<RecipeDto>{
        return initial.map{ mapFromDomainModel(it)}
    }
}