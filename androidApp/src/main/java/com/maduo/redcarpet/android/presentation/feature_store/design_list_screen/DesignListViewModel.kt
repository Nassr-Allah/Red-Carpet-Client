package com.maduo.redcarpet.android.presentation.feature_store.design_list_screen

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maduo.redcarpet.data.mappers.mapToDesign
import com.maduo.redcarpet.domain.model.Design
import com.maduo.redcarpet.domain.repositories.ClientCacheRepository
import com.maduo.redcarpet.domain.repositories.DesignRepository
import com.maduo.redcarpet.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DesignListViewModel @Inject constructor(
    private val designsRepository: DesignRepository,
    private val clientCacheRepository: ClientCacheRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DesignListScreenState())
    val state: MutableStateFlow<DesignListScreenState> = _state

    val searchQuery = mutableStateOf("")
    var designs = listOf<Design>()
    var filteredList = mutableStateListOf<Design>()

    init {
        getAllDesigns()
    }

    private fun getAllDesigns() {
        viewModelScope.launch {
            val clientAuthDetails = clientCacheRepository.getClientFromDb()
            Log.d("DesignListViewModel", clientAuthDetails.toString())
            if (clientAuthDetails != null) {
                designsRepository.getAllDesigns(clientAuthDetails.token).collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _state.value = _state.value.copy(isLoading = true, error = "")
                        }
                        is Resource.Success -> {
                            designs = result.data?.map {
                                mapToDesign(it)
                            } ?: emptyList()
                            filteredList = designs.toMutableStateList()
                            _state.value = _state.value.copy(isLoading = false, designs = designs)
                        }
                        is Resource.Error -> {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                error = result.message ?: "Unknown Error"
                            )
                        }
                    }
                }
            } else {
                _state.value = _state.value.copy(isLoading = false, error = "Unauthorized Request")
            }
        }
    }

    fun filterDesignsByQuery(query: String) {
        filteredList = designs.filter { it.name.contains(query, true) }.toMutableStateList()
        _state.value = _state.value.copy(designs = filteredList)
    }

    fun filterListByCategory(category: String) {
        if (category.equals("all", true)) {
            Log.d("DesignListViewModel", category)
            filteredList = designs.toMutableStateList()
            _state.value = _state.value.copy(designs = designs)
        } else {
            filteredList = designs.filter {
                it.category.equals(category, true)
            }.toMutableStateList()
            _state.value = _state.value.copy(designs = filteredList)
        }
    }

}