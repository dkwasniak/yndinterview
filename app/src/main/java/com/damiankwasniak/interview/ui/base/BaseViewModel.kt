package com.damiankwasniak.interview.ui.base

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.ViewModel
import com.damiankwasniak.interview.provider.ResourcesProvider

open class BaseViewModel<T : Any>(
    private val resourcesProvider: ResourcesProvider
) : ViewModel(), Observable {

    private val callbacks: PropertyChangeRegistry = PropertyChangeRegistry()

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

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

    fun notifyChange() {
        callbacks.notifyCallbacks(this, 0, null)
    }

    fun notifyPropertyChanged(fieldId: Int) {
        callbacks.notifyCallbacks(this, fieldId, null)
    }

    sealed class BaseViewCommand {

    }
}

