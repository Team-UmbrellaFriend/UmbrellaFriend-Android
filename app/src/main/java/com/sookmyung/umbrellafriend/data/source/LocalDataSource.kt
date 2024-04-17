package com.sookmyung.umbrellafriend.data.source

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val prefs: SharedPreferences
) {
    var token: String
        set(value) = prefs.edit { putString(TOKEN, value) }
        get() = prefs.getString(TOKEN, "") ?: ""

    var login: Boolean
        set(value) = prefs.edit { putBoolean(LOGIN, value) }
        get() = prefs.getBoolean(LOGIN, false)

    companion object {
        private const val TOKEN = "token"
        private const val LOGIN = "login"
    }
}
