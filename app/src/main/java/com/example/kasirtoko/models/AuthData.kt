package com.example.kasirtoko.models

data class AuthData (
    @SerializedName("admin")
    val admin: Admin? = null,
    @SerializedName("token")
    val token: String? = null
)