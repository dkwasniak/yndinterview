package com.damiankwasniak.interview

import android.app.Application
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraXConfig
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.damiankwasniak.data.di.dataModule
import com.damiankwasniak.data.utils.SessionManager
import com.damiankwasniak.domain.di.domainModule
import com.damiankwasniak.interview.di.appModule
import com.damiankwasniak.interview.di.viewModelModule
import com.facebook.stetho.Stetho
import com.uphyca.stetho_realm.RealmInspectorModulesProvider
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class InterviewApplication : Application(), CameraXConfig.Provider, LifecycleObserver {

    private val sessionManager: SessionManager by inject()

    override fun onCreate() {
        super.onCreate()
        initKoin()
        initStetho()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
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

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onMoveToBackground() {
        sessionManager.clearSession()
    }
}

