package com.maduo.redcarpet.data.mappers

import com.maduo.redcarpet.domain.dto.ClientCollectionDto
import com.maduo.redcarpet.domain.model.ClientCollection

fun mapToCollection(collectionDto: ClientCollectionDto): ClientCollection {
    return ClientCollection(
        id = collectionDto.id,
        designs = collectionDto.designs,
        patterns = collectionDto.patterns
    )
}