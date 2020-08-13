package com.damiankwasniak.domain.di

import com.damiankwasniak.domain.interactor.AuthInteractor
import com.damiankwasniak.domain.interactor.PhotosInteractor
import com.damiankwasniak.domain.interactor.SecretKeyInteractor
import org.koin.dsl.module

val domainModule = module {

    single<AuthInteractor> {
        AuthInteractor(get())
    }

    single {
        PhotosInteractor(get())
    }

    single {
        SecretKeyInteractor(get())
    }
}