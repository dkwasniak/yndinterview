package com.damiankwasniak.data.di

import com.damiankwasniak.data.AppPrefs
import com.damiankwasniak.data.repository.AuthorizationRepositoryImpl
import com.damiankwasniak.data.repository.PhotosRepositoryImpl
import com.damiankwasniak.domain.repository.AuthorizationRepository
import com.damiankwasniak.domain.repository.PhotosRepository
import io.realm.Realm
import org.koin.dsl.module

val dataModule = module {

    single {
        AppPrefs(get())
    }

    single {
        AuthorizationRepositoryImpl(get()) as AuthorizationRepository
    }

    single {
        PhotosRepositoryImpl(get()) as PhotosRepository
    }

    single<Realm> {
        Realm.getDefaultInstance()
    }
}