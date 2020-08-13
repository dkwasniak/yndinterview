package com.damiankwasniak.domain.interactor

import com.damiankwasniak.domain.repository.SecretKeyRepository
import com.damiankwasniak.domain.utils.AsyncResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SecretKeyInteractor(
    private val secretKeyRepository: SecretKeyRepository
) {

    suspend fun storeSecretKey(key: ByteArray): AsyncResult<Boolean> {
        return withContext(Dispatchers.IO) {
            secretKeyRepository.storeSecretKey(key)
        }
    }

    suspend fun getSecretKey(): AsyncResult<ByteArray> {
        return withContext(Dispatchers.IO) {
            secretKeyRepository.getSecretKey()
        }
    }
}