package com.damiankwasniak.extensions

import com.damiankwasniak.data.dao.PhotoDao
import com.damiankwasniak.data.realm.RealmProvider
import com.damiankwasniak.domain.utils.AsyncResult
import io.realm.RealmModel
import io.realm.RealmResults

fun <T : RealmModel> RealmResults<T>.asAsyncResult() = AsyncResult.Success(this)

fun RealmProvider.photoDao(): PhotoDao = PhotoDao(this)
