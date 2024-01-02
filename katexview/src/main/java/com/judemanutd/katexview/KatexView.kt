package com.judemanutd.katexview

import android.annotation.SuppressLint
import android.content.Context
import android.view.MotionEvent
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.x5.template.Chunk
import com.x5.template.Theme
import com.x5.template.providers.AndroidTemplates


private const val TAG_FORMULA: String = "formula"
private const val TAG_TEXT_COLOR: String = "textColor"
private const val TAG_TEXT_SIZE: String = "textSize"

private var mText: String? = null
//TODO: pick theme set text color
private var mTextColor: Int = android.R.color.holo_purple
private var mTextSize: Int = 20
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun KatexView(text: String, textColor: Int, backgroundColor: Int, textSize: Int) {
    AndroidView(factory = {

        WebView(it).apply {
            settings.allowFileAccess = true
            settings.allowUniversalAccessFromFileURLs = true
            settings.javaScriptEnabled = true
            settings.cacheMode = WebSettings.LOAD_NO_CACHE
            settings.displayZoomControls = false
            settings.builtInZoomControls = false
            settings.setSupportZoom(false)
            settings.useWideViewPort = false
            setBackgroundColor(backgroundColor)
            setText(text, it)
            setTextColor(textColor, it)
            setTextSize(textSize, it)
            loadDataWithBaseURL(null, loadData(it), "text/html", "utf-8", "about:blank")
            isVerticalScrollBarEnabled = false
            isHorizontalScrollBarEnabled = false
            getSettings().layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            webViewClient = WebViewClient()
        }
    })
}

fun setText(text: String, context: Context) {
    mText = text
    loadData(context)
}

fun setTextColor(color: Int, context: Context) {
    mTextColor = color
    loadData(context)
}

fun setTextSize(size: Int, context: Context) {
    mTextSize = size
    loadData(context)
}

fun getText(): String? {
    return mText
}

private fun loadData(context: Context): String {
    if (mText != null) {
        val chunk: Chunk = getChunk(context = context)
        chunk.set(TAG_FORMULA, mText)
        chunk.set(TAG_TEXT_COLOR, getHexColor(mTextColor))
        chunk.set(TAG_TEXT_SIZE, mTextSize)
        return chunk.toString()
    }
    return ""
}

private fun getChunk(context: Context): Chunk {
    val loader = AndroidTemplates(context)
    return Theme(loader).makeChunk("katex")
}

private fun getHexColor(intColor: Int): String {
    //Android and javascript color format differ javascript support Hex color,
    // so the android color which user sets is converted to hexcolor to replicate the same in javascript.
    return String.format("#%06X", 0xFFFFFF and intColor)
}