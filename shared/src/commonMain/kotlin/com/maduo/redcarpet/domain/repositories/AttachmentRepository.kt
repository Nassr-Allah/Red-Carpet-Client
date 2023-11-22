package com.maduo.redcarpet.domain.repositories

import com.maduo.redcarpet.util.Resource
import kotlinx.coroutines.flow.Flow

interface AttachmentRepository {

    suspend fun uploadAttachment(image: ByteArray, fileName: String): Flow<Resource<String>>

}