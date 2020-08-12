package com.damiankwasniak.interview

import android.app.Application
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraXConfig
import com.damiankwasniak.data.di.dataModule
import com.damiankwasniak.data.utils.Encrypter
import com.damiankwasniak.domain.di.domainModule
import com.damiankwasniak.domain.interactor.AuthInteractor
import com.damiankwasniak.interview.di.appModule
import com.damiankwasniak.interview.di.viewModelModule
import com.facebook.stetho.Stetho
import com.uphyca.stetho_realm.RealmInspectorModulesProvider
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class InterviewApplication : Application(), CameraXConfig.Provider {

    private val encrypter: Encrypter by inject()

    override fun onCreate() {
        super.onCreate()
        initKoin()
        initStetho()
        encrypter.initialize()
    }

    private fun initStetho() {
        Stetho.initialize(
            Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                .build()
        )
    }

    override fun getCameraXConfig(): CameraXConfig {
        return Camera2Config.defaultConfig()
    }

    private fun initKoin() {
        val context = this
        startKoin {
            androidContext(context)
            modules(
                appModule,
                viewModelModule,
                dataModule,
                domainModule
            )
        }
    }
}

