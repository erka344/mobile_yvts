package com.example.yuvts.data.local

import androidx.room.*
import com.example.yuvts.domain.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: Word) // ☑ `Word` class-г параметр болгон авсан эсэхийг шалга

    @Query("SELECT * FROM words")
    fun getAllWords(): Flow<List<Word>>

    @Delete
    suspend fun deleteWord(word: Word) // ☑ `Word` class байх ёстой

    @Query("SELECT * FROM words WHERE id = :wordId")
    suspend fun getWordById(wordId: Int): Word?

    @Query("SELECT id FROM words WHERE english = :wordEnglish AND mongolia = :wordMongolia")
    suspend fun getIdByWord(wordEnglish: String, wordMongolia: String): Int?


    @Update
    suspend fun updateWord(word: Word)
}


