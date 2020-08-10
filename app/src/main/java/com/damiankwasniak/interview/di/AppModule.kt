package com.damiankwasniak.interview.di

import com.damiankwasniak.interview.file.DefaultFileManager
import com.damiankwasniak.interview.file.FileManager
import com.damiankwasniak.interview.permission.AndroidPermissionsManager
import com.damiankwasniak.interview.permission.PermissionsManager
import com.damiankwasniak.interview.provider.AndroidResourcesProvider
import com.damiankwasniak.interview.provider.ResourcesProvider
import org.koin.dsl.module

val appModule = module {

    single<ResourcesProvider> {
        AndroidResourcesProvider(get())
    }

    single<PermissionsManager> {
        AndroidPermissionsManager(get())
    }

    single {
        DefaultFileManager(get()) as FileManager
    }
}