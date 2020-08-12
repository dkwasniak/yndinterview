package com.damiankwasniak.data.realm

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration

class RealmProvider(val context: Context) {
    private var initialiazed = false

    private val DATABASE_NAME = "ynd.realm"

    private fun init() {
        Realm.init(context)
        val realmConfiguration = RealmConfiguration
            .Builder()
            .name(DATABASE_NAME)
            .schemaVersion(1)
            .build()
        Realm.setDefaultConfiguration(realmConfiguration)
    }

    fun getRealm(): Realm {
        if (!initialiazed) {
            init()
            initialiazed = true
        }
        return Realm.getDefaultInstance()
    }
}