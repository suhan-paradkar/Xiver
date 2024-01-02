package com.wizard.xiver.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.wizard.xiver.Entry
import com.wizard.xiver.screens.EmptyScreen
import com.wizard.xiver.screens.ResultScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(
    mainNavController: NavHostController,
    query: MutableState<String>,
    paperMutableArgument: MutableState<Entry>
) {
    val reloadBoolean = remember {
        mutableStateOf(true)
    }
    val isActive = remember {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = {
            DockedSearchBar(
                modifier = Modifier.padding(10.dp),
                query = query.value,
                onQueryChange ={
                    query.value = it
                },
                onSearch ={
                    reloadBoolean.value = !reloadBoolean.value
                    isActive.value = false
                },
                active = isActive.value,
                onActiveChange ={
                    isActive.value = it
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search")
                },
                placeholder = {
                    Text(text = "Search")
                }
            ) {

            }
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            if (query.value != "") {
                ResultScreen(
                    query = query,
                    reloadBoolean = reloadBoolean,
                    paperMutableArgument = paperMutableArgument,
                    mainNavController = mainNavController
                )
            } else {
                EmptyScreen(message = "Search Something")
            }
        }
    }
}