package com.damiankwasniak.data.dao

import com.damiankwasniak.data.model.PhotoDbModel
import com.damiankwasniak.domain.utils.AsyncResult
import com.damiankwasniak.extensions.asAsyncResult
import io.realm.Realm
import io.realm.RealmResults
import java.lang.Exception

class PhotoDao(val realm: Realm) {

    fun savePhoto(byteArray: ByteArray): AsyncResult<Boolean> {
        var asyncResult: AsyncResult<Boolean> = AsyncResult.Error(Exception("Unable to save photo"))
        realm.executeTransaction {
            val photo = PhotoDbModel(byteArray)
            it.insert(photo)
            asyncResult = AsyncResult.Success(false)
        }
        return asyncResult
    }

    fun getPhotos(): AsyncResult<RealmResults<PhotoDbModel>> {
        return realm.where(PhotoDbModel::class.java).findAllAsync().asAsyncResult()
    }
}