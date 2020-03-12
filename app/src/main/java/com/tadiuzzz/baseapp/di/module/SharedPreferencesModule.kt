package com.tadiuzzz.baseapp.di.module

import android.content.Context
import android.content.SharedPreferences
import com.tadiuzzz.baseapp.di.scope.AppScope
import dagger.Module
import dagger.Provides
import com.tadiuzzz.baseapp.BuildConfig

@Module
class SharedPreferencesModule {

    @Provides
    @AppScope
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(BuildConfig.APPLICATION_ID + "_preferences", Context.MODE_PRIVATE)
    }

}