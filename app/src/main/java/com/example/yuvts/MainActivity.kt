package com.example.yuvts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.yuvts.data.local.AppDatabase
import com.example.yuvts.data.local.UserPreferences
import com.example.yuvts.data.repository.WordRepository
import com.example.yuvts.ui.theme.YuvtsTheme
import com.example.yuvts.viewModel.WordViewModel
import com.example.yuvts.viewModel.WordViewModelFactory
import com.example.yuvts.worker.NotificationWorker
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Schedule notification for 1 minute after app close
        scheduleNotification()
        
        setContent {
            YuvtsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val database = AppDatabase.getDatabase(applicationContext)
                    val repository = WordRepository(database.wordDao())
                    val userPreferences = UserPreferences(applicationContext)
                    val viewModel: WordViewModel = viewModel(
                        factory = WordViewModelFactory(repository, userPreferences)
                    )
                    
                    naviScreen(
                        modifier = Modifier,
                        viewModel = viewModel
                    )
                }
            }
        }
    }

    private fun scheduleNotification() {
        val notificationWork = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(24, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(applicationContext)
            .enqueue(notificationWork)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    YuvtsTheme {
        Greeting("Android")
    }
}
