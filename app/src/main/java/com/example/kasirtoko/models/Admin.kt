package com.example.kasirtoko.models

data class Admin(
@SerializedName("email")
val email: String? = null,
@SerializedName("id")
val id: Int? = null,
@SerializedName("nama")
val nama: String? = null
)