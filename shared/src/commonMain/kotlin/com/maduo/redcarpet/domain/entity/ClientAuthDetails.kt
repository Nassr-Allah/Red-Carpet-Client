package com.maduo.redcarpet.domain.entity

data class ClientAuthDetails(
    val id: Long? = null,
    val token: String = "",
    val clientId: String = ""
)
