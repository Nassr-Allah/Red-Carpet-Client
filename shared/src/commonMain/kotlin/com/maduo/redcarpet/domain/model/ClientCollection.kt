package com.maduo.redcarpet.domain.model

import com.maduo.redcarpet.domain.dto.DesignDto
import com.maduo.redcarpet.domain.dto.OriginalPatternDto

data class ClientCollection(
    val id: String = "",
    val designs: List<String> = emptyList(),
    val patterns: List<String> = emptyList()
)
