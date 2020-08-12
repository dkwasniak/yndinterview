package com.damiankwasniak.interview.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry


abstract class BaseActivity<T : BaseViewModel<U>, U : Any> : AppCompatActivity(), LifecycleOwner {

    val lifecycleRegistry: LifecycleRegistry by lazy {
        LifecycleRegistry(this)
    }

    abstract fun provideLayoutRes(): Int

    abstract fun getViewModel(): BaseViewModel<U>

    abstract fun onViewStateChanged(command: U)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(provideLayoutRes())
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
        registerViewStateObserver()
        registerGeneralViewStateObserver()
    }


    private fun registerGeneralViewStateObserver() {
        getViewModel().observeBaseViewState {
            onBaseViewStateChanged(it)
        }
    }

    private fun registerViewStateObserver() {
        getViewModel().observeViewState {
            onViewStateChanged(it)
        }
    }

    open fun onBaseViewStateChanged(command: BaseViewModel.BaseViewCommand) {

    }

    override fun onStart() {
        super.onStart()
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }


    override fun onResume() {
        super.onResume()
        getViewModel().onResume()
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun onPause() {
        super.onPause()
        getViewModel().onPause()
        getViewModel().dispose()
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    }

    override fun onStop() {
        super.onStop()
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
    }
}
