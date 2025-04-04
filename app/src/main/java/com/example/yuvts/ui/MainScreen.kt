@file:OptIn(ExperimentalFoundationApi::class)

package com.example.yuvts.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.yuvts.viewModel.WordViewModel


@Composable
fun mainScreen(
    modifier: Modifier,
    viewModel: WordViewModel,
    onAddBottomClicked: () -> Unit,
    onEditBottomClicked: (Int) -> Unit,
) {
    val words by viewModel.allWords.collectAsState(initial = emptyList())
    val selectedLanguage by viewModel.selectedLanguage.collectAsState(initial = "Both")
    var currentWordIndex by remember { mutableStateOf(0) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    
    // Handle words list changes
    LaunchedEffect(words.size) {
        if (words.isEmpty()) {
            currentWordIndex = 0
        } else if (currentWordIndex >= words.size) {
            currentWordIndex = words.size - 1
        }
    }
    
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Үг устгах") },
            text = { Text("Та үг устгахдаа итгэлтэй байна уу?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (words.isNotEmpty()) {
                            viewModel.deleteWord(words[currentWordIndex])
                            if (currentWordIndex >= words.size - 1) {
                                currentWordIndex = maxOf(0, words.size - 2)
                            }
                        }
                        showDeleteDialog = false
                    }
                ) {
                    Text("Тийм")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteDialog = false }
                ) {
                    Text("Үгүй")
                }
            }
        )
    }
    
    Column(
        modifier = modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(250.dp, Alignment.Bottom),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (words.isNotEmpty()) {
                // Show English text based on language setting
                if (selectedLanguage == "English" || selectedLanguage == "Both") {
                    Card {
                        Text(
                            text = words[currentWordIndex].english,
                            modifier = Modifier
                                .padding(10.dp)
                                .combinedClickable(
                                    onClick = { },
                                    onLongClick = {
                                        viewModel.getWordById(words[currentWordIndex].id)
                                        onEditBottomClicked(words[currentWordIndex].id)
                                    }
                                )
                                .fillMaxWidth(),
                        )
                    }
                }
                
                // Show Mongolian text based on language setting
                if (selectedLanguage == "Монгол" || selectedLanguage == "Both") {
                    Card {
                        Text(
                            text = words[currentWordIndex].mongolia,
                            modifier = Modifier
                                .padding(10.dp)
                                .combinedClickable(
                                    onClick = { },
                                    onLongClick = {
                                        viewModel.getWordById(words[currentWordIndex].id)
                                        onEditBottomClicked(words[currentWordIndex].id)
                                    }
                                )
                                .fillMaxWidth(),
                        )
                    }
                }
            } else {
                Card {
                    Text(
                        text = "No words available",
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                    )
                }

                Card  {
                    Text(
                        text = "Үг байхгүй байна",
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                    )
                }
            }
        }
        
        Column(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(15.dp, 15.dp, 15.dp, 50.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {

                Button(
                    onClick = onAddBottomClicked,
                    modifier = Modifier.fillMaxWidth(0.33f)
                ) {
                    Text(text = "нэмэх")
                }
                Button(
                    onClick = { 
                        if (words.isNotEmpty()) {
                            viewModel.getWordById(words[currentWordIndex].id)
                            onEditBottomClicked(words[currentWordIndex].id)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(0.5f),
                    enabled = words.isNotEmpty()
                ) {
                    Text(text = "шинэчлэх")
                }
                Button(
                    onClick = { 
                        if (words.isNotEmpty()) {
                            showDeleteDialog = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth(1f),
                    enabled = words.isNotEmpty()
                ) {
                    Text(text = "устгах")
                }
            }
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Button(
                    onClick = { 
                        if (currentWordIndex > 0) {
                            currentWordIndex--
                        }
                    },
                    modifier = Modifier.fillMaxWidth(0.5f),
                    enabled = words.isNotEmpty()
                ) {
                    Text(text = "өмнөх")
                }
                Button(
                    onClick = { 
                        if (currentWordIndex < words.size - 1) {
                            currentWordIndex++
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = words.isNotEmpty()
                ) {
                    Text(text = "дараа")
                }
            }
        }
    }
}


//@Preview
//@Composable
//fun mainScreenPreview() {
//    mainScreen(
//        modifier = Modifier,
//        viewModel = viewModel(
//            factory = WordViewModelFactory(
//                WordRepository(
//                    AppDatabase.getDatabase(LocalContext.current).wordDao()
//                )
//            )
//        ),
//        onAddBottomClicked = {},
//        onEditBottomClicked = { "ewr", "ewr" },
//
//    )
//}