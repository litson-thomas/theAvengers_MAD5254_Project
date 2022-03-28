package com.example.theavengers_mad5254_project.utils

import android.content.Context
import android.content.SharedPreferences

object AppPreference {
    private const val NAME = "SnowApp"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    private val IS_LOGIN = Pair("isLogin",false)
    private val USER_TOKEN = Pair("userToken","")
    private val USER_UID = Pair("userID","")

    fun init(context: Context){
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var isLogin: Boolean
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getBoolean(IS_LOGIN.first, IS_LOGIN.second)

        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putBoolean(IS_LOGIN.first,value)
        }

    var userToken: String
        get() = preferences.getString(USER_TOKEN.first, USER_TOKEN.second).toString()

        set(value) = preferences.edit {
            it.putString(USER_TOKEN.first, value)
        }

    var userID: String
        get() = preferences.getString(USER_UID.first, USER_UID.second).toString()

        set(value) = preferences.edit {
            it.putString(USER_UID.first, value)
        }
}