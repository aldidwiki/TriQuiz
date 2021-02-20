package com.aldidwiki.myquizapp.data.source.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user_entity")
@Parcelize
data class UserEntity(
        @PrimaryKey(autoGenerate = true) val id: Int,
        val name: String,
        val totalAnswered: Int,
        val totalScore: Int,
        val totalCorrect: Int,
        val totalIncorrect: Int,
        val sessionToken: String
) : Parcelable
