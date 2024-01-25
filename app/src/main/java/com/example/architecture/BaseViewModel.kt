package com.example.architecture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class BaseViewModel( val globalState: GlobalState) : ViewModel() {
    private var mJob: Job? = null

    protected fun <T> baseRequest(successHandler: suspend (data: T) -> Unit, request: () -> Flow<T>) {
        mJob = viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, error ->
            viewModelScope.launch(Dispatchers.Main) {
                globalState.setError(error.message ?: "")
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

interface CoroutinesErrorHandler {
    fun onError(message:String)
}