package com.example.architecture.items.data.repository

import com.example.architecture.common.data.utils.apiFlow
import com.example.architecture.items.data.apiServices.ItemDetailsDto
import com.example.architecture.items.data.apiServices.ItemService
import com.example.architecture.items.data.apiServices.ItemsDto
import com.example.architecture.items.domain.repository.ItemRepository
import com.example.architecture.items.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val api: ItemService
): ItemRepository {


    override fun getItems(): Flow<Resource<ItemsDto>> = apiFlow { api.getItems() }


    override fun getItemDetails(id: String):  Flow<Resource<ItemDetailsDto>> = apiFlow {
         api.getItemDetails(id)
    }
}