package com.wizard.xiver.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.wizard.xiver.Entry


@Composable
fun DashboardScreen(paperMutableArgument: MutableState<Entry>, mainNavController: NavHostController) {
    val reloadBoolean = remember {
        mutableStateOf(true)
    }
    Surface(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        DashListHost(reloadBoolean = reloadBoolean, paperMutableArgument = paperMutableArgument, mainNavController = mainNavController)
    }
}


@Composable
fun DashListHost(
    reloadBoolean: MutableState<Boolean>,
    paperMutableArgument: MutableState<Entry>,
    mainNavController: NavHostController
) {
    val query = remember {
        mutableStateOf("all:electron")
    }
    ResultScreen(query, reloadBoolean, paperMutableArgument, mainNavController)
}

