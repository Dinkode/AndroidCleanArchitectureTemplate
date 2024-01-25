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
import com.example.architecture.items.domain.use_cases.ItemUseCases
import com.example.architecture.items.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemDetailsViewModel @Inject constructor(
    private val useCases: ItemUseCases,
    savedStateHandle: SavedStateHandle,
    globalState: GlobalState
): BaseViewModel(globalState) {
    var state by mutableStateOf(ItemDetailsState(isLoading = true, data = null))
        private set

    init {
        Log.d("dddww", savedStateHandle.get<Int>("id").toString())
        savedStateHandle.get<String>("id")?.let { id ->
            getItemDetails(id)
        }
    }


    fun getItemDetailsHandler (data: Resource<ItemDetailsDto>) {
        when (data) {
            is Resource.Success -> {
                state.data = data.data?.toItemDetailsModelMap()
            }

            is Resource.Error -> {


            }

            else -> {}
        }
    }


    fun getItemDetails (id: String) {
        viewModelScope.launch {
            baseRequest({getItemDetailsHandler(it)}) {
                useCases.getItemDetails(id)
            }
        }
    }
}

