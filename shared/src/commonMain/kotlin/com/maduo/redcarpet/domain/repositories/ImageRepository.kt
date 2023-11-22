package com.maduo.redcarpet.domain.repositories

import com.maduo.redcarpet.util.Resource
import kotlinx.coroutines.flow.Flow

interface ImageRepository {

    suspend fun uploadReceipt(image: ByteArray, fileName: String, token: String): Flow<Resource<String>>

}