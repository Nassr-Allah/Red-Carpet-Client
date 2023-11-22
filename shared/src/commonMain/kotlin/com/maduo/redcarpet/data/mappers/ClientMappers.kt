package com.maduo.redcarpet.data.mappers

import com.maduo.redcarpet.domain.entity.ClientAuthDetails
import database.ClientEntity

fun ClientEntity.toClientAuthDetails(): ClientAuthDetails {
    return ClientAuthDetails(id, token, clientId)
}