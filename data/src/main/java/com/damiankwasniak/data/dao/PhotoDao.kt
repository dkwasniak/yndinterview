package com.damiankwasniak.data.dao

import com.damiankwasniak.data.model.PhotoDbModel
import com.damiankwasniak.data.realm.RealmProvider
import com.damiankwasniak.domain.utils.AsyncResult
import com.damiankwasniak.extensions.asAsyncResult
import io.realm.Realm
import io.realm.RealmResults
import java.lang.Exception

class PhotoDao(val realm: RealmProvider) {

    fun savePhoto(model: PhotoDbModel): AsyncResult<Boolean> {
        var asyncResult: AsyncResult<Boolean> = AsyncResult.Error(Exception("Unable to save photo"))
        realm.getRealm().executeTransaction {
            it.insert(model)
            asyncResult = AsyncResult.Success(false)
        }
        return asyncResult
    }

    fun getPhotos(): AsyncResult<MutableList<PhotoDbModel>> {
        val result = realm.getRealm().where(PhotoDbModel::class.java).findAll()
        return AsyncResult.Success(realm.getRealm().copyFromRealm(result))
    }
}