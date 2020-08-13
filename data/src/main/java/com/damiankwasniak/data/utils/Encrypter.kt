package com.damiankwasniak.data.utils

import android.util.Base64
import com.damiankwasniak.data.AppPrefs
import com.damiankwasniak.domain.utils.AESUtils
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec


class Encrypter(
    private val appPrefs: AppPrefs
) {

    var test = byteArrayOf()

    private var isInitialized = false

    init {
        savePasswordHashInDb()
    }

    fun initialize() {
        if (!appPrefs.encrypterInitialize) {
            appPrefs.passHash = 1479587
            isInitialized = true
        }
    }

    fun isCodeCorrect(code: String): Boolean {
        if (!isInitialized) {
            throw Exception("Encrypter not initialized. Call Encrypter.initialize() first")
        }
        return code.hashCode() == appPrefs.passHash
    }

    fun getDecryptedKey(code: String): SecretKey {
        return if (appPrefs.secureKey.isEmpty()) {
            val key = AESUtils.getRandomKey()
            appPrefs.secureKey = "dsdasd"
            appPrefs.secureKey = Base64.encodeToString(key.encoded, Base64.DEFAULT)
            test = AESUtils.encrypt(key.encoded, AESUtils.getKeyFromCode(code))
            key
        } else {
            //val encryptedKey = Base64.decode(appPrefs.secureKey, Base64.DEFAULT)
            val encryptedKey = test
            val decryptedKey = AESUtils.decrypt(encryptedKey, AESUtils.getKeyFromCode(code))
            SecretKeySpec(decryptedKey, 0, decryptedKey.size, "AES")
        }
    }

    private fun savePasswordHashInDb() {

    }
}