package com.example.architecture.items.data.apiServices

import com.example.architecture.common.data.utils.BaseDto
import com.example.architecture.items.domain.models.ItemDetailsModel
import com.example.architecture.items.domain.models.ItemModel

data class ItemDetailsDto(
    val id: Int,
    val title: String,
    val description: String,
    val images: List<String>

): BaseDto<ItemDetailsModel> {
    override fun toModel(): ItemDetailsModel {
        return ItemDetailsModel(id, title, description, images)
    }
}
