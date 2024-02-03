package com.example.architecture.login.presentation
import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.viewModelScope
import com.example.architecture.BaseViewModel
import com.example.architecture.common.utils.BaseRequestState
import com.example.architecture.common.utils.TokenManager
import com.example.architecture.common.utils.UiEvent
import com.example.architecture.common.utils.UserManager
import com.example.architecture.login.domain.use_cases.LoginUseCases
import com.example.architecture.login.domain.models.UserModel
import com.example.architecture.utils.Fields
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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
    @ApplicationContext private val context: Context
): BaseViewModel() {


    private var _fieldsState = mutableStateMapOf<Fields, String>()
    val fieldsState: SnapshotStateMap<Fields, String> = _fieldsState


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _user = mutableStateOf<BaseRequestState<UserModel>>(BaseRequestState(null, false, ""))
    val user = _user

    private suspend fun onError(error: String) {
        _eventFlow.emit(
            UiEvent.ShowSnackbar(error)
        )
    }

    fun onFieldChange(field: Fields, value: String) {
        _fieldsState[field] = value
    }
    private suspend fun onSuccess (data: UserModel?) {
        data?.let {
            tokenManager.saveToken(it.token)
                    userManager.saveUser(UserModel(it.firstName, it.lastName, it.token))
                }
    }


    fun login () {
        viewModelScope.launch {
            baseRequest(_user, { onSuccess(it) }, {onError(it)}) {
                useCases.login(fieldsState[Fields.USERNAME] ?: "", fieldsState[Fields.PASSWORD] ?: "")
            }
        }
    }
}