package com.damiankwasniak.data.di

import com.damiankwasniak.data.AppPrefs
import com.damiankwasniak.data.realm.RealmProvider
import com.damiankwasniak.data.repository.AuthorizationRepositoryImpl
import com.damiankwasniak.data.repository.PhotosRepositoryImpl
import com.damiankwasniak.data.repository.SecretKeyRepositoryImpl
import com.damiankwasniak.domain.repository.AuthorizationRepository
import com.damiankwasniak.domain.repository.PhotosRepository
import com.damiankwasniak.domain.repository.SecretKeyRepository
import org.koin.dsl.module

val dataModule = module {

    single {
        AppPrefs(get())
    }

    single<AuthorizationRepository> {
        AuthorizationRepositoryImpl(get(), get())
    }

    single<PhotosRepository> {
        PhotosRepositoryImpl(get(), get(), get())
    }

    single<SecretKeyRepository> {
        SecretKeyRepositoryImpl(get())
    }

    single<RealmProvider> {
        RealmProvider(get())
    }
}