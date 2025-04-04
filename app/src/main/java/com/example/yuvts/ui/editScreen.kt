package com.example.yuvts.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.yuvts.viewModel.WordViewModel

@Composable
fun editScreen(
    modifier: Modifier,
    viewModel: WordViewModel,
    onInsertButtonClicked: (String, String) -> Unit,
    onDisposeButtonClicked: () -> Unit,
) {
    val currentWord by viewModel.currentWord.collectAsState()
    var englishText by remember { mutableStateOf(currentWord?.english ?: "") }
    var mongoliaText by remember { mutableStateOf(currentWord?.mongolia ?: "") }
    
    // Update text fields when currentWord changes
    LaunchedEffect(currentWord) {
        englishText = currentWord?.english ?: ""
        mongoliaText = currentWord?.mongolia ?: ""
    }
    
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = englishText,
            onValueChange = { englishText = it },
            label = { Text("English Word") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
        )

        OutlinedTextField(
            value = mongoliaText,
            onValueChange = { mongoliaText = it },
            label = { Text("Монгол үг") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    if (englishText.isNotBlank() && mongoliaText.isNotBlank()) {
                        if (currentWord != null) {
                            // Update existing word
                            viewModel.updateWord(currentWord!!.copy(
                                english = englishText,
                                mongolia = mongoliaText
                            ))
                            onDisposeButtonClicked()
                        } else {
                            // Insert new word
                            onInsertButtonClicked(englishText, mongoliaText)
                            onDisposeButtonClicked()
                        }
                    }
                },
                modifier = Modifier.weight(1f),
                enabled = englishText.isNotBlank() && mongoliaText.isNotBlank()
            ) {
                Text(if (currentWord != null) "Update" else "Save")
            }

            Button(
                onClick = onDisposeButtonClicked,
                modifier = Modifier.weight(1f)
            ) {
                Text("Cancel")
            }
        }
    }
} 