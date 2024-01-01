package com.wizard.xiver.utils

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController

class NavigationUtils {
    companion object{
        fun NavController.popUpTo(destination: String) = navigate(destination) {
            popUpTo(graph.findStartDestination().id) {
                saveState = true
            }
            restoreState = true
        }
    }
}