package com.damiankwasniak.data.dao

import com.damiankwasniak.data.model.SecretKeyDbModel
import com.damiankwasniak.data.realm.RealmProvider
import com.damiankwasniak.domain.utils.AsyncResult

class SecretKeyDao(val realm: RealmProvider) {

    fun saveSecretKey(key: ByteArray): AsyncResult<Boolean> {
        var asyncResult: AsyncResult<Boolean> = AsyncResult.Error(Exception("Unable to save key"))
        realm.getRealm().executeTransaction {
            val model = SecretKeyDbModel(key)
            it.insert(model)
            asyncResult = AsyncResult.Success(false)
        }
        return asyncResult
    }

    fun getKey(): AsyncResult<ByteArray> {
        val result = realm.getRealm().where(SecretKeyDbModel::class.java).findFirst()
        return if (result != null) {
            return AsyncResult.Success(realm.getRealm().copyFromRealm(result).byteArray)
        } else {
            AsyncResult.Success(byteArrayOf())
        }
    }
}