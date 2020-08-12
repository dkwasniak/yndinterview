package com.damiankwasniak.interview.ui.code

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.damiankwasniak.domain.interactor.AuthInteractor
import com.damiankwasniak.domain.utils.AsyncResult
import com.damiankwasniak.interview.R
import com.damiankwasniak.interview.ui.base.BaseViewModel
import com.damiankwasniak.interview.extensions.exhaustive
import com.damiankwasniak.interview.provider.ResourcesProvider
import kotlinx.coroutines.launch


class CodeFragmentViewModel(
    private val authInteractor: AuthInteractor,
    private val resourcesProvider: ResourcesProvider
) : BaseViewModel<CodeFragmentViewModel.Command>(resourcesProvider) {

    private val code = "1111"

    private val _error: MutableLiveData<String?> = MutableLiveData(null)

    val error: LiveData<String?> = _error

    fun onCodeEntered(code: String) {
        viewModelScope.launch {
            val result = authInteractor.checkPinCode(code)
            when (result) {
                is AsyncResult.Success -> onCodeVerified(result.data)
                is AsyncResult.Error -> showError(result.exception)
            }.exhaustive
        }
    }

    private fun onCodeVerified(isValid: Boolean) {
        if (isValid) {
            Command.CodeCorrect.apply()
        } else {
            _error.value = resourcesProvider.getString(R.string.error_invalid_code)
        }
    }

    fun onCodeChanged() {
        _error.value = null
    }

    sealed class Command {
        object CodeCorrect : Command()

    }

}
