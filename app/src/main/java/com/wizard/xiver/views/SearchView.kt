package com.wizard.xiver.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.wizard.xiver.Entry
import com.wizard.xiver.screens.EmptyScreen
import com.wizard.xiver.screens.ResultScreen
import kotlinx.coroutines.launch

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
    val selectedFilterChip = remember {
        mutableIntStateOf(-1)
    }
    val focusRequester = remember { FocusRequester() }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var textFieldValueState = remember {
        mutableStateOf(
            TextFieldValue(
                text = query.value,
                selection = TextRange(query.value.length)
            )
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            DockedSearchBarWithTextField(
                modifier = Modifier.padding(10.dp)
                    .focusRequester(focusRequester),
                query = textFieldValueState,
                onQueryChange ={
                    textFieldValueState.value = it
                },
                onSearch ={
                    if (selectedFilterChip.intValue != -1) {
                        reloadBoolean.value = !reloadBoolean.value
                        isActive.value = false
                    } else {
                        isActive.value = false
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                "Choose Search Filter"
                            )
                        }
                    }
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
                SearchBoxSuggest(query = textFieldValueState, selectedFilterChip = selectedFilterChip, focusRequester = focusRequester)
            }
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            if (query.value != "") {
                key(reloadBoolean.value) {
                    ResultScreen(
                        query = query,
                        reloadBoolean = reloadBoolean,
                        paperMutableArgument = paperMutableArgument,
                        mainNavController = mainNavController
                    )
                }
            } else {
                EmptyScreen(message = "Search Something")
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchBoxSuggest(
    query: MutableState<TextFieldValue>,
    selectedFilterChip: MutableState<Int>,
    focusRequester: FocusRequester
) {

    val searchFilterItems = arrayOf("all:" to "All", "ti:" to "Title", "au:" to "Author", "abs:" to "Abstract", "co:" to "Comment", "jr:" to "Journal Reference", "cat:" to "Category", "rn:" to "Report Number", "id:" to "Arxiv ID")

    val modes = arrayOf("AND", "ANDNOT", "OR")
    val selectedMode = remember {
        mutableIntStateOf(0)
    }

    Box(modifier = Modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(text = "Search In")

            Spacer(modifier = Modifier.padding(5.dp))

            FlowRow(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                searchFilterItems.withIndex().forEach() {
                    SearchFilterChip(
                        query = query,
                        selectedFilterChip = selectedFilterChip,
                        queryAdd = it.value.first,
                        index = it.index,
                        label = it.value.second,
                        selectedMode = selectedMode,
                        modes = modes,
                        focusRequester = focusRequester
                    )
                }
            }

            Spacer(modifier = Modifier.padding(5.dp))

            Text(text = "Mode")

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                modes.withIndex().forEach {
                    ModeFilterChip(selectedMode = selectedMode, label = it.value, index = it.index)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModeFilterChip(selectedMode: MutableIntState, label: String, index: Int){
    FilterChip(
        selected = (selectedMode.intValue == index),
        onClick = {
            selectedMode.intValue = index
        },
        label = {
            Text(text = label)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchFilterChip(
    query: MutableState<TextFieldValue>,
    selectedFilterChip: MutableState<Int>,
    queryAdd: String,
    index: Int,
    label: String,
    selectedMode: MutableIntState,
    modes: Array<String>,
    focusRequester: FocusRequester
){
    FilterChip(
        onClick = {
            if (query.value.text == "") {
                val newQuery = query.value.text + queryAdd
                query.value = TextFieldValue(text = newQuery, selection = TextRange(newQuery.length))
            } else {
                val newQuery = query.value.text + " " + modes[selectedMode.intValue] + " " + queryAdd
                query.value = TextFieldValue(text = newQuery, selection = TextRange(newQuery.length))
            }
            selectedFilterChip.value = index
            focusRequester.requestFocus()
        },
        label = {
            Text(text = label)
        },
        selected = (selectedFilterChip.value == index)
    )
}

@Composable
@Preview
fun SearchBoxSuggestPreview() {
    val mutableString = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val mutableInt = remember {
        mutableIntStateOf(0)
    }
    SearchBoxSuggest(
        query = mutableString,
        selectedFilterChip = mutableInt,
        focusRequester = FocusRequester()
    )
}