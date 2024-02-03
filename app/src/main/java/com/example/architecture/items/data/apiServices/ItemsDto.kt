package com.example.architecture.items.data.apiServices

import com.example.architecture.common.data.utils.BaseDto
import com.example.architecture.items.domain.models.ItemModel
import com.squareup.moshi.Json

data class ItemsDto(
    @field:Json(name = "products")
    val items: List<ItemDataDto>
): BaseDto<List<ItemModel>> {

    override fun toModel(): List<ItemModel> {
        return items.map { item ->
            val id = item.id
            val itemName = item.itemName
            return@map ItemModel(id, itemName)
        }
    }

}