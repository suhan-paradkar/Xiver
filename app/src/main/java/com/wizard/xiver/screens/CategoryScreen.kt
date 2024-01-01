package com.wizard.xiver.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wizard.xiver.utils.CategoryUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen() {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center
    ) {
        CategoryUtils.CATEGORIES.forEach { item ->
            if (item.subCategories.isNotEmpty()) {
                val expanded = remember {
                    mutableStateOf(false)
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RectangleShape,
                    onClick = {
                        expanded.value = !expanded.value
                    },
                    colors = CardDefaults.elevatedCardColors()
                ){
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier.align(Alignment.CenterStart)
                        ) {
                            Text(
                                text = item.name,
                                modifier = Modifier.padding(top = 20.dp, bottom = 20.dp, start = 15.dp),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        Box(
                            modifier = Modifier.align(Alignment.CenterEnd)
                                .padding(end = 10.dp)
                        ) {
                            if (!expanded.value) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Expand"
                                )
                            } else {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = Icons.Default.ArrowDropUp,
                                    contentDescription = "Collapse"
                                )
                            }
                        }
                    }
                }
                if (expanded.value) {
                    item.subCategories.forEach {
                        Card (
                            modifier = Modifier.fillMaxWidth(),
                            shape = RectangleShape
                        ) {
                            Text(
                                text = it!!.name,
                                modifier = Modifier.padding(top = 20.dp, bottom = 20.dp, start = 20.dp),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RectangleShape,
                    colors = CardDefaults.elevatedCardColors()
                ) {
                    Text(
                        text = item.name,
                        modifier = Modifier.padding(top = 20.dp, bottom = 20.dp, start = 15.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CategoryScreenPreview() {
    CategoryScreen()
}