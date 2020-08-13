package com.damiankwasniak.data.repository

import com.damiankwasniak.data.realm.RealmProvider
import com.damiankwasniak.domain.repository.SecretKeyRepository
import com.damiankwasniak.domain.utils.AsyncResult
import com.damiankwasniak.extensions.secretKeyDao

class SecretKeyRepositoryImpl(
    private val realm: RealmProvider
): SecretKeyRepository {

    override fun storeSecretKey(key: ByteArray): AsyncResult<Boolean> {
        return realm.secretKeyDao().saveSecretKey(key)
    }

    override fun getSecretKey(): AsyncResult<ByteArray> {
        return realm.secretKeyDao().getKey()
    }
}