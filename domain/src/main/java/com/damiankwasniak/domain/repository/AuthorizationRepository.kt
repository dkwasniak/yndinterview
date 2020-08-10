package com.damiankwasniak.domain.repository

import com.damiankwasniak.domain.utils.AsyncResult

interface AuthorizationRepository {

    fun checkPinCode(pinCode: String?): AsyncResult<Boolean>

    fun lock()

    fun isLocked(): AsyncResult<Boolean>
}