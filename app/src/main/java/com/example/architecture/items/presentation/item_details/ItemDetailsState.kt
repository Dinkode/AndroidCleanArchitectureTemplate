package com.example.architecture.items.presentation.item_details

import com.example.architecture.items.domain.models.ItemDetailsModel

data class ItemDetailsState(
    var data: ItemDetailsModel?,
    var isLoading: Boolean
)
