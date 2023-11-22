package com.maduo.redcarpet.domain.firebase

import com.maduo.redcarpet.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

actual class FirebasePhoneAuthManager actual constructor() {
    actual suspend fun verifyPhoneNumber(
        phoneNumber: String
    ): Flow<Resource<String>> = callbackFlow {
    }
}