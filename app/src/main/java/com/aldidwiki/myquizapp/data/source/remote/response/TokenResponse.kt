package com.aldidwiki.myquizapp.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TokenResponse(

        @field:SerializedName("response_code")
        val responseCode: Int,

        @field:SerializedName("response_message")
        val responseMessage: String,

        @field:SerializedName("token")
        val token: String
)
