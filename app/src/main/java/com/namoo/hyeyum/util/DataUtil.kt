package com.namoo.hyeyum.util

import android.content.Context
import com.namoo.hyeyum.App.Companion.context

object DataUtil
{
    const val SETTINGS = "Settings" //설정

    fun set(name: String, location: String, data: Any)
    {
        val sharedPreference = context.getSharedPreferences(location, Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()

        when (data)
        {
            is String -> editor.putString(name, data)
            is Int -> editor.putInt(name, data)
            is Boolean -> editor.putBoolean(name, data)
        }

        editor.apply()
    }
    fun get(name: String, location: String, defaultValue: Any): Any
    {
        return when (defaultValue)
        {
            is String -> context.getSharedPreferences(location, Context.MODE_PRIVATE).getString(name, defaultValue) ?: ""
            is Int -> context.getSharedPreferences(location, Context.MODE_PRIVATE).getInt(name, defaultValue)
            is Boolean -> context.getSharedPreferences(location, Context.MODE_PRIVATE).getBoolean(name, defaultValue)
            else -> ""
        }
    }
    fun reset(location: String)
    {
        val sharedPreference = context.getSharedPreferences(location, Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()

        editor.clear()
        editor.apply()
    }
}