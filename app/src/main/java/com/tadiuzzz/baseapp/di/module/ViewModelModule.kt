package com.tadiuzzz.baseapp.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tadiuzzz.baseapp.di.ViewModelFactory
import com.tadiuzzz.baseapp.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import com.tadiuzzz.baseapp.feature.example.ExampleViewModel
import com.tadiuzzz.baseapp.feature.main.MainViewModel

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun mainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExampleViewModel::class)
    internal abstract fun exampleViewModel(viewModel: ExampleViewModel): ViewModel

}