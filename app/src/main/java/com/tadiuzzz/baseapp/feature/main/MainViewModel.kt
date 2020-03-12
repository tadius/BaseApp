package com.tadiuzzz.baseapp.feature.main

import androidx.lifecycle.ViewModel
import com.tadiuzzz.baseapp.utils.InfoHandler
import com.tadiuzzz.baseapp.feature.navigation.Navigator
import javax.inject.Inject

class MainViewModel @Inject constructor(
    val infoHandler: InfoHandler,
    val navigator: Navigator
) : ViewModel() {

}