package com.example.architecture.items.presentation.items
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.architecture.BaseViewModel
import com.example.architecture.common.utils.BaseRequestState
import com.example.architecture.items.domain.models.ItemModel
import com.example.architecture.items.domain.use_cases.ItemUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
    private val useCases: ItemUseCases,
): BaseViewModel() {

    private val _items = mutableStateOf<BaseRequestState<List<ItemModel>>>(BaseRequestState(null, false, ""))
    val items = _items

    init {
        getItems()
    }

    private fun getItems () {
        viewModelScope.launch {
            baseRequest(_items, null, null) {
                useCases.getItems()
            }
        }
    }
}