package com.maduo.redcarpet.data.data_source

import com.maduo.redcarpet.data.remote.RestApi
import com.maduo.redcarpet.domain.repositories.ImageRepository
import com.maduo.redcarpet.util.Resource
import io.ktor.client.call.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ImageRepositoryImpl(private val restApi: RestApi = RestApi()): ImageRepository {

    override suspend fun uploadReceipt(image: ByteArray, fileName: String, token: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading<String>())
        println("Image Repo:  started call ...")
        try {
            val response = restApi.postAttachment(image, fileName, token)
            println("Image Repo:  response: $response")
            if (response.status.value == 201) {
                val imageUrl = response.body<String>()
                emit(Resource.Success(imageUrl))
            } else {
                val message = response.body<String>().ifEmpty { "Upload Failed!" }
                emit(Resource.Error<String>(message))
            }
        } catch (e: Exception) {
            println("Image Repo:  exception $e")
            emit(Resource.Error<String>(e.message ?: "Unexpected Error"))
        }
    }

}