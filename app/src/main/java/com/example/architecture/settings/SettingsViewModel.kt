package com.example.architecture.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.architecture.BaseViewModel
import com.example.architecture.common.utils.TokenManager
import com.example.architecture.common.utils.UserManager
import com.example.architecture.login.data.apiServices.response.UserDataDto
import com.example.architecture.login.domain.models.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val userManager: UserManager
): BaseViewModel() {

    var user by mutableStateOf<UserModel?>(null)
        private set
    init {
        viewModelScope.launch {
            user = userManager.getUser().first()
        }
    }

    fun logout() {
        viewModelScope.launch {
            tokenManager.deleteToken()
            userManager.deleteUser()
        }
    }
}