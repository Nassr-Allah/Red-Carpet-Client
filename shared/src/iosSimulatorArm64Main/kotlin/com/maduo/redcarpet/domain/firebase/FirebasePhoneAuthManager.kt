package com.maduo.redcarpet.domain.firebase

actual class FirebasePhoneAuthManager actual constructor() {
    actual suspend fun verifyPhoneNumber(
        phoneNumber: String,
        callback: (String) -> Unit
    ) {
    }
}