package com.example.testcomposemvvm.di

import com.example.testcomposemvvm.network.RecipeService
import com.example.testcomposemvvm.network.data.RecipeDtoMapper
import com.example.testcomposemvvm.repository.RecipeRepository
import com.example.testcomposemvvm.repository.RecipeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideRecipeRepository(
        recipeService: RecipeService,
        recipeDtoMapper: RecipeDtoMapper
    ) : RecipeRepository{
        return RecipeRepositoryImpl(
            recipeService,recipeDtoMapper
        )
    }
}