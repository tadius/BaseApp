package com.tadiuzzz.baseapp.feature.navigation

import androidx.lifecycle.LiveData
import androidx.navigation.NavDirections
import com.tadiuzzz.baseapp.di.scope.AppScope
import com.tadiuzzz.baseapp.utils.SingleLiveEvent
import javax.inject.Inject

@AppScope
class Navigator @Inject constructor() {

    private val _navigationEvent =
        SingleLiveEvent<NavCommand>()
    val navigationEvent: LiveData<NavCommand>
            get() = _navigationEvent

    fun exit() {
        _navigationEvent.value = NavCommand.NavExit()
    }

    fun back() {
        _navigationEvent.value = NavCommand.NavBack()
    }

    fun navigateTo(navDirections: NavDirections) {
        _navigationEvent.value = NavCommand.NavTo(navDirections)
    }

}