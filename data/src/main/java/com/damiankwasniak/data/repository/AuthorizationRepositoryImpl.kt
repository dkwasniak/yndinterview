package com.damiankwasniak.data.repository

import com.damiankwasniak.data.AppPrefs
import com.damiankwasniak.domain.repository.AuthorizationRepository
import com.damiankwasniak.domain.utils.AsyncResult

class AuthorizationRepositoryImpl(
    private val appPrefs: AppPrefs
) : AuthorizationRepository {

    override fun isLocked(): AsyncResult<Boolean> {
        return AsyncResult.Success(appPrefs.lock)
    }

    override fun lock() {
        appPrefs.lock = true
    }

    override fun checkPinCode(pinCode: String?): AsyncResult<Boolean> {
        return AsyncResult.Success(appPrefs.pinCode == pinCode)
    }
}