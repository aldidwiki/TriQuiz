package com.aldidwiki.myquizapp.data.source.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aldidwiki.myquizapp.data.source.local.entity.QuestionEntity
import com.aldidwiki.myquizapp.data.source.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalService {
    @Query("SELECT * FROM question_entity")
    fun getTempQuestion(): Flow<List<QuestionEntity>>

    @Query("SELECT * FROM user_entity ORDER BY totalScore DESC")
    fun getUsers(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: QuestionEntity)

    @Query("DELETE FROM question_entity")
    suspend fun clearQuestionEntity()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("DELETE FROM user_entity")
    suspend fun clearLeaderBoards()
}