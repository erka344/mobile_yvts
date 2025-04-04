package com.example.yuvts.data.repository

import com.example.yuvts.data.local.WordDao
import com.example.yuvts.domain.Word
import kotlinx.coroutines.flow.Flow

class WordRepository(private val wordDao: WordDao) {
    val allWords: Flow<List<Word>> = wordDao.getAllWords()

    suspend fun insert(word: Word) {
        wordDao.insertWord(word)
    }

    suspend fun delete(word: Word) {
        wordDao.deleteWord(word)
    }

    suspend fun update(word: Word) {
        wordDao.updateWord(word)
    }

    suspend fun getWordById(id: Int): Word? {
        return wordDao.getWordById(id)
    }

    suspend fun getIdByWord(word: Word): Int? {
        return wordDao.getIdByWord(word.english, word.mongolia)
    }

}
