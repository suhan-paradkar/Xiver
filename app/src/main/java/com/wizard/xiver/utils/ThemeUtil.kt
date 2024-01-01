package com.wizard.xiver.utils

import SettingsUtil
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun theme(): MutableState<Boolean> {
    val context = LocalContext.current
    val settings = SettingsUtil(context)
    val themeInt = settings.getTheme.collectAsState(initial = 0)
    val isDarkMode = remember {
        mutableStateOf(false)
    }
    isDarkMode.value = when (themeInt.value) {
        0 -> {
            isSystemInDarkTheme()
        }
        1 -> {
            false
        }
        2 -> {
            true
        }
        else -> {
            isSystemInDarkTheme()
        }
    }
    return isDarkMode
}