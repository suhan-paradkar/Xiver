package com.wizard.xiver

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkRemove
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wizard.xiver.SavedUtils.Companion.isPaperSaved
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.MissingFieldException
import nl.adaptivity.xmlutil.serialization.UnknownXmlFieldException
import java.io.IOException


@Composable
fun DashboardView() {
    val reloadBoolean = remember {
        mutableStateOf(true)
    }
    Surface(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        DashListHost(reloadBoolean)
    }
}


@Composable
fun DashListHost(reloadBoolean: MutableState<Boolean>) {
    val query = remember {
        mutableStateOf("all:electron")
    }
    ResultScreen(query, reloadBoolean)
}

@Composable
fun DashHolder(
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

@OptIn(ExperimentalSerializationApi::class)
@Composable
fun ResultScreen(
    query: MutableState<String>,
    reloadBoolean: MutableState<Boolean>
) {
    val paperDisplayMutableList = remember { mutableStateListOf<Entry>()}
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
    val index = remember{ mutableIntStateOf(0) }
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
        if (minorCardState.value != "Loading" || index.intValue == 0){
            loadMore.value = isAtBottom.value
        }
    }
    LaunchedEffect(loadMore.value, reloadBoolean.value) {
        if (!isLoading.value) {
            Log.d("LaunchedEffect","Running index = "+ index.intValue.toString())
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
                    if(index.intValue == 0) {
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
            } catch (e: retrofit2.HttpException) {
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
        DashHolder(dashState = dashState, error = error, index = index, reloadBoolean = reloadBoolean)
    } else {
        if (paperDisplayMutableList.isNotEmpty()) {
            Box(
                modifier = Modifier.padding(start = 10.dp, end = 10.dp)
            ) {
                LazyColumn(
                    state = state
                ) {
                    items(items = paperDisplayMutableList) {
                        PaperView(paperEntity = it)
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
        ){
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaperView(paperEntity: Entry) {
    ElevatedCard(
        modifier = Modifier.padding(bottom = 5.dp),
        shape = RoundedCornerShape(5)
    ) {
        Column(
            modifier = Modifier.wrapContentHeight(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, top = 10.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = paperEntity.title,
                        style = MaterialTheme.typography.headlineSmall,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Column {
                        if (paperEntity.author.isNotEmpty()) {
                            paperEntity.author.forEach {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        modifier = Modifier.padding(end = 5.dp),
                                        imageVector = Icons.Filled.Person,
                                        contentDescription = "Author"
                                    )
                                    Text(
                                        text = it.name.authorName,
                                        style = MaterialTheme.typography.titleSmall
                                    )
                                }
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(5.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        text = paperEntity.summary.replace("\n", " ").replace("\r", " "),
                        maxLines = 10,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    // KatexView(text = paperEntity.summary.replace("\n", " ").replace("\r", " "), textColor = MaterialTheme.colorScheme.primary.toArgb())
                }
        }
        Box(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
        ) {
            val paperSaved = remember {
                mutableStateOf(false)
            }
            val scope = rememberCoroutineScope()
            val context =  LocalContext.current

            LaunchedEffect(true) {
                paperSaved.value = isPaperSaved(paperEntity, context)
            }

            Card(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .wrapContentSize()
                    .padding(end = 10.dp),
                shape = RoundedCornerShape(20),
                onClick = {
                    if(paperSaved.value) {
                        scope.launch {
                            SavedUtils.deletePaper(paperEntity, context)
                            paperSaved.value = isPaperSaved(paperEntity, context)
                        }
                    } else {
                        scope.launch {
                            SavedUtils.savePaper(paperEntity, context)
                            paperSaved.value = isPaperSaved(paperEntity, context)
                        }
                    }
                }
            ) {
                if (paperSaved.value) {
                    Icon(
                        imageVector = Icons.Default.BookmarkRemove,
                        contentDescription = "Remove saved Article",
                        modifier = Modifier
                            .size(40.dp)
                            .padding(5.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.BookmarkAdd,
                        contentDescription = "Save Article",
                        modifier = Modifier
                            .size(40.dp)
                            .padding(5.dp)
                    )
                }
            }
            Card(
                modifier = Modifier
                    .wrapContentHeight()
                    .align(Alignment.CenterEnd)
                    .padding(end = 60.dp),
                shape = RoundedCornerShape(20)
            ) {
                Icon(
                    imageVector = Icons.Default.Download,
                    contentDescription = "Download Article",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(5.dp)
                )
            }
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

@Preview
@Composable
fun PaperViewPreview(){
    val emptyList = List(size = 3, init = {(Link("",it.toString(), ""))})
    val emptyList2 = List(size = 3, init = {Author(name = Name(it.toString()))})
    PaperView(
        Entry (
            id = "",
            title = "Title",
            summary = "A Large Summary",
            author = emptyList2,
            link = emptyList,
            published = "00/00/0000",
            updated = "11/11/1111",
            comment = "A super looong comment",
            category = Category(term = ""),
            journalRef = "",
            primaryCategory = Category(term = "")
        )
    )
}