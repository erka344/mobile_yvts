package com.example.yuvts

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.yuvts.domain.Word
import com.example.yuvts.ui.SettingsScreen
import com.example.yuvts.ui.editScreen
import com.example.yuvts.ui.mainScreen
import com.example.yuvts.viewModel.WordViewModel

enum class Screens(@StringRes val title: Int) {
    Main(title = R.string.main_screen),
    editWords(title = R.string.edit_words_screen),
    settings(title = R.string.settings_screen),
}

@Composable
fun naviScreen(
    modifier: Modifier,
    viewModel: WordViewModel
) {
    val navController = rememberNavController()

    Scaffold (
        topBar = {
            TopBar(
                modifier = Modifier,
                navController = navController
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screens.Main.name,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(route = Screens.Main.name) {
                mainScreen(
                    viewModel = viewModel,
                    onAddBottomClicked = {
                        viewModel.getWordById(-1)
                        navController.navigate(Screens.editWords.name)
                    },
                    onEditBottomClicked = {
                        navController.navigate(Screens.editWords.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                )
            }

            composable(route = Screens.editWords.name) {
                editScreen(
                    viewModel = viewModel,
                    onInsertButtonClicked = { english, mongolia ->
                        viewModel.insertWord(english, mongolia)
                        navController.navigateUp()
                    },
                    onDisposeButtonClicked = {
                        navController.navigateUp()
                    },
                    modifier = Modifier
                        .fillMaxSize()
                )
            }

            composable(route = Screens.settings.name) {
                var selectedLanguage by remember { mutableStateOf("Both") }

                SettingsScreen(
                    viewModel = viewModel,
                    onSelectionChanged = { selection ->
                        selectedLanguage = when (selection) {
                            "гадаад үгийг ил харуулна" -> "English"
                            "монгол үгийг ил харуулна" -> "Монгол"
                            "хоёуланг нь үгийг ил харуулна" -> "Both"
                            else -> "Both"
                        }
                    },
                    onBackButtonClicked = {
                        navController.navigateUp()
                    },
                    onSaveButtonClicked = {
                        viewModel.updateLanguage(selectedLanguage)
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier,
    navController: NavController
){
    CenterAlignedTopAppBar(
        title = { Text(text = "Kартын апп") },
        actions = {
            IconButton(
                onClick = {
                    navController.navigate(Screens.settings.name)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "settings"
                )
            }
        }

    )
}