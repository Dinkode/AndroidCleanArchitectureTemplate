package com.example.architecture.items.domain.repository

import com.example.architecture.items.data.apiServices.ItemDetailsDto
import com.example.architecture.items.data.apiServices.ItemsDto
import com.example.architecture.items.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    fun getItems(): Flow<Resource<ItemsDto>>

    fun getItemDetails(id: String): Flow<Resource<ItemDetailsDto>>
}