package com.example.architecture.login.presentation
import android.annotation.SuppressLint
import android.service.autofill.UserData
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.viewModelScope
import com.example.architecture.BaseViewModel
import com.example.architecture.common.utils.TokenManager
import com.example.architecture.common.utils.UiEvent
import com.example.architecture.common.utils.UserManager
import com.example.architecture.login.domain.use_cases.LoginUseCases
import com.example.architecture.items.domain.utils.Resource
import com.example.architecture.login.data.apiServices.response.UserDataDto
import com.example.architecture.login.data.mappers.toUserModelMap
import com.example.architecture.login.domain.models.UserModel
import com.example.architecture.utils.Fields
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("MutableCollectionMutableState")
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCases: LoginUseCases,
    private val tokenManager: TokenManager,
    private val userManager: UserManager,
): BaseViewModel() {


    private var _fieldsState = mutableStateMapOf<Fields, String>()
    val fieldsState: SnapshotStateMap<Fields, String> = _fieldsState

    var isLoading by mutableStateOf(false)
        private set


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    suspend fun onError(error: String) {
        _eventFlow.emit(
            UiEvent.ShowSnackbar(error)
        )
    }

    fun onFieldChange(field: Fields, value: String) {
        _fieldsState[field] = value
    }
    private suspend fun loginHandler (data: Resource<UserDataDto>) {
        when (data) {
            is Resource.Loading -> {
                isLoading = true
            }
            is Resource.Success -> {
                val user = data.data?.toUserModelMap()
                user?.let {
                    tokenManager.saveToken(it.token)
                    userManager.saveUser(UserModel(it.firstName, it.lastName, it.token))
                }
                isLoading = false
            }

            is Resource.Error -> {
                _eventFlow.emit(
                    UiEvent.ShowSnackbar(
                        data.message ?: ""
                    )
                )
                isLoading = false
            }

            else -> {}
        }
    }


    fun login () {
        viewModelScope.launch {
            baseRequest({loginHandler(it)}, {onError(it)}) {
                useCases.login(fieldsState[Fields.USERNAME] ?: "", fieldsState[Fields.PASSWORD] ?: "")
            }
        }
    }
}