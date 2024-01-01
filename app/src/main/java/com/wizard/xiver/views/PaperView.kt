package com.wizard.xiver.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkRemove
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.wizard.xiver.Entry
import com.wizard.xiver.utils.EmptySerialUtils
import com.wizard.xiver.utils.SavedUtils

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PaperView(
    paperEntity: Entry,
    onBookmarkClick: () -> Unit,
    paperMutableArgument: MutableState<Entry>,
    mainNavController: NavHostController
) {
    ElevatedCard(
        modifier = Modifier.padding(bottom = 7.dp),
        shape = RoundedCornerShape(5),
        onClick = {
            paperMutableArgument.value = paperEntity
            mainNavController.navigate("PaperFullView")
        }
    ) {
        Column(
            modifier = Modifier.wrapContentHeight(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
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
                FlowRow(
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
                    style = MaterialTheme.typography.bodyMedium
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
            val context = LocalContext.current

            LaunchedEffect(true) {
                paperSaved.value = SavedUtils.isPaperSaved(paperEntity, context)
            }

            IconButton(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .wrapContentSize()
                    .padding(end = 10.dp),
                enabled = true,
                onClick = {
                    onBookmarkClick()
                    paperSaved.value = SavedUtils.isPaperSaved(paperEntity, context)
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
            IconButton(
                modifier = Modifier
                    .wrapContentHeight()
                    .align(Alignment.CenterEnd)
                    .padding(end = 60.dp),
                onClick = {}
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

@Preview
@Composable
fun PaperViewPreview() {
    PaperView(
        paperEntity = EmptySerialUtils.EmptyEntry,
        onBookmarkClick = {},
        paperMutableArgument = remember {
            mutableStateOf(EmptySerialUtils.EmptyEntry)
        },
        mainNavController = rememberNavController()
    )
}