package com.damiankwasniak.data.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

const val SECRET_KEY_ID = "SecretKeyId"

open class SecretKeyDbModel(
    @PrimaryKey
    var id: String = SECRET_KEY_ID,
    var byteArray: ByteArray
) : RealmObject() {

    constructor() : this(SECRET_KEY_ID, byteArrayOf())

    constructor(byteArray: ByteArray) : this(SECRET_KEY_ID, byteArray)
}