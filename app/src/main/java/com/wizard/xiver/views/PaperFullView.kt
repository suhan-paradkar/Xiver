package com.wizard.xiver.views

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.judemanutd.katexview.KatexView
import com.wizard.xiver.Entry
import com.wizard.xiver.utils.EmptySerialUtils

@Composable
fun PaperFullView(navController: NavHostController, paperEntity: MutableState<Entry>) {
    Scaffold(
        topBar = {
            BackTopAppBar(
                title = "Full Paper",
                onBackClick = {
                    navController.navigateUp()
                }
            )
        },
        floatingActionButton = {
            FullPaperFAB(paperEntity = paperEntity.value)
        }
    ) {
        PaperFullViewContent(
            modifier = Modifier.padding(it),
            paperEntity = paperEntity.value
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PaperFullViewContent(modifier: Modifier, paperEntity: Entry) {
    Surface(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    KatexView(
                        text = paperEntity.title,
                        textColor = MaterialTheme.colorScheme.onSurface.toArgb(),
                        backgroundColor = MaterialTheme.colorScheme.surface.toArgb(),
                        textSize = 35
                    )
                    //Text(
                      //  text = paperEntity.title,
                        //style = MaterialTheme.typography.headlineSmall,
                       // textAlign = TextAlign.Start
                    //)
                }
                DividerView(modifier = Modifier.align(Alignment.CenterHorizontally))

                Row(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    Text(
                        text = "Last Updated",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = paperEntity.updated,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                DividerView(modifier = Modifier.align(Alignment.CenterHorizontally))

                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Authors")
                    FlowRow(
                        modifier = Modifier.padding(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(0.dp)
                    ) {
                        if (paperEntity.author.isNotEmpty()) {
                            paperEntity.author.forEach {
                                ElevatedAssistChip(
                                    onClick = {},
                                    label = {
                                        Text(
                                            text = it.name.authorName,
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Filled.Person,
                                            contentDescription = "Author"
                                        )
                                    }
                                )
                            }
                        }
                    }
                }

                DividerView(modifier = Modifier.align(Alignment.CenterHorizontally))

                Column(
                    modifier = Modifier.padding(10.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "Summary",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    KatexView(
                        text = paperEntity.summary.replace("\n", " ").replace("\r", " "),
                        textColor = MaterialTheme.colorScheme.onSurface.toArgb(),
                        backgroundColor = MaterialTheme.colorScheme.surface.toArgb(),
                        textSize = 17
                    )
                }
                DividerView(modifier = Modifier.align(Alignment.CenterHorizontally))

                Row(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "Comments",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = paperEntity.comment?: "No Comments",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            
        }
    }
}


@Composable
fun FullPaperFAB(paperEntity: Entry) {

    val uriHandler = LocalUriHandler.current

    var pdfLink :String? = null
    paperEntity.link.forEach{
        if (it.type == "application/pdf") {
            pdfLink = it.href!!
        }
    }
    
    FloatingActionButton(
        modifier = Modifier
            .padding(end = 20.dp, bottom = 25.dp),
        onClick = {
            uriHandler.openUri(pdfLink?: "about:blank")
        }
    ) {
        Row(
            modifier = Modifier.padding(start = 10.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Icon(
                imageVector = Icons.Outlined.Download,
                contentDescription = "Download Paper"
            )
            Text(
                text = "Download Paper"
            )
        }
    }
}


@Composable
fun DividerView(modifier: Modifier) {
    Box(modifier = modifier.wrapContentSize()) {
        Divider(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .align(Alignment.Center),
            thickness = 2.dp
        )
    }
}

@Composable
@Preview
fun PaperFullViewContentPreview() {
    PaperFullViewContent(
        modifier = Modifier,
        paperEntity = EmptySerialUtils.EmptyEntry
    )
}
