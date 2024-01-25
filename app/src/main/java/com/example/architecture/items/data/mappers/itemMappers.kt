package com.example.architecture.items.data.mappers

import com.example.architecture.items.data.apiServices.ItemDetailsDto
import com.example.architecture.items.data.apiServices.ItemsDto
import com.example.architecture.items.domain.models.ItemDetailsModel
import com.example.architecture.items.domain.models.ItemModel

fun ItemsDto.toItemModelMap(): List<ItemModel> {
    return items.map { item ->
        val id = item.id
        val itemName = item.itemName
        return@map ItemModel(id, itemName)
    }
}

fun ItemDetailsDto.toItemDetailsModelMap(): ItemDetailsModel {
    return ItemDetailsModel(id, itemName, itemName)
    }
