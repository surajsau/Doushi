package com.halfplatepoha.doushi

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

const val PREF_LANGUAGE = "pref_lang"
const val PREF_LOCALE = "pref_locale"
const val PREF_MODE = "pref_mode"

class DoushiPref(context: Context) {

    private var sharedpreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun setInPref(key: String, value: Boolean) {
        val editor = sharedpreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun setInPref(key: String, value: String) {
        val editor = sharedpreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }


    fun setInPref(key: String, value: Float) {
        val editor = sharedpreferences.edit()
        editor.putFloat(key, value)
        editor.apply()
    }

    fun setInPref(key: String, value: Int) {
        val editor = sharedpreferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getFromPref(key: String, defaultValue: String): String {
        return sharedpreferences.getString(key, defaultValue)?:""
    }

    fun getBooleanFromPref(key: String, defaultValue: Boolean?): Boolean {
        return sharedpreferences.getBoolean(key, defaultValue!!)
    }

    fun getLongFromPref(key: String, defaultValue: Long): Long {
        return sharedpreferences.getLong(key, defaultValue)
    }

    fun getIntFromPref(key: String, defaultValue: Int): Int {
        return sharedpreferences.getInt(key, defaultValue)
    }

    fun removeFromPref(key: String) {
        sharedpreferences.edit().remove(key).apply()
    }

}