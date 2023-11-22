package com.maduo.redcarpet.data.remote

import com.maduo.redcarpet.domain.dto.*
import com.maduo.redcarpet.domain.model.Course
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class RestApi {

    companion object {
        private const val BASE_URL = "http://89.116.236.180"
        //private const val BASE_URL = "http://10.0.2.2:8080"
    }

    private val httpClient = HttpClient {
        install(Auth) {
            bearer {  }
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }

    //Auth
    suspend fun login(authRequestDto: AuthRequestDto): HttpResponse {
        return httpClient.post("$BASE_URL/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(authRequestDto)
        }
    }

    //Clients
    suspend fun fetchClientById(id: String, token: String): HttpResponse {
        println("fetchClientById called")
        return httpClient.get("$BASE_URL/clients/$id") {
            bearerAuth(token)
        }
    }

    suspend fun fetchClientByPhoneNumber(phoneNumber: String, token: String): HttpResponse {
        return httpClient.get("$BASE_URL/clients/$phoneNumber") {
            bearerAuth(token)
        }
    }

    suspend fun postClient(clientDto: ClientDto): HttpResponse {
        return httpClient.post("$BASE_URL/clients") {
            contentType(ContentType.Application.Json)
            setBody(clientDto)
        }
    }

    suspend fun updateClient(clientDto: ClientDto, token: String): HttpResponse {
        return httpClient.put("$BASE_URL/clients") {
            contentType(ContentType.Application.Json)
            setBody(clientDto)
            bearerAuth(token)
        }
    }

    //Collections
    suspend fun fetchCollectionByClientId(clientId: String, token: String): HttpResponse {
        return httpClient.get("$BASE_URL/collections/client/$clientId") {
            bearerAuth(token)
        }
    }

    suspend fun fetchCollectionById(id: String, token: String): HttpResponse {
        return httpClient.get("$BASE_URL/collections/$id") {
            bearerAuth(token)
        }
    }

    suspend fun postCollection(clientCollectionDto: ClientCollectionDto, token: String): HttpResponse {
        return httpClient.post("$BASE_URL/collections") {
            setBody(clientCollectionDto)
            bearerAuth(token)
        }
    }

    //Course
    suspend fun fetchAllCourses(token: String): HttpResponse {
        return httpClient.get("$BASE_URL/courses") {
            bearerAuth(token)
        }
    }

    suspend fun fetchCourseById(id: String, token: String): HttpResponse {
        return httpClient.get("$BASE_URL/courses/$id") {
            bearerAuth(token)
        }
    }

    //Custom Orders
    suspend fun fetchCustomOrdersByClientId(clientId: String, token: String): HttpResponse {
        return httpClient.get("$BASE_URL/custom-orders/client/$clientId") {
            bearerAuth(token)
        }
    }

    suspend fun fetchCustomOrderById(id: String, token: String): HttpResponse {
        return httpClient.get("$BASE_URL/custom-orders/$id") {
            bearerAuth(token)
        }
    }

    suspend fun postCustomOrder(customOrderDto: CustomOrderDto, token: String): HttpResponse {
        return httpClient.post("$BASE_URL/custom-orders") {
            contentType(ContentType.Application.Json)
            setBody(customOrderDto)
            bearerAuth(token)
            println("$body")
        }
    }

    //Designs
    suspend fun fetchAllDesigns(token: String): HttpResponse {
        return httpClient.get("$BASE_URL/designs") {
            bearerAuth(token)
        }
    }

    suspend fun fetchDesignById(id: String, token: String): HttpResponse {
       return httpClient.get("$BASE_URL/designs/$id") {
           bearerAuth(token)
       }
    }

    //Orders
    suspend fun fetchClientOrders(clientId: String, token: String): HttpResponse {
        return httpClient.get("$BASE_URL/orders/client/$clientId") {
            bearerAuth(token)
        }
    }

    suspend fun fetchOrderById(id: String, token: String): HttpResponse {
        return httpClient.get("$BASE_URL/orders/$id") {
            bearerAuth(token)
        }
    }

    suspend fun postOrder(orderDto: NormalOrderDto, token: String): HttpResponse {
        return httpClient.post("$BASE_URL/orders") {
            contentType(ContentType.Application.Json)
            setBody(orderDto)
            bearerAuth(token)
        }
    }

    //Patterns
    suspend fun fetchPatternById(id: String, token: String): HttpResponse {
        return httpClient.get("$BASE_URL/patterns/$id") {
            bearerAuth(token)
        }
    }

    //Payments
    suspend fun fetchClientPayments(clientId: String, token: String): HttpResponse {
        return httpClient.get("$BASE_URL/payments/client/$clientId") {
            bearerAuth(token)
        }
    }

    suspend fun postPayment(paymentDto: PaymentDto, token: String): HttpResponse {
        return httpClient.post("$BASE_URL/payments") {
            contentType(ContentType.Application.Json)
            setBody(paymentDto)
            bearerAuth(token)
        }
    }

    //Registrations
    suspend fun postRegistration(registrationDto: RegistrationDto, token: String): HttpResponse {
        return httpClient.post("$BASE_URL/registrations") {
            contentType(ContentType.Application.Json)
            setBody(registrationDto)
            bearerAuth(token)
        }
    }

    //Images
    suspend fun postAttachment(image: ByteArray, fileName: String, token: String): HttpResponse {
        return httpClient.post("$BASE_URL/image/attachment") {
            setBody(MultiPartFormDataContent(
                formData {
                    append("", image, Headers.build {
                        append(HttpHeaders.ContentType, "image")
                        append(HttpHeaders.ContentDisposition, "filename=\"$fileName\"")
                    })
                },
                boundary = "WebAppBoundary"
            ))
            onUpload { bytesSentTotal, contentLength ->
                println("Sent $bytesSentTotal, Total: $contentLength")
            }
            bearerAuth(token)
        }
    }


}