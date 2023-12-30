package com.wizard.xiver

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wizard.xiver.ui.theme.XiverTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XiverTheme {
                val navSelected = remember { mutableIntStateOf(0) }
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainView(navSelected = navSelected, navHostController = navController)
                }
            }
        }
    }
}

@Composable
fun MainView(modifier: Modifier = Modifier, navSelected: MutableState<Int>, navHostController: NavHostController) {
    var title: String = "Dashboard"
    when (navSelected.value) {
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
            NavigationBarView(navSelected = navSelected, modifier = modifier.fillMaxWidth(), navHostController = navHostController)
        },
        topBar = {
            TopAppBarView(title = title)
        }
    ) {
        NavHostContainer(navHostController = navHostController, modifier = Modifier.padding(it))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarView(title: String) {
    TopAppBar(
        title = {
            Text(text = title)
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
                Icon(Icons.Filled.Home, contentDescription = "Dashboard")
                   },
            label = { Text("Dashboard") },
            selected = navSelected.value == 0,
            onClick = {
                navSelected.value = 0
                navHostController.navigate("dashboard")
            }
        )
        NavigationBarItem(
            icon = {
                Icon(Icons.Filled.Bookmark, contentDescription = "Saved")
                   },
            label = { Text("Saved") },
            selected = navSelected.value == 1,
            onClick = {
                navSelected.value = 1
                navHostController.navigate("SavedScreen")
            }
        )
        NavigationBarItem(
            selected = navSelected.value == 2,
            onClick = {
                navSelected.value = 2
                navHostController.navigate("categories")
                      },
            icon = {
                Icon(Icons.Filled.Category, contentDescription = "Categories")
                   },
            label = { Text("Categories") }
        )
    }
}

@Composable
fun NavHostContainer(navHostController: NavHostController, modifier: Modifier) {
   NavHost(
       navController = navHostController,
       startDestination = "dashboard",
       modifier = modifier
       ) {
       composable("dashboard") {
           DashboardView()
       }
       composable("categories"){
           CategoryScreen()
       }
       composable("SavedScreen"){
           SavedView()
       }
   }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    XiverTheme {
        MainView(navSelected = mutableIntStateOf(0), navHostController = rememberNavController())
    }
}

@Preview
@Composable
fun NavHostControllerPreView() {
    NavHostContainer(navHostController = rememberNavController(), modifier = Modifier.padding(0.dp))
}