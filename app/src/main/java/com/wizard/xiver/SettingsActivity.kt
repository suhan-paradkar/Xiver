package com.wizard.xiver

import android.os.Bundle
import android.view.Menu
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import com.wizard.xiver.ui.theme.XiverTheme

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XiverTheme {
                Scaffold(
                    topBar = {
                        SettingsTopAppBar()
                    }
                ) {
                    SettingsView(modifier = Modifier.padding(it))
                }
            }
        }
    }
}

@Composable
private fun SettingsView(modifier: Modifier) {
    Box(modifier = modifier) {
        Column{
            ThemeCard()
        }
    }
}

@Composable
fun ThemeCard() {
    val isThemeMenuExpanded = remember {
        mutableStateOf(false)
    }
    Card(
        shape = RectangleShape
    ) {
       Box{
           Text(text = "Theme")
       }
        Box{
            DropdownMenu(
                expanded = isThemeMenuExpanded.value,
                onDismissRequest = {
                    isThemeMenuExpanded.value = false
                }
            ) {
                DropdownMenuItem(
                    text = {
                           Text(text = "Light")
                    },
                    onClick = {
                        isThemeMenuExpanded.value = false
                    }
                )
                DropdownMenuItem(
                    text = {
                           Text(text = "Dark")
                    }, onClick = {
                        isThemeMenuExpanded.value = false
                    }
                )
                DropdownMenuItem(
                    text = {
                           Text(text = "Auto")
                    }, onClick = {
                        isThemeMenuExpanded.value = false
                    }
                )
            }
        }
    }
}

@Composable
fun SettingsTopAppBar() {

}
