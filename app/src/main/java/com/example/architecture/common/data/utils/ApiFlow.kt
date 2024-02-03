package com.example.architecture.common.data.utils

import com.example.architecture.items.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withTimeoutOrNull
import retrofit2.Response

fun<T> apiFlow(call: suspend () -> Response<T>): Flow<Resource<T>> = flow {
    emit(Resource.Loading)

    withTimeoutOrNull(20000L) {
        val response = call()

        try {
            if (response.isSuccessful) {
                response.body()?.let { data ->
                    emit(Resource.Success(data))
                }
            } else {
                response.errorBody()?.let { error ->
                    error.close()
                    val parsedError: Error = Error(error.toString())
                    emit(Resource.Error(parsedError.message ?: "", response.body()))
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: e.toString(), response.body()))
        }
    } ?: emit(Resource.Error("Timeout! Please try again.", null))
}.flowOn(Dispatchers.IO)

fun<T, W> Flow<T>.toModel(callback: () -> W):Flow<W> {
    return flow { callback() }
}