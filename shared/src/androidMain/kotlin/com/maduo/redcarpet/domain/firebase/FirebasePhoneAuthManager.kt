package com.maduo.redcarpet.domain.firebase

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.maduo.redcarpet.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

actual class FirebasePhoneAuthManager actual constructor() {

    private lateinit var activity: Activity
    private lateinit var auth: FirebaseAuth

    actual suspend fun verifyPhoneNumber(phoneNumber: String): Flow<Resource<String>> = callbackFlow {
        trySend(Resource.Loading<String>())
        Log.d("PhoneAuthCallback", phoneNumber)
        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d("PhoneAuthCallback", "verification completed")
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.d("PhoneAuthCallback", "$e")
                trySend(Resource.Error<String>(e.message ?: "Unknown Firebase Error"))
            }
            override fun onCodeSent(verificationId: String, resendToken: PhoneAuthProvider.ForceResendingToken) {
                trySend(Resource.Success(verificationId))
            }
        }

        auth = Firebase.auth

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setCallbacks(callbacks)
            .setActivity(activity)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)

        awaitClose { channel.close() }
    }

    fun setActivity(context: Activity) {
        activity = context
    }

    fun signInUserWithCredential(credential: PhoneAuthCredential): Flow<Resource<Boolean>> = callbackFlow {
        trySend(Resource.Loading<Boolean>())
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                trySend(Resource.Success(true))
            } else {
                trySend(Resource.Error<Boolean>("Wrong Code!"))
            }
        }
        awaitClose { channel.close() }
    }
}