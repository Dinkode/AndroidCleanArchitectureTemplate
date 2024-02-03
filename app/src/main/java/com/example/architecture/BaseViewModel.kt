package com.example.architecture

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.architecture.common.data.utils.BaseDto
import com.example.architecture.common.utils.BaseRequestState
import com.example.architecture.items.domain.utils.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class BaseViewModel : ViewModel() {
    private var mJob: Job? = null

    protected fun <T:BaseDto<W>, W> baseRequest(state: MutableState<BaseRequestState<W>>, successHandler: (suspend (s: W?) -> Unit)?, errorHandler: (suspend (e: String) -> Unit)?, request: () -> Flow<Resource<T>>) {
        mJob = viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, error ->
            viewModelScope.launch {
                state.value = BaseRequestState(data = null, isLoading = false, error = error.message)
                if (errorHandler != null) {
                    errorHandler(error.message ?: "Unhandled error")
                }
            }
        }){
            request().collect {
                withContext(Dispatchers.Main) {
                    when(it) {
                        is Resource.Success<T> -> {
                            state.value = BaseRequestState(data = it.data?.toModel(), isLoading = false, error = null)

                            if (successHandler != null) {
                                successHandler(it.data?.toModel())
                            }
                        }

                        is Resource.Error -> {
                            val errorMessage = it.message ?: "Unhandled error"
                            state.value = BaseRequestState(data = null, isLoading = false, error = errorMessage)
                            if (errorHandler != null) {
                                errorHandler(errorMessage)
                            }
                        }
                        Resource.Loading -> {
                            state.value = BaseRequestState(data = null, isLoading = true, error = null)
                        }
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        mJob?.let {
            if (it.isActive) {
                it.cancel()
            }
        }
    }

}