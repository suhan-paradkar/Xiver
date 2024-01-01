package com.wizard.xiver.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedAssistChip
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
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
                    Text(
                        text = paperEntity.title,
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Start
                    )
                }
                Divider(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .align(Alignment.CenterHorizontally),
                    thickness = Dp.Hairline
                )
                Column(modifier = Modifier.padding(10.dp)) {
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
                Divider(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .align(Alignment.CenterHorizontally),
                    thickness = Dp.Hairline
                )
                Box(modifier = Modifier.padding(10.dp)) {
                    KatexView(
                        text = paperEntity.summary.replace("\n", " ").replace("\r", " "),
                        textColor = MaterialTheme.colorScheme.onSurface.toArgb(),
                        backgroundColor = MaterialTheme.colorScheme.surface.toArgb()
                    )
                }
            }
        }
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
