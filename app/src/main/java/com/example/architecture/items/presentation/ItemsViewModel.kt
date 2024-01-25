package com.example.architecture.items.presentation
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.architecture.BaseViewModel
import com.example.architecture.items.data.apiServices.ItemsDto
import com.example.architecture.items.data.mappers.toItemModelMap
import com.example.architecture.items.domain.use_cases.ItemUseCases
import com.example.architecture.items.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
    private val useCases: ItemUseCases,
    globalState: GlobalState
): BaseViewModel(globalState) {
    private val _state = mutableStateOf(ItemsState(emptyList(), true))
    val state: State<ItemsState> = _state

    init {
        getItems()
    }

    fun getItemsHandler (data: Resource<ItemsDto>) {
        when (data) {
            is Resource.Success -> {
                data.data?.toItemModelMap()?.let {
                    _state.value = ItemsState(showButton = false, items = it)
                }
            }

            is Resource.Error -> {


            }

            else -> {}
        }
    }

    fun getItems () {
        viewModelScope.launch {
            baseRequest({ getItemsHandler(it) }) {
                useCases.getItems()
            }
        }
    }
}