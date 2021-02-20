package com.aldidwiki.myquizapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aldidwiki.myquizapp.data.source.local.entity.QuestionEntity
import com.aldidwiki.myquizapp.data.source.local.entity.UserEntity

@Database(entities = [QuestionEntity::class, UserEntity::class], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun localService(): LocalService

    companion object {
        const val DB_NAME = "quiz_db"
    }
}