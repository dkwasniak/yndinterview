package com.damiankwasniak.domain.repository

import com.damiankwasniak.domain.utils.AsyncResult

interface SecretKeyRepository {

    fun storeSecretKey(key: ByteArray): AsyncResult<Boolean>

    fun getSecretKey(): AsyncResult<ByteArray>
}