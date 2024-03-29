package com.aldidwiki.myquizapp.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aldidwiki.myquizapp.domain.model.UserDomainModel

@Entity(tableName = "user_entity")
data class UserEntity(
        @PrimaryKey(autoGenerate = true) val id: Int,
        val name: String,
        val totalAnswered: Int,
        val totalScore: Int,
        val totalCorrect: Int,
        val totalIncorrect: Int
) {
    fun toDomainModel() = UserDomainModel(
            id = id,
            name = name,
            totalAnswered = totalAnswered,
            totalScore = totalScore,
            totalCorrect = totalCorrect,
            totalIncorrect = totalIncorrect
    )
}
