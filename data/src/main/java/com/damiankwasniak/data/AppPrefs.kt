package com.damiankwasniak.data

import android.content.Context
import com.damiankwasniak.EMPTY_STRING

class AppPrefs(ctx: Context) {

    private val prefs = ctx.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    private val editor = prefs.edit()

    var encrypterInitialize: Boolean
        get() = prefs.getBoolean(KEY_ENCRYPTER, false)
        set(value) = editor.putBoolean(KEY_ENCRYPTER, value).apply()

    var secureKey: String
        get() = prefs.getString(KEY_SECURE_KEY, EMPTY_STRING)
        set(value) = editor.putString(KEY_SECURE_KEY, value).apply()

    var passHash: Int
        get() = prefs.getInt(KEY_PASS_HASH, 1479587)
        set(value) = editor.putInt(KEY_PASS_HASH, value).apply()

    var lock: Boolean
        get() = prefs.getBoolean(KEY_LOCK, true)
        set(value) = editor.putBoolean(KEY_LOCK, value).apply()

    fun setFirstAsking(permission: String, value: Boolean = true) {
        editor.putBoolean(permission, value).commit()
    }

    fun isFirstAsking(permission: String): Boolean = prefs.getBoolean(permission, true)

    companion object {
        private const val KEY_LOCK = "key_lock"
        private const val KEY_ENCRYPTER = "key_encrypter"
        private const val KEY_PASS_HASH = "key_pass_hash"
        private const val KEY_SECURE_KEY = "key_secure_key"
    }
}