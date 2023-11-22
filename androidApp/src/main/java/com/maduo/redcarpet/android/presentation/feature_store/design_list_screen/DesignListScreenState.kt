package com.maduo.redcarpet.android.presentation.feature_store.design_list_screen

import com.maduo.redcarpet.domain.dto.DesignCategoryDto
import com.maduo.redcarpet.domain.model.Design

data class DesignListScreenState(
    val isLoading: Boolean = false,
    val designs: List<Design> = emptyList(),
    val categories: List<DesignCategoryDto> = emptyList(),
    val error: String = ""
)