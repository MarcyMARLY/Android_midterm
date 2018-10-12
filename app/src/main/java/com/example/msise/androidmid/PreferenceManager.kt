package com.example.msise.androidmid

import android.content.Context
import android.content.SharedPreferences

private const val LOGIN_DETAILS = "login_details"
private const val EMAIL = "email"
private const val PASSWORD = "password"

class PreferenceManager(
        private val context: Context
) {
    private val preferenceContext: Context = context

    fun saveLoginDetails(email: String, password: String) {
        val sharedPreferences: SharedPreferences = preferenceContext.getSharedPreferences(LOGIN_DETAILS, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(EMAIL, email)
        editor.putString(PASSWORD, password)
        editor.commit()
    }

    fun isUserLogOut(): Boolean {
        val sharedPreferences: SharedPreferences = preferenceContext.getSharedPreferences(LOGIN_DETAILS, Context.MODE_PRIVATE)
        val isEmail: Boolean = sharedPreferences.getString(EMAIL, "").isEmpty()
        val isPassword: Boolean = sharedPreferences.getString(PASSWORD, "").isEmpty()

        return isEmail || isPassword
    }

    fun logout() {
        val sharedPreferences: SharedPreferences = preferenceContext.getSharedPreferences(LOGIN_DETAILS, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.remove(LOGIN_DETAILS)
        editor.commit()
    }
}