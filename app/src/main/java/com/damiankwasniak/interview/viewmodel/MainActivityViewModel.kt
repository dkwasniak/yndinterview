package com.damiankwasniak.interview.viewmodel

import androidx.lifecycle.viewModelScope
import com.damiankwasniak.domain.interactor.AuthInteractor
import com.damiankwasniak.interview.base.BaseViewModel
import com.damiankwasniak.interview.provider.ResourcesProvider
import kotlinx.coroutines.launch


class MainActivityViewModel(
    private val authInteractor: AuthInteractor,
    resourcesProvider: ResourcesProvider
) : BaseViewModel<MainActivityViewModel.Command>(resourcesProvider) {


    override fun onPause() {
        super.onPause()
        viewModelScope.launch {
            authInteractor.lock()
        }
    }

    override fun onResume() {
        super.onResume()
        Command.AppResumed.apply()
    }


    sealed class Command {
        object AppResumed : Command()
    }

}
