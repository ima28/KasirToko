package com.example.kasirtoko.reponses

import com.google.gson.annotations.SerializedName

data class SalesResponse<Data> (
    @SerializedName("data")
    val data: Data? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("success")
    val success: Boolean? = null
)