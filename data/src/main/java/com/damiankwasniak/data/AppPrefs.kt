package com.damiankwasniak.data

import android.content.Context

class AppPrefs(ctx: Context) {

    private val prefs = ctx.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    private val editor = prefs.edit()

    var pinCode: String?
        get() = prefs.getString(KEY_PIN_CODE, "1111")
        set(value) = editor.putString(KEY_PIN_CODE, value).apply()

    var lock: Boolean
        get() = prefs.getBoolean(KEY_LOCK, true)
        set(value) = editor.putBoolean(KEY_LOCK, value).apply()

    fun setFirstAsking(permission: String, value: Boolean = true) {
        editor.putBoolean(permission, value).commit()
    }

    fun isFirstAsking(permission: String): Boolean = prefs.getBoolean(permission, true)

    companion object {
        private const val KEY_PIN_CODE = "key_pin_code"
        private const val KEY_LOCK = "key_lock"
    }
}