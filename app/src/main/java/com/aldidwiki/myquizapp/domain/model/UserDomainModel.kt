package com.aldidwiki.myquizapp.domain.model

import android.os.Parcelable
import com.aldidwiki.myquizapp.data.source.local.entity.UserEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDomainModel(
        val id: Int,
        val name: String,
        val totalAnswered: Int,
        val totalScore: Int,
        val totalCorrect: Int,
        val totalIncorrect: Int
) : Parcelable {
    fun toEntity() = UserEntity(
            id = id,
            name = name,
            totalAnswered = totalAnswered,
            totalScore = totalScore,
            totalCorrect = totalCorrect,
            totalIncorrect = totalIncorrect
    )
}
