package com.wizard.xiver.views

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wizard.xiver.Entry
import com.wizard.xiver.SettingsActivity
import com.wizard.xiver.screens.CategoryScreen
import com.wizard.xiver.screens.DashboardScreen
import com.wizard.xiver.screens.SavedScreen
import com.wizard.xiver.utils.NavigationUtils.Companion.popUpTo

@Composable
fun MainView(
    modifier: Modifier,
    paperMutableArgument: MutableState<Entry>,
    mainNavController: NavHostController
) {
    val navSelected = remember { mutableIntStateOf(1) }
    val navController = rememberNavController()
    var title: String = "Saved"
    when (navSelected.intValue) {
        0 -> {
            title = "Dashboard"
        }
        1 -> {
            title = "Saved"
        }
        2 -> {
            title = "Categories"
        }
    }
    Scaffold(
        bottomBar = {
            NavigationBarView(
                navSelected = navSelected,
                modifier = modifier.fillMaxWidth(),
                navHostController = navController
            )
        },
        topBar = {
            TopAppBarView(title = title)
        }
    ) {
        MainNavHost(navHostController = navController, modifier = Modifier.padding(it), paperMutableArgument = paperMutableArgument, mainNavController = mainNavController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarView(title: String) {
    val context = LocalContext.current
    val intent = Intent(context, SettingsActivity::class.java)
    TopAppBar(
        title = {
            Text(text = title)
        },
        actions = {
            IconButton(
                onClick = {
                    context.startActivity(intent)
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = "Settings"
                )
            }
        }
    )
}

@Composable
fun NavigationBarView(
    modifier: Modifier = Modifier,
    navSelected: MutableState<Int>,
    navHostController: NavHostController
) {
    NavigationBar(
        modifier = modifier.fillMaxWidth()
    ) {
        NavigationBarItem(
            icon = {
                if (navSelected.value == 0) {
                    Icon(Icons.Filled.Home, contentDescription = "Dashboard")
                } else {
                    Icon(Icons.Outlined.Home, contentDescription = "Dashboard")
                }
            },
            label = { Text("Dashboard") },
            selected = navSelected.value == 0,
            onClick = {
                navSelected.value = 0
                navHostController.popUpTo("dashboard")
            }
        )
        NavigationBarItem(
            icon = {
                if (navSelected.value == 1) {
                    Icon(Icons.Filled.Bookmark, contentDescription = "Saved")
                } else {
                    Icon(Icons.Outlined.BookmarkBorder, contentDescription = "Saved")
                }
            },
            label = { Text("Saved") },
            selected = navSelected.value == 1,
            onClick = {
                navSelected.value = 1
                navHostController.popUpTo("SavedScreen")
            }
        )
        NavigationBarItem(
            selected = navSelected.value == 2,
            onClick = {
                navSelected.value = 2
                navHostController.popUpTo("categories")
            },
            icon = {
                if (navSelected.value == 2) {
                    Icon(Icons.Filled.Category, contentDescription = "Categories")
                } else {
                    Icon(Icons.Outlined.Category, contentDescription = "Categories")
                }
            },
            label = { Text("Categories") }
        )
    }
}

@Composable
fun MainNavHost(
    navHostController: NavHostController,
    modifier: Modifier,
    paperMutableArgument: MutableState<Entry>,
    mainNavController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = "SavedScreen",
        modifier = modifier
    ) {
        composable("dashboard") {
            DashboardScreen(paperMutableArgument = paperMutableArgument, mainNavController = mainNavController)
        }
        composable("categories") {
            CategoryScreen()
        }
        composable("SavedScreen") {
            SavedScreen(paperMutableArgument = paperMutableArgument, mainNavController = mainNavController)
        }
    }
}