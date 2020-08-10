package com.damiankwasniak.domain.di

import com.damiankwasniak.domain.interactor.AuthInteractor
import com.damiankwasniak.domain.interactor.PhotosInteractor
import org.koin.dsl.module

val domainModule = module {

    single<AuthInteractor> {
        AuthInteractor(get())
    }

    single {
        PhotosInteractor(get())
    }
}