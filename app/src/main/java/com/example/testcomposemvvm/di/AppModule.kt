package com.example.testcomposemvvm.di

import android.content.Context
import android.content.SharedPreferences
import com.example.testcomposemvvm.presentation.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
    return app as BaseApplication
    }

    @Singleton
    @Provides
    fun provideRandomString():String {
        return "test Random String"
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences{
        return context.getSharedPreferences("preferences_name",Context.MODE_PRIVATE)
    }

}