package com.example.kasirtoko.reponses

import okhttp3.ResponseBody
import java.io.Reader

data class ErrorResponse(
    @com.example.kasirtoko.models.SerializedName("message")
    val message: String? = null,
    @com.example.kasirtoko.models.SerializedName("success")
    val success: Boolean? = null
) {

    companion object {
        val gson = Gson()

        private fun Gson(): Any {
            TODO("Not yet implemented")
        }

        fun fromErrorBody(body: ResponseBody?) = gson.fromJson(body?.charStream(), ErrorResponse::class.java)
    }
}

private fun Any.fromJson(charStream: Reader?, java: Class<ErrorResponse>): Any {
    TODO("Not yet implemented")
}
