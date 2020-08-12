package com.damiankwasniak.domain.utils

import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

const val SALT = "sdokfpawijefqaewkf923r283urqiewlkfw"

object AESUtils {

    fun getKeyFromCode(code: String): SecretKey {
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1", "BC")
        val spec = PBEKeySpec(code.toCharArray(), SALT.toByteArray(), 1024, 256)
        val tmp = factory.generateSecret(spec)
        val secret = SecretKeySpec(tmp.getEncoded(), "AES")
        val key = String(secret.encoded)
        val lenght = secret.encoded.size.toString()
        return secret
    }

    fun getRandomKey(): SecretKey {
        val keyGenerator: KeyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(256)
        val secretKey: SecretKey = keyGenerator.generateKey()
        return secretKey
    }

    fun encrypt(plaintext: ByteArray, key: SecretKey): ByteArray {
        val cipher = Cipher.getInstance("AES")
        val keySpec = SecretKeySpec(key.encoded, "AES")
        cipher.init(Cipher.ENCRYPT_MODE, keySpec)
        return cipher.doFinal(plaintext)
    }

    fun decrypt(cipherText: ByteArray, key: SecretKey): ByteArray {
        val cipher = Cipher.getInstance("AES")
        val keySpec = SecretKeySpec(key.encoded, "AES")
        cipher.init(Cipher.DECRYPT_MODE, keySpec)
        return cipher.doFinal(cipherText)
    }
}