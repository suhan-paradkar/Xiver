package com.wizard.xiver.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.wizard.xiver.utils.ArxivNetUtils
import com.wizard.xiver.Entry
import com.wizard.xiver.views.PaperView
import com.wizard.xiver.utils.SavedUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.MissingFieldException
import nl.adaptivity.xmlutil.serialization.UnknownXmlFieldException
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalSerializationApi::class)
@Composable
fun ResultScreen(
    query: MutableState<String>,
    reloadBoolean: MutableState<Boolean>,
    paperMutableArgument: MutableState<Entry>,
    mainNavController: NavHostController
) {
    val paperDisplayMutableList = remember { mutableStateListOf<Entry>() }
    val localQuery = query.value
    val state = rememberLazyListState()
    val error = remember {
        mutableStateOf("")
    }
    val isAtBottom = remember {
        derivedStateOf { !state.canScrollForward }
    }

    val minorCardState = remember {
        mutableStateOf("Success")
    }
    val index = remember { mutableIntStateOf(0) }
    val scope = rememberCoroutineScope()
    val isLoading = remember {
        mutableStateOf(false)
    }
    var totalResults = 0
    val dashState = remember {
        mutableStateOf("Loading")
    }
    val loadMore = remember {
        mutableStateOf(true)
    }
    LaunchedEffect(isAtBottom.value, reloadBoolean.value) {
        if (minorCardState.value != "Loading" || index.intValue == 0) {
            loadMore.value = isAtBottom.value
        }
    }
    LaunchedEffect(loadMore.value, reloadBoolean.value) {
        if (!isLoading.value) {
            Log.d("LaunchedEffect", "Running index = " + index.intValue.toString())
            isLoading.value = true
            try {
                if (index.intValue == 0) {
                    dashState.value = "Loading"
                } else {
                    minorCardState.value = "Loading"
                }
                val result = ArxivNetUtils.ArxivApi.retrofitService.getSearchQueryItems(
                    query = localQuery,
                    indexBegin = (index.intValue) * 10,
                    sortBy = "relevance",
                    sortOrder = "descending"
                )
                totalResults = result.totalResults.totalResults
                if (result.entry.isNotEmpty()) {
                    dashState.value = "Success"
                    minorCardState.value = "Success"
                    paperDisplayMutableList += result.entry
                    delay(3000)
                } else {
                    if (index.intValue == 0) {
                        dashState.value = "Error"
                    } else {
                        minorCardState.value = "Error"
                    }
                    error.value = "No results found"
                    delay(3000)
                }
            } catch (e: IOException) {
                isLoading.value = false
                if (index.intValue == 0) {
                    dashState.value = "Error"
                } else {
                    minorCardState.value = "Error"
                }
                error.value = e.toString()
                Log.e("getSearchResults", e.toString())
            } catch (e: HttpException) {
                isLoading.value = false
                if (index.intValue == 0) {
                    dashState.value = "Error"
                } else {
                    minorCardState.value = "Error"
                }
                error.value = e.toString()
                Log.e("getSearchResults", e.toString())
            } catch (e: UnknownXmlFieldException) {
                isLoading.value = false
                if (index.intValue == 0) {
                    dashState.value = "Error"
                } else {
                    minorCardState.value = "Error"
                }
                error.value = e.toString()
                Log.e("getSearchResults", e.toString())
            } catch (e: MissingFieldException) {
                isLoading.value = false
                if (index.intValue == 0) {
                    dashState.value = "Error"
                } else {
                    minorCardState.value = "Error"
                }
                error.value = e.toString()
                Log.e("getSearchResults", e.toString())
            } finally {
                index.intValue += 1
                isLoading.value = false
            }
        }
    }
    if (dashState.value != "Success") {
        EmptyScreenHolder(
            dashState = dashState,
            error = error,
            index = index,
            reloadBoolean = reloadBoolean
        )
    } else {
        if (paperDisplayMutableList.isNotEmpty()) {
            Box(
                modifier = Modifier.padding(start = 10.dp, end = 10.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.padding(start = 5.dp, end = 5.dp),
                    state = state
                ) {
                    items(items = paperDisplayMutableList) {
                        val paperSaved = remember {
                            mutableStateOf(false)
                        }
                        val context = LocalContext.current

                        LaunchedEffect(true) {
                            paperSaved.value = SavedUtils.isPaperSaved(it, context)
                        }
                        PaperView(
                            paperEntity = it,
                            onBookmarkClick = {
                                if (paperSaved.value) {
                                    scope.launch {
                                        SavedUtils.deletePaper(it, context)
                                        paperSaved.value = SavedUtils.isPaperSaved(it, context)
                                    }
                                } else {
                                    scope.launch {
                                        SavedUtils.savePaper(it, context)
                                        paperSaved.value = SavedUtils.isPaperSaved(it, context)
                                    }
                                }
                            },
                            paperMutableArgument = paperMutableArgument,
                            mainNavController = mainNavController
                        )
                    }
                    item {
                        MinorCardHolder(
                            minorCardState = minorCardState,
                            error = error,
                        )

                    }
                }
                Log.d(
                    "DashBoardViewModel",
                    "Total " + paperDisplayMutableList.size.toString() + " items"
                )
            }
        } else {
            if (totalResults == 0) {
                Log.d("DashboardViewHost", "No results received")
                ErrorScreen(
                    error = "No Results Received",
                    index = index,
                    reloadBoolean = reloadBoolean
                )
            } else {
                Log.d("DashboardViewHost", "No items found")
                ErrorScreen(
                    error = "No Items Found",
                    index = index,
                    reloadBoolean = reloadBoolean
                )
            }
        }
    }
}

@Composable
fun MinorCardHolder(minorCardState: MutableState<String>, error: MutableState<String>) {
    if (minorCardState.value == "Error") {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = error.value,
                modifier = Modifier
                    .paddingFromBaseline(top = 24.dp),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
            )
        }
    } else if(minorCardState.value == "Loading") {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun ErrorScreen(error: String, index: MutableIntState, reloadBoolean: MutableState<Boolean>) {
    val retryAction = EmptyScreenAction(
        string = "Retry",
        icon = Icons.Outlined.Refresh,
        onClick = {
            index.intValue = 0
            reloadBoolean.value = !reloadBoolean.value
        }
    )
    val actionList: MutableList<EmptyScreenAction> = emptyList<EmptyScreenAction>().toMutableList()
    actionList += retryAction
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        EmptyScreen(message = error, actions = actionList)
    }
}

@Composable
fun LoadingScreen() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun EmptyScreenHolder(
    dashState: MutableState<String>,
    error: MutableState<String>,
    index: MutableIntState,
    reloadBoolean: MutableState<Boolean>,
) {
    when (dashState.value) {
        "Loading" -> {
            LoadingScreen()
        }
        "Error" -> {
            ErrorScreen(error = error.value, index = index, reloadBoolean = reloadBoolean)
        }
    }
}