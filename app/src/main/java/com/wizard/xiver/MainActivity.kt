package com.wizard.xiver

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wizard.xiver.ui.theme.XiverTheme
import com.wizard.xiver.utils.EmptySerialUtils
import com.wizard.xiver.utils.theme
import com.wizard.xiver.views.MainView
import com.wizard.xiver.views.PaperFullView
import com.wizard.xiver.views.SearchView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XiverTheme(
                darkTheme = theme().value
            ) {
                val mainNavController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainViewContainer(mainNavController)
                }
            }
        }
    }
}

@Composable
fun MainViewContainer(mainNavController: NavHostController) {
    val paperEntity = remember {
        mutableStateOf(EmptySerialUtils.EmptyEntry)
    }
    val query = remember {
        mutableStateOf("")
    }
    NavHost(
        navController = mainNavController,
        startDestination = "MainView"
    ) {
        composable("MainView") {
            MainView(modifier = Modifier, paperMutableArgument = paperEntity, mainNavController = mainNavController, query = query)
        }
        composable("PaperFullView"){
            PaperFullView(navController = mainNavController, paperEntity = paperEntity)
        }
        composable("SearchView") {
            SearchView(mainNavController = mainNavController, query = query, paperMutableArgument = paperEntity)
        }
    }
}