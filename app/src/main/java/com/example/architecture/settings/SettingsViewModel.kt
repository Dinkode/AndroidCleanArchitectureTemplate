package com.example.architecture.settings

import androidx.lifecycle.viewModelScope
import com.example.architecture.BaseViewModel
import com.example.architecture.common.utils.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    globalState: GlobalState
): BaseViewModel(globalState) {



    fun logout() {
        viewModelScope.launch {
            tokenManager.deleteToken()
        }
    }
}