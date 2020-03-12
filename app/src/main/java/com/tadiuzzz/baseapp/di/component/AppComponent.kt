package com.tadiuzzz.baseapp.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import com.tadiuzzz.baseapp.di.module.DataModule
import com.tadiuzzz.baseapp.di.module.NetworkModule
import com.tadiuzzz.baseapp.di.module.SharedPreferencesModule
import com.tadiuzzz.baseapp.di.module.ViewModelModule
import com.tadiuzzz.baseapp.di.scope.AppScope
import com.tadiuzzz.baseapp.feature.example.ExampleFragment
import com.tadiuzzz.baseapp.feature.main.MainActivity

@AppScope
@Component(
    modules = arrayOf(
        NetworkModule::class,
        DataModule::class,
        SharedPreferencesModule::class,
        ViewModelModule::class
    )
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)

    fun inject(fragment: ExampleFragment)

}