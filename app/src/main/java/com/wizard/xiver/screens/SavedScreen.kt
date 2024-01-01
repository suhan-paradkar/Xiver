package com.wizard.xiver.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.wizard.xiver.Entry
import com.wizard.xiver.utils.SavedUtils
import com.wizard.xiver.views.PaperView
import kotlinx.coroutines.launch

@Composable
fun SavedScreen(
    paperMutableArgument: MutableState<Entry>,
    mainNavController: NavHostController
) {
    val context = LocalContext.current
    val recomposeBoolean = remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    LaunchedEffect(recomposeBoolean.value) {}
    if (SavedUtils.getSavedPapers(context).entry.isEmpty()){
        EmptyScreen(message = "Go save some papers")
    } else {
        LazyColumn {
            items(SavedUtils.getSavedPapers(context).entry) {
                PaperView(
                    paperEntity = it,
                    onBookmarkClick = {
                        recomposeBoolean.value = !recomposeBoolean.value
                        scope.launch {
                            SavedUtils.deletePaper(it, context)
                        }
                    },
                    paperMutableArgument = paperMutableArgument,
                    mainNavController = mainNavController
                )
            }
        }
    }
}