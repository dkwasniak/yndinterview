package com.damiankwasniak.data.model

import io.realm.RealmObject

open class PhotoDbModel(
    var byteArray: ByteArray
): RealmObject() {

    constructor() : this(byteArrayOf())
}