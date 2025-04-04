package com.example.yuvts.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.yuvts.data.local.UserPreferences
import com.example.yuvts.data.repository.WordRepository
import com.example.yuvts.domain.Word
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WordViewModel(
    private val repository: WordRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {
    val allWords: Flow<List<Word>> = repository.allWords
    
    private val _currentWord = MutableStateFlow<Word?>(null)
    val currentWord: StateFlow<Word?> = _currentWord

    // Settings state
    val selectedLanguage: Flow<String> = userPreferences.selectedLanguage
    val notificationEnabled: Flow<Boolean> = userPreferences.notificationEnabled
    val notificationInterval: Flow<String> = userPreferences.notificationInterval

    private val _currentWordIndex = MutableStateFlow(0)
    val currentWordIndex: StateFlow<Int> = _currentWordIndex

    fun insertWord(english: String, mongolia: String) {
        viewModelScope.launch {
            repository.insert(Word(english = english, mongolia = mongolia))
        }
    }

    fun deleteWord(word: Word) {
        viewModelScope.launch {
            repository.delete(word)
        }
    }

    fun updateWord(word: Word) {
        viewModelScope.launch {
            repository.update(word)
        }
    }

    fun getWordById(id: Int) {
        viewModelScope.launch {
            _currentWord.value = repository.getWordById(id)
        }
    }

    fun getIdByWord(word: Word?): Int {
        return word?.id ?: -1
    }

    // Settings functions
    fun updateLanguage(language: String) {
        viewModelScope.launch {
            userPreferences.updateSelectedLanguage(language)
        }
    }

    fun updateNotificationEnabled(enabled: Boolean) {
        viewModelScope.launch {
            userPreferences.updateNotificationEnabled(enabled)
        }
    }

    fun updateNotificationInterval(interval: String) {
        viewModelScope.launch {
            userPreferences.updateNotificationInterval(interval)
        }
    }
}

class WordViewModelFactory(
    private val repository: WordRepository,
    private val userPreferences: UserPreferences
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(repository, userPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
} 