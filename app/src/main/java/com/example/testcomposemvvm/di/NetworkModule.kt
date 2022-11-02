package com.example.testcomposemvvm.di

import android.app.Application
import com.example.testcomposemvvm.network.RecipeService
import com.example.testcomposemvvm.network.data.RecipeDtoMapper
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRecipeMapper(): RecipeDtoMapper {
        return RecipeDtoMapper()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }


    @Provides
    @Singleton
    fun provideRecipeService(okHttpClient:OkHttpClient): RecipeService {
        return Retrofit.Builder()
            .baseUrl("https://food2fork.ca/api/recipe/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(RecipeService::class.java)
    }

    @Provides
    @Singleton
    @Named("auth_token")
    fun provideAuthToken():String{
       return "Token 9c8b06d329136da358c2d00e76946b0111ce2c48"
    }
}