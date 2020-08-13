package com.damiankwasniak.data.repository

import com.damiankwasniak.data.AppPrefs
import com.damiankwasniak.data.utils.SessionManager
import com.damiankwasniak.domain.repository.AuthorizationRepository
import com.damiankwasniak.domain.utils.AsyncResult

class AuthorizationRepositoryImpl(
    private val appPrefs: AppPrefs,
    private val sessionManager: SessionManager
) : AuthorizationRepository {


    override fun isLocked(): AsyncResult<Boolean> {
        return AsyncResult.Success(appPrefs.lock)
    }

    override fun lock() {
        appPrefs.lock = true
    }

    override fun checkPinCode(pinCode: String): AsyncResult<Boolean> {
        val isCodeCorrect = appPrefs.passHash == pinCode.hashCode()
        if (isCodeCorrect) {
            sessionManager.sessionCode = pinCode
        }
        return AsyncResult.Success(isCodeCorrect)
    }
}