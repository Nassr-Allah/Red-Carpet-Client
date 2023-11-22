package com.maduo.redcarpet.data.mappers

import com.maduo.redcarpet.domain.dto.DesignCategoryDto
import com.maduo.redcarpet.domain.dto.DesignDto
import com.maduo.redcarpet.domain.dto.OriginalPatternDto
import com.maduo.redcarpet.domain.model.Design

fun mapToDesign(designDto: DesignDto): Design {

    val images = designDto.images.ifEmpty { listOf("null") }

    return Design(
        id = designDto.id,
        name = designDto.name,
        designer = designDto.designer,
        price = designDto.price,
        images = images,
        sizes = designDto.sizes,
        description = designDto.description,
        deliveryPlaces = designDto.delivery?.places!!,
        deliveryPrice = designDto.delivery.price,
        patternPrice = designDto.patternPrice,
        category = designDto.category
    )
}
