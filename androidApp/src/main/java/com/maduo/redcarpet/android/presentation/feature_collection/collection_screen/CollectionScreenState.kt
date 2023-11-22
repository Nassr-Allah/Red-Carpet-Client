package com.maduo.redcarpet.android.presentation.feature_collection.collection_screen

import com.maduo.redcarpet.domain.model.ClientCollection

data class CollectionScreenState(
    val isLoading: Boolean = false,
    val collection: ClientCollection? = null,
    val error: String = ""
)