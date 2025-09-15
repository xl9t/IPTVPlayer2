package com.example.iptvplayer2.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "BumIPTV_Pref"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
        private const val KEY_SERVER_URL = "serverUrl"
        private const val KEY_USERNAME = "username"
        private const val KEY_PASSWORD = "password"
    }

    fun saveLogin(serverUrl: String, username: String, password: String) {
        val editor = prefs.edit()
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.putString(KEY_SERVER_URL, serverUrl)
        editor.putString(KEY_USERNAME, username)
        editor.putString(KEY_PASSWORD, password)
        editor.apply()
    }
    
    fun getServerUrl(): String? {
        return prefs.getString(KEY_SERVER_URL, null)
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun getUsername(): String? {
        return prefs.getString(KEY_USERNAME, null)
    }
    
    fun getPassword(): String? {
        return prefs.getString(KEY_PASSWORD, null)
    }

    fun clearSession() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }
}
