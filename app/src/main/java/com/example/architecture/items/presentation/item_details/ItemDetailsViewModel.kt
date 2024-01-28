package com.example.architecture.items.presentation.item_details

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.architecture.BaseViewModel
import com.example.architecture.items.data.apiServices.ItemDetailsDto
import com.example.architecture.items.data.mappers.toItemDetailsModelMap
import com.example.architecture.items.domain.models.ItemDetailsModel
import com.example.architecture.items.domain.use_cases.ItemUseCases
import com.example.architecture.items.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemDetailsViewModel @Inject constructor(
    private val useCases: ItemUseCases,
    savedStateHandle: SavedStateHandle,
): BaseViewModel() {
    var state by mutableStateOf(ItemDetailsState(isLoading = false, data = null))
        private set

    init {
        savedStateHandle.get<String>("id")?.let { id ->
            getItemDetails(id)
        }
    }


    private fun getItemDetailsHandler (data: Resource<ItemDetailsDto>) {
        when (data) {
            is Resource.Success -> {
                state.isLoading = false
                state.data = data.data?.toItemDetailsModelMap()
            }

            is Resource.Error -> {
                state.isLoading = true
            }

            is Resource.Loading -> {
                state.isLoading = true
            }
        }
    }


    fun getItemDetails (id: String) {
        viewModelScope.launch {
            baseRequest({getItemDetailsHandler(it)}, null) {
                useCases.getItemDetails(id)
            }
        }
    }
}

