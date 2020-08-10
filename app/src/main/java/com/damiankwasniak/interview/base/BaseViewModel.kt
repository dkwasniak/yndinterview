package com.damiankwasniak.interview.base

import androidx.lifecycle.ViewModel
import com.damiankwasniak.interview.provider.ResourcesProvider
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.Job

open class BaseViewModel<T : Any>(
    private val resourcesProvider: ResourcesProvider
) : ViewModel() {

    var viewStateObserver: ((event: T) -> Unit)? = null

    var baseViewStateObserver: ((command: BaseViewCommand) -> Unit)? = null

    open fun dispose() {
    }

    fun observeViewState(onNextEvent: (event: T) -> Unit) {
        viewStateObserver = onNextEvent
    }

    open fun onResume() {

    }

    open fun onPause() {

    }

    fun observeBaseViewState(onNextEvent: (event: BaseViewCommand) -> Unit) {
        this.baseViewStateObserver = onNextEvent
    }

    fun applyCommand(command: T) {
        this.viewStateObserver?.invoke(command)
    }

    fun T.apply() = viewStateObserver?.invoke(this)

    fun applyCommands(vararg commands: T) {
        commands.forEach {
            this.viewStateObserver?.invoke(it)
        }
    }

    open fun showError(throwable: Throwable) {
    }

    protected fun string(resId: Int) = resourcesProvider.getString(resId)

    sealed class BaseViewCommand {

    }
}

