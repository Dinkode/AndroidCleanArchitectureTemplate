package com.example.architecture.items.presentation.item_details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.architecture.BaseViewModel
import com.example.architecture.common.utils.BaseRequestState
import com.example.architecture.items.domain.models.ItemDetailsModel
import com.example.architecture.items.domain.use_cases.ItemUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemDetailsViewModel @Inject constructor(
    private val useCases: ItemUseCases,
    savedStateHandle: SavedStateHandle,
): BaseViewModel() {

    private val _itemDetails = mutableStateOf<BaseRequestState<ItemDetailsModel>>(BaseRequestState(null, false, ""))
    val itemDetails = _itemDetails

    init {
        savedStateHandle.get<String>("id")?.let { id ->
            getItemDetails(id)
        }
    }

    fun getItemDetails (id: String) {
        viewModelScope.launch {
            baseRequest(_itemDetails, null, null) {
                useCases.getItemDetails(id)
            }
        }
    }
}

