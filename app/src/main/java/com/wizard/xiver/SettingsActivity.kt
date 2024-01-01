package com.wizard.xiver

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.wizard.xiver.screens.SettingsView
import com.wizard.xiver.ui.theme.XiverTheme
import com.wizard.xiver.utils.theme
import com.wizard.xiver.views.BackTopAppBar

class SettingsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current

            XiverTheme(
                darkTheme = theme().value
            ) {
                Scaffold(
                    topBar = {
                        BackTopAppBar(
                            title = "Settings"
                        ) {
                            context.findActivity()?.finish()
                        }
                    }
                ) {
                    SettingsView(modifier = Modifier.padding(it))
                }
            }
        }
    }
}