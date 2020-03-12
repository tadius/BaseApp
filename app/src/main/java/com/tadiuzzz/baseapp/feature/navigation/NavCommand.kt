package com.tadiuzzz.baseapp.feature.navigation

import androidx.navigation.NavDirections

sealed class NavCommand {
    class NavTo(val navDirections: NavDirections) : NavCommand()
    class NavBack() : NavCommand()
    class NavExit() : NavCommand()
}