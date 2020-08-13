package com.damiankwasniak.interview.di

import com.damiankwasniak.interview.manager.CameraManager
import com.damiankwasniak.data.utils.SessionManager
import com.damiankwasniak.interview.manager.file.DefaultFileManager
import com.damiankwasniak.interview.manager.file.FileManager
import com.damiankwasniak.interview.permission.AndroidPermissionsManager
import com.damiankwasniak.interview.permission.PermissionsManager
import com.damiankwasniak.interview.provider.AndroidResourcesProvider
import com.damiankwasniak.interview.provider.ResourcesProvider
import com.damiankwasniak.interview.utils.GlideImageLoader
import com.damiankwasniak.interview.utils.YndImageLoader
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

    single { CameraManager(get(), get()) }

    single<YndImageLoader> { GlideImageLoader(get()) }

    single { SessionManager() }
}