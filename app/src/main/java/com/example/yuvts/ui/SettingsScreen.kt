package com.example.yuvts.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.yuvts.viewModel.WordViewModel

@Composable
fun SettingsScreen(
    viewModel: WordViewModel,
    onBackButtonClicked: () -> Unit,
    onSaveButtonClicked: () -> Unit,
    onSelectionChanged: (String) -> Unit,
) {

    val currentLanguage by viewModel.selectedLanguage.collectAsState(initial = "Both")
    
    val initialOption = when (currentLanguage) {
        "English" -> "гадаад үгийг ил харуулна"
        "Монгол" -> "монгол үгийг ил харуулна"
        "Both" -> "хоёуланг нь үгийг ил харуулна"
        else -> "хоёуланг нь үгийг ил харуулна"
    }
    
    var selectedOption by remember { mutableStateOf(initialOption) }
    
    // Update selectedOption when currentLanguage changes
    LaunchedEffect(currentLanguage) {
        selectedOption = when (currentLanguage) {
            "English" -> "гадаад үгийг ил харуулна"
            "Монгол" -> "монгол үгийг ил харуулна"
            "Both" -> "хоёуланг нь үгийг ил харуулна"
            else -> "хоёуланг нь үгийг ил харуулна"
        }
    }
    
    Column {
        listOf(
            "гадаад үгийг ил харуулна",
            "монгол үгийг ил харуулна",
            "хоёуланг нь үгийг ил харуулна"
        ).forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        selectedOption = option
                        onSelectionChanged(option)
                    }
                    .padding(8.dp)
            ) {
                RadioButton(
                    selected = selectedOption == option,
                    onClick = {
                        selectedOption = option
                        onSelectionChanged(option)
                    }
                )
                Text( text = option)
            }
        }

        Row (
            modifier = Modifier
                .padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ){
            Button(
                onClick = onBackButtonClicked,
                modifier = Modifier.fillMaxWidth(0.5f)
            ) {
                Text(text = "Буцах")
            }
            
            Button(
                onClick = onSaveButtonClicked,
                modifier = Modifier.fillMaxWidth(1f)
            ) {
                Text(text = "хадгалах")
            }
        }
    }
}

//@Preview
//@Composable
//fun SettingsScreenPreview() {
//    SettingsScreen(
//        viewModel = WordViewModel(WordRepository(AppDatabase.getDatabase(LocalContext.current).wordDao())),
//        onBackButtonClicked = {},
//        onSelectionChanged = {},
//        onSaveButtonClicked = {}
//    )
//}

