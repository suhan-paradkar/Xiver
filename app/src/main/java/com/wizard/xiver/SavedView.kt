package com.wizard.xiver

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun SavedView() {
    val context = LocalContext.current
    if (SavedUtils.getSavedPapers(context).entry.isEmpty()){
        EmptyScreen(message = "Go save some papers")
    } else {
        SavedUtils.getSavedPapers(context).entry.forEach {
            PaperView(paperEntity = it)
        }
    }
}