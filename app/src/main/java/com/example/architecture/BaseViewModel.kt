package com.example.architecture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class BaseViewModel() : ViewModel() {
    private var mJob: Job? = null

    protected fun <T> baseRequest(successHandler: suspend (data: T) -> Unit, errorHandler: (suspend (e: String) -> Unit)?, request: () -> Flow<T>) {
        mJob = viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, error ->
            viewModelScope.launch {
                if (errorHandler != null) {
                    errorHandler(error.message ?: "unexpected error")
                }
            }
        }){
            request().collect {
                withContext(Dispatchers.Main) {
                    successHandler(it)
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