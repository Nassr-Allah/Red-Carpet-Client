package com.maduo.redcarpet.domain.firebase

import com.maduo.redcarpet.util.Resource
import kotlinx.coroutines.flow.Flow

expect class FirebasePhoneAuthManager() {
    suspend fun verifyPhoneNumber(phoneNumber: String): Flow<Resource<String>>
}