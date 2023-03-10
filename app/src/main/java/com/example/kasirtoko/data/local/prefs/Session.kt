package com.example.kasirtoko.data.local.prefs

import com.example.kasirtoko.data.constants.AuthConst
import com.example.kasirtoko.models.Admin

class Session(private val sessionHelper: SessionHelper) {

    val token get() = sessionHelper.getString(AuthConst.KEY_AUTH_TOKEN)

    fun isLogin() = !sessionHelper.getString(AuthConst.KEY_AUTH_TOKEN).isNullOrEmpty()

    fun saveUser(admin: Admin, authKey: String?) = sessionHelper.apply {
        save(AuthConst.KEY_ID, admin.id)
        save(AuthConst.KEY_EMAIL, admin.email)
        save(AuthConst.KEY_NAME, admin.nama)
        save(AuthConst.KEY_AUTH_TOKEN, authKey)
    }

    fun getAuthUser() = Admin(
        id = sessionHelper.getInt(AuthConst.KEY_ID),
        email = sessionHelper.getString(AuthConst.KEY_EMAIL),
        nama = sessionHelper.getString(AuthConst.KEY_NAME)
    )

    fun logout() {
        sessionHelper.destroy()
    }

}