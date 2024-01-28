package com.example.architecture.items.presentation.items

import com.example.architecture.items.domain.models.ItemModel
import com.example.architecture.login.domain.models.UserModel

data class ItemsState(
    var items: List<ItemModel> = emptyList(),
    var showButton: Boolean = false,
    var user: UserModel? = null
)