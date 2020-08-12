package com.damiankwasniak.domain.interactor

import com.damiankwasniak.domain.repository.AuthorizationRepository
import com.damiankwasniak.domain.utils.AsyncResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthInteractor(
    private val authorizationRepository: AuthorizationRepository
) {

    suspend fun checkPinCode(pinCode: String): AsyncResult<Boolean> {
        return withContext(Dispatchers.IO) {
            authorizationRepository.checkPinCode(pinCode)
        }
    }

    suspend fun lock() {
        return withContext(Dispatchers.IO) {
            authorizationRepository.lock()
        }
    }

    suspend fun isLocked(): AsyncResult<Boolean> {
        return withContext(Dispatchers.IO) {
            authorizationRepository.isLocked()
        }
    }

}